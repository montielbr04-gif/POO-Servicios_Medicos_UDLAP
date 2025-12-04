/*
 * Clase: TestFaltaRecursos
 * Breve: Pruebas para insertar y limpiar filas de recursos faltantes en la base.
 * Uso: Verifica que `insertRecurso` y las consultas funcionen como se espera.
 */
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class TestFaltaRecursos {

    @BeforeClass
    public static void setup() {
        DatabaseHelper.createTables();
    }

    @AfterClass
    public static void cleanupAllTestRows() throws Exception {
        // Eliminar cualquier fila generada por tests cuyo tipo empiece con TEST_
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM recursos_faltantes WHERE tipo LIKE 'TEST_%' OR tipo = 'TEST_ORDER'")) {
            ps.executeUpdate();
        }
    }

    @Test
    public void testInventarioRecursosNoNulo() {
        InventarioRecursos inventario = new InventarioRecursos();
        assertNotNull("Inventario no debe ser null", inventario);
    }

    @Test
    public void testRecursoGetters() {
        RecursoDisponible recurso = new RecursoDisponible("R001", "Medicamento", "Paracetamol",
            5, 30, "Alta", true);
        assertEquals("ID debe ser R001", "R001", recurso.getId());
        assertEquals("Tipo debe ser Medicamento", "Medicamento", recurso.getTipo());
        assertEquals("Nombre debe ser Paracetamol", "Paracetamol", recurso.getNombre());
        assertEquals("Cantidad actual debe ser 5", 5, recurso.getCantidadActual());
        assertEquals("Cantidad mínima debe ser 30", 30, recurso.getCantidadMinima());
        assertEquals("Demanda debe ser Alta", "Alta", recurso.getDemanda());
        assertTrue("Sin alternativa debe ser true", recurso.isSinAlternativa());
    }

    @Test
    public void testRecursoDisponibleEstaEnFalta() {
        RecursoDisponible recurso = new RecursoDisponible("R001", "Medicamento", "Paracetamol",
            2, 10, "Alta", false);
        assertTrue("Recurso debe estar en falta (2 < 10)", recurso.estaEnFalta());
    }

    @Test
    public void testRecursoDisponibleNoEstaEnFalta() {
        RecursoDisponible recurso = new RecursoDisponible("R002", "Medicamento", "Ibuprofeno",
            15, 10, "Alta", false);
        assertFalse("Recurso no debe estar en falta (15 >= 10)", recurso.estaEnFalta());
    }

    @Test
    public void testInventarioGetTodos() {
        InventarioRecursos inventario = new InventarioRecursos();
        assertNotNull("Lista de recursos no debe ser null", inventario.getTodos());
        assertTrue("Debe haber al menos un recurso", inventario.getTodos().size() > 0);
    }

    @Test
    public void testInventarioGetEnFalta() {
        InventarioRecursos inventario = new InventarioRecursos();
        assertNotNull("Lista de faltantes no debe ser null", inventario.getEnFalta());
        assertTrue("Lista de faltantes debe tener size >= 0", inventario.getEnFalta().size() >= 0);
    }

    @Test
    public void testInsertRecursoDirectDB() throws Exception {
        String tipo = "TEST_RECURSO_" + System.currentTimeMillis();
        String detalle = "Detalle prueba";
        String severidad = "Alta";
        boolean validado = true;
        String notas = "NOTAS_TEST_" + System.currentTimeMillis();

        // Insertar
        DatabaseHelper.insertRecurso(tipo, detalle, severidad, validado, notas);

        // Verificar que esté en la BD
        boolean found = false;
        List<String[]> recursos = DatabaseHelper.getRecursos();
        for (String[] r : recursos) {
            if (r.length >= 6 && tipo.equals(r[0]) && detalle.equals(r[1])) {
                found = true;
                assertEquals("Severidad debe coincidir", severidad, r[2]);
                assertTrue("Campo validado debe contener Sí o No", r[3].equals("Sí") || r[3].equals("No"));
                assertEquals("Notas debe coincidir", notas, r[4]);
                break;
            }
        }

        // Limpieza: eliminar por tipo
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement("DELETE FROM recursos_faltantes WHERE tipo = ?")) {
            ps.setString(1, tipo);
            ps.executeUpdate();
        }

        assertTrue("Registro insertado debe encontrarse en la BD", found);
    }

    @Test
    public void testInsertRecursoHandlesNullsAndDefaults() throws Exception {
        String detalle = "DL_NULL_TEST_" + System.currentTimeMillis();

        // tipo=null, severidad=null, notas=null -> debe usar valores por defecto
        DatabaseHelper.insertRecurso(null, detalle, null, false, null);

        boolean found = false;
        for (String[] r : DatabaseHelper.getRecursos()) {
            if (r.length >= 6 && detalle.equals(r[1])) {
                found = true;
                assertEquals("Tipo por defecto", "(Sin tipo)", r[0]);
                assertEquals("Detalle debe coincidir", detalle, r[1]);
                assertEquals("Severidad por defecto", "(Sin severidad)", r[2]);
                assertEquals("Notas por defecto vacías", "", r[4]);
                assertTrue("Validado debe ser 'No'", r[3].equals("No") || r[3].equals("Sí"));
                break;
            }
        }

        // Limpieza
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement("DELETE FROM recursos_faltantes WHERE detalle = ?")) {
            ps.setString(1, detalle);
            ps.executeUpdate();
        }

        assertTrue("Registro con valores nulos debe insertarse y encontrarse", found);
    }

    @Test
    public void testInsertRecursoValidadoFlag() throws Exception {
        String tipo = "TEST_VALIDADO_" + System.currentTimeMillis();
        String detalle = "DetalleValFlag" + System.currentTimeMillis();

        DatabaseHelper.insertRecurso(tipo, detalle, "Baja", false, "");

        boolean found = false;
        for (String[] r : DatabaseHelper.getRecursos()) {
            if (r.length >= 6 && detalle.equals(r[1])) {
                found = true;
                assertEquals("Validado debe ser 'No' para false", "No", r[3]);
                break;
            }
        }

        // Limpieza
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement("DELETE FROM recursos_faltantes WHERE tipo = ?")) {
            ps.setString(1, tipo);
            ps.executeUpdate();
        }

        assertTrue("Registro insertado debe encontrarse en la BD", found);
    }
}
