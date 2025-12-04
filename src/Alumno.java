/*
 * Clase: Alumno
 * Breve: Guarda los datos de un estudiante: nombre, edad, peso y horario.
 * Uso: Modelo simple para manejar la información del alumno en la app.
 */
public class Alumno {
    private String nombre;
    private int edad;
    private double peso;
    private String horarioCita;

    public Alumno(String nombre, int edad, double peso) {
        this.nombre = nombre;
        setEdad(edad);
        setPeso(peso);
        this.horarioCita = null;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        int MIN_EDAD = 0;
        int MAX_EDAD = 120;
        if (edad < MIN_EDAD || edad > MAX_EDAD) {
            throw new IllegalArgumentException("Edad debe estar entre " + MIN_EDAD + " y " + MAX_EDAD);
        }
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        double MIN_PESO = 0.0; // kg (permitir 0 como valor inicial)
        double MAX_PESO = 300.0; // kg
        if (peso < MIN_PESO || peso > MAX_PESO) {
            throw new IllegalArgumentException("Peso debe estar entre " + MIN_PESO + " y " + MAX_PESO + " kg");
        }
        this.peso = peso;
    }

    public String getHorarioCita() {
        return horarioCita;
    }

    public void setHorarioCita(String horarioCita) {
        this.horarioCita = horarioCita;
    }

    public String mostrarInfo() {
        return "Nombre: " + nombre + "\nEdad: " + edad + "\nPeso: " + peso + " kg" +
               (horarioCita != null ? "\nHorario de cita: " + horarioCita : "\nSin cita agendada");
    }
}
