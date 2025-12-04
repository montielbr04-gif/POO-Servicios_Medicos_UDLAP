/*
 * Clase: TestUrgencias
 * Breve: Pruebas para la ventana de Urgencias (titulo y botones).
 * Uso: Verifica que los botones existan, tengan texto y listeners.
 */
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
 
import javax.swing.JButton;
 
public class TestUrgencias {
 
    private Urgencias urgencias;
 
    @Before
    public void setUp() {
        // Crear la ventana de urgencias antes de cada test
        urgencias = new Urgencias();
    }
 
    @Test
    public void testTituloVentanaEsCorrecto() {
        // El título de la ventana debe ser "URGENCIAS"
        assertEquals("El título debe ser URGENCIAS",
                     "URGENCIAS", urgencias.getTitle());
    }
 
    @Test
    public void testBotonServiciosExisteYTextoCorrecto() {
        // El botón de Servicios Médicos debe existir y tener el texto correcto
        JButton btnServicios = urgencias.getServiciosButton();
 
        assertNotNull("El botón de Servicios Médicos no debe ser null", btnServicios);
        assertEquals("Texto del botón de Servicios Médicos incorrecto",
                     "Llamar Servicios Médicos UDLAP", btnServicios.getText());
        assertTrue("El botón de Servicios Médicos debe estar habilitado",
                   btnServicios.isEnabled());
    }
 
    @Test
    public void testBoton911ExisteYTextoCorrecto() {
        // El botón de 911 debe existir y tener el texto correcto
        JButton btn911 = urgencias.getEmergenciaButton();
 
        assertNotNull("El botón de 911 no debe ser null", btn911);
        assertEquals("Texto del botón 911 incorrecto",
                     "Llamar 911", btn911.getText());
        assertTrue("El botón de 911 debe estar habilitado",
                   btn911.isEnabled());
    }
 
    @Test
    public void testBotonesTienenListeners() {
        // Verificar que los botones tienen un ActionListener
        JButton btnServicios = urgencias.getServiciosButton();
        JButton btn911 = urgencias.getEmergenciaButton();
 
        assertTrue("Servicios Médicos debe tener un listener",
                   btnServicios.getActionListeners().length > 0);
 
        assertTrue("Llamar 911 debe tener un listener",
                   btn911.getActionListeners().length > 0);
    }
}
 
 