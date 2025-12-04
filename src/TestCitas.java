/*
 * Clase: TestCitas
 * Breve: Pruebas unitarias para las operaciones de citas (reservar, actualizar, borrar).
 * Uso: Verifica que las funciones de `DatabaseHelper` para citas funcionen bien.
 */
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.List;

public class TestCitas {

    @BeforeClass
    public static void setup() {
        DatabaseHelper.createTables();
    }

    @Test
    public void testReservarCitaInsertaEnBDYElimina() {
        String nombre = "Test Citas A";
        String horario = "08:00 AM - 08:30 AM";
        // Asegurar que el horario esté libre para la prueba
        DatabaseHelper.deleteCitaByHorario(horario);
        // Intentar reservar
        boolean ok = DatabaseHelper.reservarOCita(nombre, 30, 70.0, horario);
        assertTrue("La reserva debe retornar true si el horario está disponible", ok);

        // Verificar que exista en la BD
        List<String[]> citas = DatabaseHelper.getCitas();
        boolean encontrado = false;
        for (String[] c : citas) {
            if (c[0].equals(nombre) && horario.equals(c[3])) {
                encontrado = true;
                break;
            }
        }
        assertTrue("La cita reservada debe encontrarse en la BD", encontrado);

        // Limpieza
        DatabaseHelper.deleteCitaByNombre(nombre);
        List<String[]> despues = DatabaseHelper.getCitas();
        boolean sigue = false;
        for (String[] c : despues) if (c[0].equals(nombre)) { sigue = true; break; }
        assertFalse("La cita debe eliminarse en la limpieza", sigue);
    }

    @Test
    public void testActualizarCitaEnBD() {
        String nombre = "UpdateTest";
        // Asegurar limpieza previa
        DatabaseHelper.deleteCitaByNombre(nombre);
        DatabaseHelper.deleteCitaByHorario("08:00 AM - 08:30 AM");
        DatabaseHelper.deleteCitaByHorario("10:00 AM - 10:30 AM");
        DatabaseHelper.reservarOCita(nombre, 25, 65.0, "08:00 AM - 08:30 AM");

        // Actualizar por nombre
        DatabaseHelper.updateCitaByNombre(nombre, nombre, 26, 66.0, "10:00 AM - 10:30 AM");

        List<String[]> citas = DatabaseHelper.getCitas();
        boolean actualizado = false;
        for (String[] c : citas) {
            if (c[0].equals(nombre) && "10:00 AM - 10:30 AM".equals(c[3]) && Integer.parseInt(c[1]) == 26) {
                actualizado = true;
                break;
            }
        }
        assertTrue("La cita debe actualizarse en la BD", actualizado);

        // Limpieza
        DatabaseHelper.deleteCitaByNombre(nombre);
        List<String[]> despues = DatabaseHelper.getCitas();
        boolean sigue = false;
        for (String[] c : despues) if (c[0].equals(nombre)) { sigue = true; break; }
        assertFalse("La cita debe eliminarse en la limpieza", sigue);
    }

    @Test
    public void testHorarioDisponibilidadViaBD() {
        String nombre = "BusyUser";
        String horario = "08:00 AM - 08:30 AM";
        // Asegurar que el horario esté libre y luego reservarlo
        DatabaseHelper.deleteCitaByHorario(horario);
        DatabaseHelper.reservarOCita(nombre, 22, 60.0, horario);

        // Para otro nombre, el horario debe estar ocupado
        boolean disponible = DatabaseHelper.isHorarioAvailable(horario, "OtherUser");
        assertFalse("Horario ocupado debería reportarse como no disponible", disponible);

        // Limpieza
        DatabaseHelper.deleteCitaByNombre(nombre);
        List<String[]> despues = DatabaseHelper.getCitas();
        boolean sigue = false;
        for (String[] c : despues) if (c[0].equals(nombre)) { sigue = true; break; }
        assertFalse("La cita debe eliminarse en la limpieza", sigue);
    }

    @Test
    public void testUpsertCitaInsertaYActualiza() {
        String nombre = "UpsertUser";
        // Asegurar limpieza previa en horarios objetivo
        DatabaseHelper.deleteCitaByNombre(nombre);
        DatabaseHelper.deleteCitaByHorario("08:00 AM - 08:30 AM");
        DatabaseHelper.deleteCitaByHorario("10:00 AM - 10:30 AM");

        // Usar horarios libres (08:00 y 10:00) para evitar colisiones con datos existentes
        DatabaseHelper.upsertCita(nombre, 20, 60.0, "08:00 AM - 08:30 AM");

        // Volver a upsert con mismo nombre y horario distinto (libre)
        DatabaseHelper.upsertCita(nombre, 21, 61.5, "10:00 AM - 10:30 AM");

        List<String[]> citas = DatabaseHelper.getCitas();
        boolean found = false;
        for (String[] c : citas) {
            if (c[0].equals(nombre) && "10:00 AM - 10:30 AM".equals(c[3]) && Integer.parseInt(c[1]) == 21) {
                found = true;
                break;
            }
        }
        assertTrue("Upsert debe actualizar la cita existente para el mismo nombre", found);

        // Limpieza
        DatabaseHelper.deleteCitaByNombre(nombre);
        List<String[]> despues = DatabaseHelper.getCitas();
        boolean sigue = false;
        for (String[] c : despues) if (c[0].equals(nombre)) { sigue = true; break; }
        assertFalse("La cita debe eliminarse en la limpieza", sigue);
    }
}
