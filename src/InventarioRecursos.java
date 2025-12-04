 
/*
 * Clase: InventarioRecursos
 * Breve: Representa el inventario local de recursos médicos y su estado.
 * Uso: Ayuda a saber qué items están por debajo del mínimo y deben solicitarse.
 */

import java.util.ArrayList;
import java.util.List;

public class InventarioRecursos {
 
    private List<RecursoDisponible> recursos;
 
    public InventarioRecursos() {
        recursos = new ArrayList<>();
 
        // DEMANDA ALTA
        recursos.add(new RecursoDisponible("R001", "Medicamento", "Paracetamol 500 mg (tabletas)", 5, 30, "Alta", false));
        recursos.add(new RecursoDisponible("R002", "Medicamento", "Ibuprofeno 400 mg (tabletas)", 10, 25, "Alta", false));
        recursos.add(new RecursoDisponible("R003", "Medicamento", "Sales de rehidratación oral", 3, 15, "Alta", true));
        recursos.add(new RecursoDisponible("R004", "Medicamento", "Solución salina (bolsa IV)", 2, 10, "Alta", true));
        recursos.add(new RecursoDisponible("R005", "Medicamento", "Omeprazol 20 mg (cápsulas)", 40, 30, "Alta", false));
        recursos.add(new RecursoDisponible("R006", "Material", "Guantes desechables", 50, 100, "Alta", true));
        recursos.add(new RecursoDisponible("R007", "Material", "Cubrebocas quirúrgicos", 80, 100, "Alta", false));
        recursos.add(new RecursoDisponible("R008", "Material", "Jeringas", 60, 80, "Alta", false));
        recursos.add(new RecursoDisponible("R009", "Material", "Gasas", 120, 100, "Alta", false));
        recursos.add(new RecursoDisponible("R010", "Material", "Vendas", 20, 40, "Alta", false));
 
        // DEMANDA MEDIA
        recursos.add(new RecursoDisponible("R011", "Material", "Alcohol etílico 70%", 25, 20, "Media", false));
        recursos.add(new RecursoDisponible("R012", "Material", "Catéter IV", 15, 20, "Media", false));
        recursos.add(new RecursoDisponible("R013", "Equipo", "Termómetro digital", 4, 4, "Media", false));
        recursos.add(new RecursoDisponible("R014", "Medicamento", "Salbutamol inhalador", 2, 8, "Media", true));
        
        // DEMANDA BAJA
        recursos.add(new RecursoDisponible("R015", "Medicamento", "Adrenalina (ampollas)", 1, 3, "Baja", true));
        recursos.add(new RecursoDisponible("R016", "Medicamento", "Antihistamínico inyectable", 5, 5, "Baja", false));
        recursos.add(new RecursoDisponible("R017", "Equipo", "Oxímetro de pulso", 1, 2, "Baja", false));
    }
 
    public List<RecursoDisponible> getTodos() {
        return recursos;
    }
 
    public List<RecursoDisponible> getEnFalta() {
        List<RecursoDisponible> faltantes = new ArrayList<>();
        for (RecursoDisponible r : recursos) {
            if (r.estaEnFalta()) {
                faltantes.add(r);
            }
        }
        return faltantes;
    }
}
 
 