/*
 * Clase: TestSM
 * Breve: Conjunto de pruebas básicas para varios componentes (datos, recursos, validaciones).
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSM {

    // ==================== TESTS PARA DatosInicio ====================
    @Test
    public void testValidarAdminCredencialesCorrectas() {
        assertTrue("Admin debe validarse con ID 12345 y PW Admin.1",
            DatosInicio.validar("12345", "Admin.1"));
    }

    @Test
    public void testValidarAdminIDIncorrecto() {
        assertFalse("Admin con ID incorrecto no debe validarse",
            DatosInicio.validar("99999", "Admin.1"));
    }

    @Test
    public void testValidarAdminPasswordIncorrecto() {
        assertFalse("Admin con PW incorrecta no debe validarse",
            DatosInicio.validar("12345", "WrongPassword"));
    }

    @Test
    public void testValidarAlumnoCredencialesCorrectas() {
        assertTrue("Alumno debe validarse con ID 123456 y PW Alumno.1",
            DatosInicio.validar("123456", "Alumno.1"));
    }

    @Test
    public void testValidarAlumnoIDIncorrecto() {
        assertFalse("Alumno con ID incorrecto no debe validarse",
            DatosInicio.validar("654321", "Alumno.1"));
    }

    @Test
    public void testValidarAlumnoPasswordIncorrecto() {
        assertFalse("Alumno con PW incorrecta no debe validarse",
            DatosInicio.validar("123456", "WrongPassword"));
    }

    @Test
    public void testValidarIDNull() {
        assertFalse("ID null no debe validarse",
            DatosInicio.validar(null, "Admin.1"));
    }

    @Test
    public void testValidarPasswordNull() {
        assertFalse("PW null no debe validarse",
            DatosInicio.validar("12345", null));
    }

    @Test
    public void testValidarAmbosCamposNull() {
        assertFalse("Ambos null no deben validarse",
            DatosInicio.validar(null, null));
    }

    // Tests ligeros de integración y otras piezas
    @Test
    public void testFlujoDatosLoginAAgendar() {
        assertTrue("Alumno debe validarse", DatosInicio.validar("123456", "Alumno.1"));
        Alumno alumno = new Alumno("Carlos López", 21, 72.0);
        assertNotNull("Alumno debe crearse", alumno);
        alumno.setHorarioCita("09:00 AM - 09:30 AM");
        assertEquals("Horario debe asignarse", "09:00 AM - 09:30 AM", alumno.getHorarioCita());
    }
}