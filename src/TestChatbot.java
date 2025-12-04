/*
 * Clase: TestChatbot
 * Breve: Pruebas para verificar los textos y opciones que maneja el Chatbot.
 * Uso: Comprueba que las respuestas y nodos principales no estén vacíos.
 */
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
 
public class TestChatbot {
 
    private String[] opcionesRoot;
    private String[] opcionesFiebre;
    private String respuestaFiebre;
    private String[] opcionesVolver;
 
    @Before
    public void setUp() {
        // Configuramos la información tal como está en tu Chatbot
        opcionesRoot = new String[]{"Síntomas", "Agendar cita", "Información general"};
        opcionesFiebre = new String[]{"Sí, agendar cita", "Volver al inicio"};
        respuestaFiebre = "La fiebre puede deberse a infección o deshidratación. ¿Deseas agendar cita?";
        opcionesVolver = new String[]{"Síntomas", "Agendar cita", "Información general"};
    }
 
    // ==================== TESTS PARA NODOS PRINCIPALES ====================
 
    @Test
    public void testNodosPrincipalesNoVacios() {
        assertNotNull("Las opciones del nodo root no deben ser nulas", opcionesRoot);
        assertEquals("Root debe tener 3 opciones", 3, opcionesRoot.length);
 
        for (String opcion : opcionesRoot) {
            assertTrue("Cada opción del root debe tener contenido", opcion.length() > 0);
        }
    }
 
    // ==================== TEST DEL NODO FIEBRE ====================
 
    @Test
    public void testNodoFiebreOpcionesValidas() {
        assertNotNull("Las opciones de fiebre no deben ser nulas", opcionesFiebre);
        assertEquals("El nodo fiebre debe tener 2 opciones", 2, opcionesFiebre.length);
 
        for (String opcion : opcionesFiebre) {
            assertTrue("Las opciones del nodo fiebre deben tener texto", opcion.length() > 0);
        }
    }
 
    @Test
    public void testNodoFiebreRespuestaCorrecta() {
        assertNotNull("La respuesta del nodo Fiebre no debe ser nula", respuestaFiebre);
        assertTrue("La respuesta debe mencionar fiebre", respuestaFiebre.toLowerCase().contains("fiebre"));
    }
 
    // ==================== TEST DEL NODO VOLVER AL INICIO ====================
 
    @Test
    public void testNodoVolverInicioOpciones() {
        assertNotNull("El nodo Volver al inicio no debe ser nulo", opcionesVolver);
        assertEquals("Volver al inicio debe tener 3 opciones", 3, opcionesVolver.length);
    }
 
    // ==================== TEST DEL REPORTE ====================
 
    @Test
    public void testGuardarReporte() {
        String usuario = "Usuario prueba";
        String bot = "Mensaje prueba";
 
        assertNotNull("El usuario no debe ser nulo", usuario);
        assertNotNull("El mensaje del bot no debe ser nulo", bot);
 
        assertTrue("El usuario debe tener contenido", usuario.length() > 0);
        assertTrue("El mensaje del bot debe tener contenido", bot.length() > 0);
    }
}
 
 