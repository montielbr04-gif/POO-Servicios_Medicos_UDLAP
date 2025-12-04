
/*
 * Clase: DatabaseHelper
 * Breve: Funciones para crear tablas y leer/escribir datos en la base SQLite.
 * Uso: Ayudante central para operaciones con tablas como citas, recursos y sobrecarga.
 */
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper {

    // Devuelve una lista de todos los nombres registrados en la tabla citas
    public static java.util.List<String> getNombresCitas() {
        java.util.List<String> nombres = new java.util.ArrayList<>();
        String sql = "SELECT nombre FROM citas ORDER BY nombre ASC";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    nombres.add(nombre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return nombres;
        }


    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void createTables() {
        try (Connection conn = ConexionSQLite.conectar()) {
            if (conn == null) return;
            try (Statement st = conn.createStatement()) {
                // recursos_faltantes
                st.executeUpdate("CREATE TABLE IF NOT EXISTS recursos_faltantes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tipo TEXT, detalle TEXT, severidad TEXT, validado INTEGER, notas TEXT, creado_at TEXT)");

                // sobrecarga_pacientes
                st.executeUpdate("CREATE TABLE IF NOT EXISTS sobrecarga_pacientes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT, sintomas TEXT, prioridad TEXT, accion TEXT, creado_at TEXT)");

                // citas (nombre unique, horario unique)
                st.executeUpdate("CREATE TABLE IF NOT EXISTS citas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT UNIQUE, edad INTEGER, peso REAL, horario TEXT UNIQUE, creado_at TEXT)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertRecurso(String tipo, String detalle, String severidad, boolean validado, String notas) {
        // Validar que los datos no sean nulos ni vacíos
        if (tipo == null || tipo.trim().isEmpty()) tipo = "(Sin tipo)";
        if (detalle == null || detalle.trim().isEmpty()) detalle = "(Sin detalle)";
        if (severidad == null || severidad.trim().isEmpty()) severidad = "(Sin severidad)";
        if (notas == null) notas = "";
        String sql = "INSERT INTO recursos_faltantes(tipo, detalle, severidad, validado, notas, creado_at) VALUES(?,?,?,?,?,?)";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ps.setString(2, detalle);
            ps.setString(3, severidad);
            ps.setInt(4, validado ? 1 : 0);
            ps.setString(5, notas);
            ps.setString(6, LocalDateTime.now().format(TF));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSobrecarga(String nombre, String sintomas, String prioridad, String accion) {
        String sql = "INSERT INTO sobrecarga_pacientes(nombre, sintomas, prioridad, accion, creado_at) VALUES(?,?,?,?,?)";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, sintomas);
            ps.setString(3, prioridad);
            ps.setString(4, accion);
            ps.setString(5, LocalDateTime.now().format(TF));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getHorariosOcupados() {
        Set<String> set = new HashSet<>();
        String sql = "SELECT horario FROM citas WHERE horario IS NOT NULL";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String h = rs.getString("horario");
                    if (h != null && !h.trim().isEmpty()) set.add(h);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static void upsertCita(String nombre, int edad, double peso, String horario) {
        // si existe por el nombre, lo actualiza, sino lo inserta
        String select = "SELECT id FROM citas WHERE nombre = ?";
        try (Connection conn = ConexionSQLite.conectar()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(select)) {
                ps.setString(1, nombre);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String update = "UPDATE citas SET edad = ?, peso = ?, horario = ?, creado_at = ? WHERE nombre = ?";
                        try (PreparedStatement pu = conn.prepareStatement(update)) {
                            pu.setInt(1, edad);
                            pu.setDouble(2, peso);
                            pu.setString(3, horario);
                            pu.setString(4, LocalDateTime.now().format(TF));
                            pu.setString(5, nombre);
                            pu.executeUpdate();
                        }
                        return;
                    }
                }
            }

            String insert = "INSERT INTO citas(nombre, edad, peso, horario, creado_at) VALUES(?,?,?,?,?)";
            try (PreparedStatement pi = conn.prepareStatement(insert)) {
                pi.setString(1, nombre);
                pi.setInt(2, edad);
                pi.setDouble(3, peso);
                pi.setString(4, horario);
                pi.setString(5, LocalDateTime.now().format(TF));
                pi.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Comprueba si un horario está disponible. Si 'nombreExclusion' no es null, se permite
    // que ese nombre tenga el horario (para permitir modificar su propio horario).
    public static boolean isHorarioAvailable(String horario, String nombreExclusion) {
        if (horario == null || horario.trim().isEmpty()) return true;
        String sql = "SELECT nombre FROM citas WHERE horario = ?" + (nombreExclusion != null ? " AND nombre != ?" : "") + " LIMIT 1";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, horario);
            if (nombreExclusion != null) ps.setString(2, nombreExclusion);
            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // En caso de error de BD, conservador: indicar que no está disponible
            return false;
        }
    }

    // Intenta reservar (insertar o actualizar) una cita sólo si el horario está disponible
    // Devuelve true si la operación se realizó correctamente, false si el horario ya estaba ocupado.
    public static boolean reservarOCita(String nombre, int edad, double peso, String horario) {
        // permitir si el horario está libre o pertenece al mismo nombre
        if (!isHorarioAvailable(horario, nombre)) {
            return false;
        }
        upsertCita(nombre, edad, peso, horario);
        return true;
    }

    public static void updateCitaByNombre(String originalName, String nombre, int edad, double peso, String horario) {
        String sql = "UPDATE citas SET nombre = ?, edad = ?, peso = ?, horario = ?, creado_at = ? WHERE nombre = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.setDouble(3, peso);
            ps.setString(4, horario);
            ps.setString(5, LocalDateTime.now().format(TF));
            ps.setString(6, originalName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualiza solo los campos edad/peso/horario para la cita identificada por nombre (no cambia nombre)
    public static void updateCitaFieldsByNombre(String nombreClave, int edad, double peso, String horario) {
        String sql = "UPDATE citas SET edad = ?, peso = ?, horario = ?, creado_at = ? WHERE nombre = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, edad);
            ps.setDouble(2, peso);
            ps.setString(3, horario);
            ps.setString(4, LocalDateTime.now().format(TF));
            ps.setString(5, nombreClave);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCitaByNombre(String nombre) {
        String sql = "DELETE FROM citas WHERE nombre = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Elimina citas por horario (útil para pruebas y limpieza controlada)
    public static void deleteCitaByHorario(String horario) {
        if (horario == null) return;
        String sql = "DELETE FROM citas WHERE horario = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, horario);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getRecursos() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT tipo, detalle, severidad, validado, notas, creado_at FROM recursos_faltantes ORDER BY creado_at DESC";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String tipo = rs.getString(1);
                String detalle = rs.getString(2);
                String severidad = rs.getString(3);
                String validado = rs.getInt(4) == 1 ? "Sí" : "No";
                String notas = rs.getString(5);
                String creado = rs.getString(6);
                list.add(new String[]{tipo, detalle, severidad, validado, notas, creado});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String[]> getSobrecarga() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT nombre, sintomas, prioridad, accion, creado_at FROM sobrecarga_pacientes ORDER BY creado_at DESC";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString(1);
                String sintomas = rs.getString(2);
                String prioridad = rs.getString(3);
                String accion = rs.getString(4);
                String creado = rs.getString(5);
                list.add(new String[]{nombre, sintomas, prioridad, accion, creado});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String[]> getCitas() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT nombre, edad, peso, horario, creado_at FROM citas ORDER BY creado_at DESC";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString(1);
                String edad = String.valueOf(rs.getInt(2));
                String peso = String.valueOf(rs.getDouble(3));
                String horario = rs.getString(4);
                String creado = rs.getString(5);
                list.add(new String[]{nombre, edad, peso, horario, creado});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
