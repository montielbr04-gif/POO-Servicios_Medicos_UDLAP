/*
 * Clase: Sobrecarga
 * Breve: Ventana para registrar pacientes en modo de alta demanda (sobrecarga).
 * Uso: Recoge nombre, síntomas y opción de atención y guarda en la base si es necesario.
 */
import javax.swing.*;
import java.awt.*;


public class Sobrecarga extends JFrame {
	private JTextField txtNombre;
	private JTextArea txtSintomas;
	private JComboBox<String> comboPrioridad;
	private JCheckBox campusCheck, hospitalCheck;
	private JButton btnRegistrar;

	public Sobrecarga() {
		setTitle("Modo Sobrecarga - Registro de Paciente");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		// Panel central con layout vertical para apariencia móvil
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
		panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		// Título
		JLabel titleLabel = new JLabel("MODO SOBRECARGA", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		titleLabel.setForeground(new Color(30, 60, 90));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
		panelCentro.add(titleLabel);

		// Panel de campos centrado
		JPanel camposPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		// Nombre del Paciente
		JLabel lblNombre = new JLabel("Nombre:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		camposPanel.add(lblNombre, gbc);
		txtNombre = new JTextField(15);
		txtNombre.setPreferredSize(new Dimension(200, 28));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		camposPanel.add(txtNombre, gbc);

		// Síntomas
		JLabel lblSintomas = new JLabel("Síntomas:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		camposPanel.add(lblSintomas, gbc);
		txtSintomas = new JTextArea(3, 15);
		txtSintomas.setLineWrap(true);
		txtSintomas.setWrapStyleWord(true);
		JScrollPane scrollSintomas = new JScrollPane(txtSintomas);
		scrollSintomas.setPreferredSize(new Dimension(200, 80));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		camposPanel.add(scrollSintomas, gbc);

		// Prioridad
		JLabel lblPrioridad = new JLabel("Prioridad:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		camposPanel.add(lblPrioridad, gbc);
		comboPrioridad = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
		comboPrioridad.setPreferredSize(new Dimension(200, 28));
		gbc.gridx = 1;
		gbc.gridy = 2;
		camposPanel.add(comboPrioridad, gbc);

		// Acción (Campus/Hospital)
		JLabel lblAccion = new JLabel("Acción:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.NORTH;
		camposPanel.add(lblAccion, gbc);
		JPanel panelChecks = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		campusCheck = new JCheckBox("Campus");
		hospitalCheck = new JCheckBox("Hospital");
		panelChecks.add(campusCheck);
		panelChecks.add(Box.createHorizontalStrut(12));
		panelChecks.add(hospitalCheck);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		camposPanel.add(panelChecks, gbc);

		camposPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentro.add(camposPanel);
		panelCentro.add(Box.createVerticalStrut(20));

		// Panel para el botón (centrado)
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnRegistrar = new JButton("Registrar al Paciente");
		btnRegistrar.setBackground(new Color(30, 100, 200));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegistrar.setPreferredSize(new Dimension(280, 44));
		btnRegistrar.setFocusPainted(false);
		btnRegistrar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		btnPanel.add(btnRegistrar);
		btnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentro.add(btnPanel);

		panelCentro.add(Box.createVerticalGlue());

		add(panelCentro, BorderLayout.CENTER);

		btnRegistrar.addActionListener(e -> registrarPaciente());
	}

	private void registrarPaciente() {
		String nombre = txtNombre.getText().trim();
		String sintomas = txtSintomas.getText().trim();
		String prioridad = (String) comboPrioridad.getSelectedItem();
		String accion = (campusCheck.isSelected() ? "Campus" : "") + (hospitalCheck.isSelected() ? (campusCheck.isSelected() ? ", " : "") + "Hospital" : "");

		if (nombre.isEmpty() || sintomas.isEmpty() || accion.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos los campos y al menos una acción son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String resumen = "Nombre: " + nombre +
						"\nSíntomas: " + sintomas +
						"\nPrioridad: " + prioridad +
						"\nAcción: " + accion;

		int res = JOptionPane.showConfirmDialog(this, resumen + "\n\n¿Registrar otro paciente?", "Paciente registrado", JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			txtNombre.setText("");
			txtSintomas.setText("");
			comboPrioridad.setSelectedIndex(0);
			campusCheck.setSelected(false);
			hospitalCheck.setSelected(false);
		} else {
			dispose();
		}
        // Guardar en base de datos
        try {
            DatabaseHelper.insertSobrecarga(nombre, sintomas, prioridad, accion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	// Métodos auxiliares para permitir pruebas unitarias sin interactuar con diálogos
	public boolean validarFormulario() {
		String nombre = txtNombre.getText().trim();
		String sintomas = txtSintomas.getText().trim();
		String accion = (campusCheck.isSelected() ? "Campus" : "") + (hospitalCheck.isSelected() ? (campusCheck.isSelected() ? ", " : "") + "Hospital" : "");
		return !nombre.isEmpty() && !sintomas.isEmpty() && !accion.isEmpty();
	}

	public void clearForm() {
		txtNombre.setText("");
		txtSintomas.setText("");
		comboPrioridad.setSelectedIndex(0);
		campusCheck.setSelected(false);
		hospitalCheck.setSelected(false);
	}
}
