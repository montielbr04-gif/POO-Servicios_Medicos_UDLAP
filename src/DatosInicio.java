/*
 * Clase: DatosInicio
 * Breve: Contiene credenciales de ejemplo y métodos para validar usuario/rol.
 * Uso: Permite comprobar si un ID/contraseña corresponde a admin o alumno.
 */
public class DatosInicio {
	// Credenciales fijas (simulan una pequeña "base de datos")
	private static final String ADMIN_ID = "12345";
	private static final String ADMIN_PW = "Admin.1";
	private static final String ALUMNO_ID = "123456";
	private static final String ALUMNO_PW = "Alumno.1";

	public static boolean validar(String id, String contra) {
		if (id == null || contra == null) return false;
		id = id.trim();
		contra = contra.trim();
		if (ADMIN_ID.equals(id) && ADMIN_PW.equals(contra)) return true;
		if (ALUMNO_ID.equals(id) && ALUMNO_PW.equals(contra)) return true;
		return false;
	}

	public static boolean esAdmin(String id) {
		return ADMIN_ID.equals(id);
	}

	public static boolean esAlumno(String id) {
		return ALUMNO_ID.equals(id);
	}
}
