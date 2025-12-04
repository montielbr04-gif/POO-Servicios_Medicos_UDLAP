/*
 * Clase: TestAlumno
 * Breve: Conjunto de pruebas para el modelo de datos de Alumno.
 * Uso: Contiene tests para validar el comportamiento y atributos del modelo Alumno.
 */
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestAlumno {

	private Alumno alumnoTest;

	@Before
	public void setUp() {
		alumnoTest = new Alumno("Juan Pérez", 20, 70.5);
	}

	@Test
	public void testAlumnoConstructor() {
		assertEquals("Nombre debe ser Juan Pérez", "Juan Pérez", alumnoTest.getNombre());
		assertEquals("Edad debe ser 20", 20, alumnoTest.getEdad());
		assertEquals("Peso debe ser 70.5", 70.5, alumnoTest.getPeso(), 0.01);
		assertNull("Horario debe ser null inicialmente", alumnoTest.getHorarioCita());
	}

	@Test
	public void testAlumnoSetNombre() {
		alumnoTest.setNombre("María García");
		assertEquals("Nombre debe actualizar a María García", "María García", alumnoTest.getNombre());
	}

	@Test
	public void testAlumnoSetNombreNull() {
		alumnoTest.setNombre(null);
		assertNull("Nombre puede ser null", alumnoTest.getNombre());
	}

	@Test
	public void testAlumnoSetEdad() {
		alumnoTest.setEdad(25);
		assertEquals("Edad debe actualizar a 25", 25, alumnoTest.getEdad());
	}

	@Test
	public void testAlumnoSetEdadNegativa() {
		try {
			alumnoTest.setEdad(-5);
			fail("Se esperaba IllegalArgumentException al establecer edad negativa");
		} catch (IllegalArgumentException ex) {}
	}

	@Test
	public void testAlumnoSetEdadCero() {
		alumnoTest.setEdad(0);
		assertEquals("Edad cero se acepta", 0, alumnoTest.getEdad());
	}

	@Test
	public void testAlumnoSetPeso() {
		alumnoTest.setPeso(75.0);
		assertEquals("Peso debe actualizar a 75.0", 75.0, alumnoTest.getPeso(), 0.01);
	}

	@Test
	public void testAlumnoSetPesoNegativo() {
		try {
			alumnoTest.setPeso(-10.0);
			fail("Se esperaba IllegalArgumentException al establecer peso negativo");
		} catch (IllegalArgumentException ex) {}
	}

	@Test
	public void testAlumnoSetPesoCero() {
		alumnoTest.setPeso(0.0);
		assertEquals("Peso cero se acepta", 0.0, alumnoTest.getPeso(), 0.01);
	}

	@Test
	public void testAlumnoSetHorarioCita() {
		alumnoTest.setHorarioCita("08:00 AM - 08:30 AM");
		assertEquals("Horario debe ser 08:00 AM - 08:30 AM",
			"08:00 AM - 08:30 AM", alumnoTest.getHorarioCita());
	}

	@Test
	public void testAlumnoMostrarInfo() {
		String info = alumnoTest.mostrarInfo();
		assertTrue("Info debe contener nombre", info.contains("Juan Pérez"));
		assertTrue("Info debe contener edad", info.contains("20"));
		assertTrue("Info debe contener peso", info.contains("70.5"));
		assertTrue("Info debe contener 'Sin cita' si no hay horario", info.contains("Sin cita agendada"));
	}

	@Test
	public void testAlumnoMostrarInfoConCita() {
		alumnoTest.setHorarioCita("10:00 AM - 10:30 AM");
		String info = alumnoTest.mostrarInfo();
		assertTrue("Info debe contener horario de cita", info.contains("10:00 AM - 10:30 AM"));
	}

}
