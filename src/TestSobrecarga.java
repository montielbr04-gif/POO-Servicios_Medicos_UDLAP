/*
 * Clase: TestSobrecarga
 * Breve: Pruebas para la ventana de Sobrecarga y la inserción en DB.
 * Uso: Valida que los componentes existan y que el guardado funcione.
 */
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;

public class TestSobrecarga {

    @BeforeClass
    public static void setup() {
        // Asegurarse que la tabla exista antes
        DatabaseHelper.createTables();
    }

    @Test
    public void testComponentsExist() throws Exception {
        Sobrecarga window = new Sobrecarga();

        // Acceder a campos privados
        Field fNombre = Sobrecarga.class.getDeclaredField("txtNombre");
        Field fSintomas = Sobrecarga.class.getDeclaredField("txtSintomas");
        Field fPrioridad = Sobrecarga.class.getDeclaredField("comboPrioridad");
        Field fCampus = Sobrecarga.class.getDeclaredField("campusCheck");
        Field fHospital = Sobrecarga.class.getDeclaredField("hospitalCheck");

        fNombre.setAccessible(true);
        fSintomas.setAccessible(true);
        fPrioridad.setAccessible(true);
        fCampus.setAccessible(true);
        fHospital.setAccessible(true);

        assertNotNull("Campo txtNombre debe existir", fNombre.get(window));
        assertNotNull("Campo txtSintomas debe existir", fSintomas.get(window));
        assertNotNull("Combo prioridad debe existir", fPrioridad.get(window));
        assertNotNull("Checkbox campus debe existir", fCampus.get(window));
        assertNotNull("Checkbox hospital debe existir", fHospital.get(window));
    }

    @Test
    public void testValidarFormulario_emptyAndFilled() throws Exception {
        Sobrecarga s = new Sobrecarga();

        // Empeiza vacío, por lo que debería de ser invalido 
        assertFalse("Formulario vacío debe ser inválido", s.validarFormulario());

        // Llenar los campos
        Field fNombre = Sobrecarga.class.getDeclaredField("txtNombre");
        Field fSintomas = Sobrecarga.class.getDeclaredField("txtSintomas");
        Field fCampus = Sobrecarga.class.getDeclaredField("campusCheck");
        fNombre.setAccessible(true);
        fSintomas.setAccessible(true);
        fCampus.setAccessible(true);

        JTextField nombreField = (JTextField) fNombre.get(s);
        JTextArea sintomasArea = (JTextArea) fSintomas.get(s);
        JCheckBox campus = (JCheckBox) fCampus.get(s);

        nombreField.setText("Paciente Test");
        sintomasArea.setText("Dolor de cabeza");
        campus.setSelected(true);

        assertTrue("Formulario lleno debe ser válido", s.validarFormulario());
    }

    @Test
    public void testClearFormResetsFields() throws Exception {
        Sobrecarga s = new Sobrecarga();
        Field fNombre = Sobrecarga.class.getDeclaredField("txtNombre");
        Field fSintomas = Sobrecarga.class.getDeclaredField("txtSintomas");
        Field fPrioridad = Sobrecarga.class.getDeclaredField("comboPrioridad");
        Field fCampus = Sobrecarga.class.getDeclaredField("campusCheck");
        Field fHospital = Sobrecarga.class.getDeclaredField("hospitalCheck");

        fNombre.setAccessible(true);
        fSintomas.setAccessible(true);
        fPrioridad.setAccessible(true);
        fCampus.setAccessible(true);
        fHospital.setAccessible(true);

        JTextField nombreField = (JTextField) fNombre.get(s);
        JTextArea sintomasArea = (JTextArea) fSintomas.get(s);
        JComboBox<?> prioridad = (JComboBox<?>) fPrioridad.get(s);
        JCheckBox campus = (JCheckBox) fCampus.get(s);
        JCheckBox hospital = (JCheckBox) fHospital.get(s);

        nombreField.setText("X");
        sintomasArea.setText("Y");
        prioridad.setSelectedIndex(2);
        campus.setSelected(true);
        hospital.setSelected(true);

        s.clearForm();

        assertEquals("", nombreField.getText());
        assertEquals("", sintomasArea.getText());
        assertEquals(0, prioridad.getSelectedIndex());
        assertFalse(campus.isSelected());
        assertFalse(hospital.isSelected());
    }

    @Test
    public void testInsertSobrecargaDirectDB() throws Exception {
        String nombre = "TEST_SOBRECARGA_" + System.currentTimeMillis();
        String sintomas = "Sintomas de prueba";
        String prioridad = "Media";
        String accion = "Campus";

        // Insertar directamente
        DatabaseHelper.insertSobrecarga(nombre, sintomas, prioridad, accion);

        boolean found = false;
        for (String[] row : DatabaseHelper.getSobrecarga()) {
            if (row.length > 0 && nombre.equals(row[0])) {
                found = true;
                assertEquals("Prioridad debe coincidir", prioridad, row[2]);
                assertEquals("Acción debe coincidir", accion, row[3]);
                break;
            }
        }

        // Limpieza de datos de prueba
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement("DELETE FROM sobrecarga_pacientes WHERE nombre = ?")) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }
        assertTrue("Registro insertado debe encontrarse en la BD", found);
    }

    @Test
    public void testInsertSobrecargaHandlesEmptyFields() throws Exception {
        String nombre = "TEST_EMPTY_FIELDS_" + System.currentTimeMillis();
        // Insretar sin datos
        DatabaseHelper.insertSobrecarga(nombre, "", "Baja", "");

        boolean found = false;
        for (String[] row : DatabaseHelper.getSobrecarga()) {
            if (row.length > 0 && nombre.equals(row[0])) {
                found = true;
                break;
            }
        }

        // Limpieza de datos
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement("DELETE FROM sobrecarga_pacientes WHERE nombre = ?")) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }

        assertTrue("Registro con campos vacíos debe insertarse", found);
    }
}
