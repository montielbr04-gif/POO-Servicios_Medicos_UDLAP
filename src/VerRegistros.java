/*
 * Clase: VerRegistros
 * Breve: Ventana con pestañas para ver los registros guardados (recursos, sobrecarga, citas).
 * Uso: Muestra el contenido de la base de datos en tablas y permite refrescar la vista.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VerRegistros extends JFrame {

    public VerRegistros() {
        setTitle("Registros - Base de datos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        // Recursos
        String[] colsRec = {"Tipo","Detalle","Severidad","Validado","Notas","Fecha"};
        DefaultTableModel modelRec = new DefaultTableModel(colsRec, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tablaRec = new JTable(modelRec);
        tabs.addTab("Recursos faltantes", new JScrollPane(tablaRec));

        // Sobrecarga
        String[] colsSob = {"Nombre","Síntomas","Prioridad","Acción","Fecha"};
        DefaultTableModel modelSob = new DefaultTableModel(colsSob, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tablaSob = new JTable(modelSob);
        tabs.addTab("Sobrecarga", new JScrollPane(tablaSob));

        // Citas
        String[] colsCit = {"Nombre","Edad","Peso","Horario","Fecha"};
        DefaultTableModel modelCit = new DefaultTableModel(colsCit, 0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tablaCit = new JTable(modelCit);
        tabs.addTab("Citas", new JScrollPane(tablaCit));

        add(tabs, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefresh = new JButton("Actualizar");
        JButton btnClose = new JButton("Cerrar");
        bottom.add(btnRefresh);
        bottom.add(btnClose);
        add(bottom, BorderLayout.SOUTH);

        btnClose.addActionListener(e -> dispose());
        btnRefresh.addActionListener(e -> {
            // recargar
            modelRec.setRowCount(0);
            modelSob.setRowCount(0);
            modelCit.setRowCount(0);

            List<String[]> recursos = DatabaseHelper.getRecursos();
            for (String[] r : recursos) modelRec.addRow(r);

            List<String[]> sobrecarga = DatabaseHelper.getSobrecarga();
            for (String[] s : sobrecarga) modelSob.addRow(s);

            List<String[]> citas = DatabaseHelper.getCitas();
            for (String[] c : citas) modelCit.addRow(c);
        });

        // load initially
        btnRefresh.doClick();
    }
}
