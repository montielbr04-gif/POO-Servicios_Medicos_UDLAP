/*
 * Clase: FaltaRecursos
 * Breve: Ventana que muestra recursos faltantes y permite confirmar/guardar acciones.
 * Uso: Lista elementos en falta y guarda la solicitud en la base de datos.
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
 
public class FaltaRecursos extends JFrame {
 
    private JTable tablaRecursos;
    private JTextArea txtNotas;
    private JCheckBox chkValidado;
    private JButton btnGuardar;
    private JButton btnCancelar;
 
    public FaltaRecursos(List<RecursoDisponible> faltantes) {
 
        setTitle("Falta de recursos médicos - Gestión de acciones");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
 
        String[] columnas = {"Tipo", "Detalle", "Severidad"};
        Object[][] datos = new Object[faltantes.size()][3];
 
        for (int i = 0; i < faltantes.size(); i++) {
            RecursoDisponible r = faltantes.get(i);
            datos[i][0] = r.getTipo();           // Tipo
            datos[i][1] = r.getNombre();         // Detalle
            datos[i][2] = r.getDemanda();        // Severidad = demanda (Alta/Media/Baja)
        }
 
        tablaRecursos = new JTable(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Ajustar anchos de columnas
        tablaRecursos.getColumnModel().getColumn(0).setPreferredWidth(50);   // Tipo (pequeño)
        tablaRecursos.getColumnModel().getColumn(1).setPreferredWidth(150);  // Detalle (más largo)
        tablaRecursos.getColumnModel().getColumn(2).setPreferredWidth(50);   // Severidad (pequeño)
 
        JScrollPane scrollTabla = new JScrollPane(tablaRecursos);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Recursos faltantes detectados"));
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        panelTabla.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTabla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new BoxLayout(panelAcciones, BoxLayout.Y_AXIS));
        panelAcciones.setBorder(BorderFactory.createTitledBorder(
            "Confirmación de Autoridad"));
        panelAcciones.setAlignmentX(Component.CENTER_ALIGNMENT);

        chkValidado = new JCheckBox("Validado por personal médico autorizado");

        chkValidado.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelAcciones.add(Box.createVerticalStrut(10));
        panelAcciones.add(chkValidado);
        panelAcciones.add(Box.createVerticalGlue());

        // Añadir en orden vertical: tabla -> acciones
        panelCentro.add(panelTabla);
        panelCentro.add(Box.createVerticalStrut(12));
        panelCentro.add(panelAcciones);
 
        add(panelCentro, BorderLayout.CENTER);
 
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
 
        txtNotas = new JTextArea(7, 40);
        JScrollPane scrollNotas = new JScrollPane(txtNotas);
        scrollNotas.setBorder(BorderFactory.createTitledBorder(
                "Notas / acciones tomadas"));
 
        panelInferior.add(scrollNotas, BorderLayout.CENTER);
 
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        btnGuardar = new JButton("Guardar acción");
        btnGuardar.setBackground(new Color(30, 100, 200));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setFocusPainted(false);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 50, 50));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
 
        panelInferior.add(panelBotones, BorderLayout.SOUTH);
 
        add(panelInferior, BorderLayout.SOUTH);
 
 
        btnCancelar.addActionListener(e -> dispose());
 
        btnGuardar.addActionListener(e -> {
            int fila = tablaRecursos.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Selecciona un recurso de la tabla.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            // Usar String.valueOf para evitar ClassCastException si los valores no son String
            String tipo = String.valueOf(tablaRecursos.getValueAt(fila, 0));
            String detalle = String.valueOf(tablaRecursos.getValueAt(fila, 1));
            String severidad = String.valueOf(tablaRecursos.getValueAt(fila, 2));

            String notas = txtNotas.getText() != null ? txtNotas.getText().trim() : "";
            boolean validado = chkValidado.isSelected();

            String resumen = "Recurso seleccionado:\n"
                    + "- Tipo: " + tipo + "\n"
                    + "- Detalle: " + detalle + "\n"
                    + "- Severidad: " + severidad + "\n\n"
                    + "Validado por personal médico: " + (validado ? "Sí" : "No") + "\n\n"
                    + "Notas registradas:\n" + notas;

            int opcion = JOptionPane.showConfirmDialog(this, resumen + "\n\n¿Confirmar y guardar?", "Confirmar acción", JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) return;

            // Guardar en la base de datos con manejo explícito de errores
            try {
                DatabaseHelper.insertRecurso(tipo, detalle, severidad, validado, notas);
                JOptionPane.showMessageDialog(this, "Acción guardada correctamente.", "OK", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar selección y notas para evitar duplicados accidentales
                tablaRecursos.clearSelection();
                txtNotas.setText("");
                chkValidado.setSelected(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}