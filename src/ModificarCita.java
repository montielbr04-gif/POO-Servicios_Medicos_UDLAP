/*
 * Clase: ModificarCita
 * Breve: Ventana que permite cambiar o cancelar citas existentes.
 * Uso: Permite seleccionar una cita y actualizar edad, peso u horario.
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ModificarCita extends JFrame {
    private JComboBox<String> comboNombres;
    private JTextField txtEdad, txtPeso;
    private JComboBox<String> comboHorarios;
    private JButton btnGuardar, btnCancelarCita;
    private JLabel warningLabel;
    private Alumno alumno;

    public ModificarCita(Alumno alumno) {
        this.alumno = alumno;
        setTitle("Modificar Cita");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(12, 15, 15, 15));

        // Espaciador superior
        panelCentro.add(Box.createVerticalGlue());

        // Título
        JLabel titleLabel = new JLabel("MODIFICAR CITA", SwingConstants.CENTER);
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

        // Nombre (ComboBox)
        JLabel nameLabel = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        camposPanel.add(nameLabel, gbc);

        List<String> nombres = DatabaseHelper.getNombresCitas();
        comboNombres = new JComboBox<>(nombres.toArray(new String[0]));
        comboNombres.setPreferredSize(new Dimension(200, 26));
        comboNombres.setSelectedIndex(-1);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        camposPanel.add(comboNombres, gbc);

        // Edad
        JLabel ageLabel = new JLabel("Edad:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        camposPanel.add(ageLabel, gbc);

        txtEdad = new JTextField(15);
        txtEdad.setPreferredSize(new Dimension(200, 26));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        camposPanel.add(txtEdad, gbc);

        // Peso
        JLabel weightLabel = new JLabel("Peso (kg):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        camposPanel.add(weightLabel, gbc);

        txtPeso = new JTextField(15);
        txtPeso.setPreferredSize(new Dimension(200, 26));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        camposPanel.add(txtPeso, gbc);

        // Horario
        JLabel horarioLabel = new JLabel("Horario:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        camposPanel.add(horarioLabel, gbc);

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
        comboHorarios.setPreferredSize(new Dimension(200, 26));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        camposPanel.add(comboHorarios, gbc);

        camposPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(camposPanel);
        panelCentro.add(Box.createVerticalStrut(12));

        // Mensaje de advertencia
        warningLabel = new JLabel("<html><font color='red'></font></html>");
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        warningLabel.setVisible(false);
        panelCentro.add(warningLabel);

        // Panel para botones
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(30, 100, 200));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setPreferredSize(new Dimension(130, 36));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarCambios());
        btnPanel.add(btnGuardar);

        btnCancelarCita = new JButton("Cancelar Cita");
        btnCancelarCita.setBackground(new Color(220, 50, 50));
        btnCancelarCita.setForeground(Color.WHITE);
        btnCancelarCita.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelarCita.setPreferredSize(new Dimension(130, 36));
        btnCancelarCita.setFocusPainted(false);
        btnCancelarCita.addActionListener(e -> cancelarCita());
        btnPanel.add(btnCancelarCita);

        btnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(btnPanel);

        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);

        // Preseleccionar si el Alumno viene con nombre
        if (alumno != null && alumno.getNombre() != null) {
            comboNombres.setSelectedItem(alumno.getNombre());
        }

        // Evento para cargar datos al seleccionar un nombre
        comboNombres.addActionListener(e -> cargarDatosCitaSeleccionada());
    }

    private void guardarCambios() {
        String nombre = (String) comboNombres.getSelectedItem();
        String edadStr = txtEdad.getText().trim();
        String pesoStr = txtPeso.getText().trim();
        int selectedIndex = comboHorarios.getSelectedIndex();

        // Validar campos
        if (nombre == null || nombre.isEmpty() || edadStr.isEmpty() || pesoStr.isEmpty()) {
            warningLabel.setText("<html><font color='red'>Todos los campos son requeridos</font></html>");
            warningLabel.setVisible(true);
            return;
        }

        if (selectedIndex == 0) {
            warningLabel.setText("<html><font color='red'>Debe seleccionar un horario válido</font></html>");
            warningLabel.setVisible(true);
            return;
        }

        warningLabel.setVisible(false);

        try {
            int edad = Integer.parseInt(edadStr);
            double peso = Double.parseDouble(pesoStr);

            // Rango realista para edad y peso
            int MIN_EDAD = 0;
            int MAX_EDAD = 120;
            double MIN_PESO = 1.0;
            double MAX_PESO = 300.0;

            if (edad < MIN_EDAD || edad > MAX_EDAD) {
                warningLabel.setText("<html><font color='red'>Edad debe estar entre " + MIN_EDAD + " y " + MAX_EDAD + "</font></html>");
                warningLabel.setVisible(true);
                return;
            }

            if (peso < MIN_PESO || peso > MAX_PESO) {
                warningLabel.setText("<html><font color='red'>Peso debe estar entre " + MIN_PESO + " y " + MAX_PESO + " kg</font></html>");
                warningLabel.setVisible(true);
                return;
            }

            // Actualizar objeto Alumno si existe
            if (alumno != null) {
                alumno.setNombre(nombre);
                alumno.setEdad(edad);
                alumno.setPeso(peso);
                alumno.setHorarioCita((String) comboHorarios.getSelectedItem());
            }

            // Verificar que el nuevo horario esté disponible (o pertenezca al mismo nombre)
            String nuevoHorario = (String) comboHorarios.getSelectedItem();
            boolean disponible = DatabaseHelper.isHorarioAvailable(nuevoHorario, nombre);
            if (!disponible) {
                warningLabel.setText("<html><font color='red'>El horario seleccionado ya está ocupado por otra persona</font></html>");
                warningLabel.setVisible(true);
                return;
            }

            // Actualizar en base de datos por nombre seleccionado
            try {
                DatabaseHelper.updateCitaFieldsByNombre(nombre, edad, peso, nuevoHorario);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this,
                "Cita modificada exitosamente!",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } catch (NumberFormatException ex) {
            warningLabel.setText("<html><font color='red'>Edad y Peso deben ser números válidos</font></html>");
            warningLabel.setVisible(true);
        }
    }

    // Cargar datos de la cita seleccionada en los campos
    private void cargarDatosCitaSeleccionada() {
        String nombre = (String) comboNombres.getSelectedItem();
        if (nombre == null || nombre.isEmpty()) {
            txtEdad.setText("");
            txtPeso.setText("");
            comboHorarios.setSelectedIndex(0);
            return;
        }
        List<String[]> citas = DatabaseHelper.getCitas();
        for (String[] cita : citas) {
            if (cita[0].equals(nombre)) {
                txtEdad.setText(cita[1]);
                txtPeso.setText(cita[2]);
                comboHorarios.setSelectedItem(cita[3]);
                break;
            }
        }
    }

    private void cancelarCita() {
        String nombre = (String) comboNombres.getSelectedItem();
        if (nombre == null || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione la cita que desea cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resultado = JOptionPane.showConfirmDialog(this,
            "¿Desea cancelar la cita de " + nombre + "?",
            "Cancelar Cita",
            JOptionPane.YES_NO_OPTION);

        if (resultado == JOptionPane.YES_OPTION) {
            // Borrar cita en base de datos
            try {
                DatabaseHelper.deleteCitaByNombre(nombre);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (alumno != null && nombre.equals(alumno.getNombre())) {
                alumno.setHorarioCita(null);
            }
            JOptionPane.showMessageDialog(this,
                "Cita cancelada exitosamente.",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
