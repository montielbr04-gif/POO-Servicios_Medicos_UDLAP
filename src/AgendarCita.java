/*
 * Clase: AgendarCita
 * Breve: Ventana para que un alumno ponga su nombre, edad y peso antes de elegir horario.
 * Uso: Reúne datos iniciales y avanza al selector de horarios.
 */
import javax.swing.*;
import java.awt.*;

public class AgendarCita extends JFrame {
    private JTextField txtNombre, txtEdad, txtPeso;
    private JButton btnContinuar;
    private JLabel warningLabel;
    private Alumno alumno;

    public AgendarCita(Alumno alumno) {
        this.alumno = alumno;
        setTitle("Agendar Cita - Registro");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(12, 15, 15, 15));

        // Espaciador superior
        panelCentro.add(Box.createVerticalGlue());

        // Título
        JLabel titleLabel = new JLabel("REGISTRO DE DATOS", SwingConstants.CENTER);
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

        // Nombre
        JLabel nameLabel = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        camposPanel.add(nameLabel, gbc);

        txtNombre = new JTextField(15);
        txtNombre.setPreferredSize(new Dimension(200, 26));
        txtNombre.setText(alumno.getNombre() != null ? alumno.getNombre() : "");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        camposPanel.add(txtNombre, gbc);

        // Edad
        JLabel ageLabel = new JLabel("Edad:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        camposPanel.add(ageLabel, gbc);

        txtEdad = new JTextField(15);
        txtEdad.setPreferredSize(new Dimension(200, 26));
        txtEdad.setText(alumno.getEdad() > 0 ? String.valueOf(alumno.getEdad()) : "");
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
        txtPeso.setText(alumno.getPeso() > 0 ? String.valueOf(alumno.getPeso()) : "");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        camposPanel.add(txtPeso, gbc);

        camposPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(camposPanel);
        panelCentro.add(Box.createVerticalStrut(12));

        // Mensaje de advertencia
        warningLabel = new JLabel("<html><font color='red'>Todos los campos son requeridos</font></html>");
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        warningLabel.setVisible(false);
        panelCentro.add(warningLabel);

        // Panel para botón
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnContinuar = new JButton("Continuar");
        btnContinuar.setBackground(new Color(30, 100, 200));
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnContinuar.setPreferredSize(new Dimension(260, 40));
        btnContinuar.setFocusPainted(false);
        btnContinuar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnContinuar.addActionListener(e -> validarYContinuar());
        btnPanel.add(btnContinuar);
        btnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(btnPanel);

        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);
    }

    private void validarYContinuar() {
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String pesoStr = txtPeso.getText().trim();

        // Validar que todos los campos estén llenos
        if (nombre.isEmpty() || edadStr.isEmpty() || pesoStr.isEmpty()) {
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

            // Guardar datos en el objeto Alumno
            alumno.setNombre(nombre);
            alumno.setEdad(edad);
            alumno.setPeso(peso);

            // Abrir ventana de selección de horario
            SeleccionarHorario horarioWindow = new SeleccionarHorario(alumno);
            horarioWindow.setVisible(true);
            dispose();
        } catch (NumberFormatException ex) {
            warningLabel.setText("<html><font color='red'>Edad y Peso deben ser números válidos</font></html>");
            warningLabel.setVisible(true);
        }
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public int getEdad() {
        return Integer.parseInt(txtEdad.getText());
    }

    public double getPeso() {
        return Double.parseDouble(txtPeso.getText());
    }

    public JButton getBtnContinuar() {
        return btnContinuar;
    }
}