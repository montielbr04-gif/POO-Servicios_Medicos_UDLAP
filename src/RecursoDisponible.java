/*
 * Clase: RecursoDisponible
 * Breve: Modelo simple que describe un recurso (tipo, nombre, cantidad, demanda).
 * Uso: Se usa para listar los recursos y decidir si están en falta.
 */
public class RecursoDisponible {
 
    private String id;
    private String tipo;
    private String nombre;
    private int cantidadActual;
    private int cantidadMinima;
    private String demanda;
    private boolean sinAlternativa;
 
    public RecursoDisponible(String id, String tipo, String nombre,
                             int cantidadActual, int cantidadMinima,
                             String demanda, boolean sinAlternativa) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.cantidadActual = cantidadActual;
        this.cantidadMinima = cantidadMinima;
        this.demanda = demanda;
        this.sinAlternativa = sinAlternativa;
    }
 
    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public int getCantidadActual() { return cantidadActual; }
    public int getCantidadMinima() { return cantidadMinima; }
    public String getDemanda() { return demanda; }
    public boolean isSinAlternativa() { return sinAlternativa; }
 
    public boolean estaEnFalta() {
        return cantidadActual < cantidadMinima;
    }
}
 