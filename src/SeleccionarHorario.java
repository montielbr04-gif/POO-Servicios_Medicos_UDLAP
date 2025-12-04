/*
 * Clase: SeleccionarHorario
 * Breve: Ventana para elegir un horario disponible para la cita.
 * Uso: Muestra opciones de horario y reserva en la base de datos si está libre.
 */
import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class SeleccionarHorario extends JFrame {
    private JComboBox<String> comboHorarios;
    private JButton btnGuardarCita;
    private JLabel warningLabel;
    private Alumno alumno;

    public SeleccionarHorario(Alumno alumno) {
        this.alumno = alumno;
        setTitle("Seleccionar Horario");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(12, 15, 15, 15));

        // Espaciador superior
        panelCentro.add(Box.createVerticalGlue());

        // Título
        JLabel titleLabel = new JLabel("SELECCIONAR HORARIO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 60, 90));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        panelCentro.add(titleLabel);

        // Panel de campos
        JPanel camposPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label para horarios
        JLabel horarioLabel = new JLabel("Horario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        camposPanel.add(horarioLabel, gbc);

        // ComboBox de horarios disponibles
        String[] horariosDisponibles = {
            "-- Seleccione un horario --",
            "08:00 AM - 08:30 AM",
            "08:30 AM - 09:00 AM",
            "09:00 AM - 09:30 AM",
            "09:30 AM - 10:00 AM",
            "10:00 AM - 10:30 AM",
            "10:30 AM - 11:00 AM",
            "11:00 AM - 11:30 AM",
            "11:30 AM - 12:00 PM",
            "02:00 PM - 02:30 PM",
            "02:30 PM - 03:00 PM",
            "03:00 PM - 03:30 PM",
            "03:30 PM - 04:00 PM",
            "04:00 PM - 04:30 PM",
            "04:30 PM - 05:00 PM"
        };

        comboHorarios = new JComboBox<>(horariosDisponibles);
        // Filtrar horarios ocupados en la base de datos (excepto el horario que ya tiene este alumno)
        try {
            Set<String> ocupados = DatabaseHelper.getHorariosOcupados();
            String actual = alumno.getHorarioCita();
            for (int i = comboHorarios.getItemCount() - 1; i >= 0; i--) {
                String item = comboHorarios.getItemAt(i);
                if (item == null) continue;
                if (item.equals("-- Seleccione un horario --")) continue;
                if (ocupados.contains(item) && (actual == null || !actual.equals(item))) {
                    comboHorarios.removeItemAt(i);
                }
            }
        } catch (Exception ex) {
            // ignore DB errors and keep the default list
            ex.printStackTrace();
        }
        comboHorarios.setPreferredSize(new Dimension(200, 26));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        camposPanel.add(comboHorarios, gbc);

        camposPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(camposPanel);
        panelCentro.add(Box.createVerticalStrut(12));

        // Mensaje de advertencia
        warningLabel = new JLabel("<html><font color='red'>Debe seleccionar un horario válido</font></html>");
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        warningLabel.setVisible(false);
        panelCentro.add(warningLabel);

        // Panel para botón
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGuardarCita = new JButton("Guardar Cita");
        btnGuardarCita.setBackground(new Color(30, 100, 200));
        btnGuardarCita.setForeground(Color.WHITE);
        btnGuardarCita.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardarCita.setPreferredSize(new Dimension(260, 40));
        btnGuardarCita.setFocusPainted(false);
        btnGuardarCita.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnGuardarCita.addActionListener(e -> guardarCita());
        btnPanel.add(btnGuardarCita);
        btnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(btnPanel);

        // Información de resumen
        JLabel infoLabel = new JLabel("<html><small>Nombre: " + alumno.getNombre() + " | Edad: " + alumno.getEdad() + " | Peso: " + alumno.getPeso() + "</small></html>");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        panelCentro.add(infoLabel);

        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);
    }

    private void guardarCita() {
        int selectedIndex = comboHorarios.getSelectedIndex();

        // Validar que no sea la opción por defecto
        if (selectedIndex == 0) {
            warningLabel.setVisible(true);
            return;
        }

        warningLabel.setVisible(false);

        // Guardar horario en la base de datos (upsert) y en el objeto Alumno
        String horario = (String) comboHorarios.getSelectedItem();
        try {
            boolean ok = DatabaseHelper.reservarOCita(alumno.getNombre(), alumno.getEdad(), alumno.getPeso(), horario);
            if (!ok) {
                JOptionPane.showMessageDialog(this,
                    "El horario seleccionado ya está ocupado por otra cita. Por favor elija otro horario.",
                    "Horario no disponible",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        alumno.setHorarioCita(horario);

        // Mostrar confirmación
        JOptionPane.showMessageDialog(this,
            "Cita agendada exitosamente!\n\n" + alumno.mostrarInfo(),
            "Confirmación",
            JOptionPane.INFORMATION_MESSAGE);

        // Vaciar datos del alumno para que al volver a abrir el registro los campos estén limpios
        alumno.setNombre("");
        alumno.setEdad(0);
        alumno.setPeso(0.0);

        dispose();
    }

    public JComboBox<String> getComboHorarios() {
        return comboHorarios;
    }

    public JButton getBtnGuardarCita() {
        return btnGuardarCita;
    }
}
