/*
 * Clase: VentanaGeneral
 * Breve: Pantalla de inicio donde el usuario ingresa su ID y contraseña.
 * Uso: Valida credenciales y abre el panel correspondiente (admin o alumno).
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaGeneral extends JFrame{
    private JButton IniciarButton;
    private JLabel warningLabel;
    private JTextField txtID, txtContra;

    public VentanaGeneral(){
        setTitle("Servicios Médicos UDLAP");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JLabel titleLabel = new JLabel("SERVICIOS MÉDICOS UDLAP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 60, 90));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 6, 8, 6));
        add(titleLabel, gbc);

        // Panel para ID y Contraseña 
        JPanel camposPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCampos = new GridBagConstraints();
        gbcCampos.insets = new Insets(6, 6, 6, 6);
        gbcCampos.anchor = GridBagConstraints.CENTER;

        JLabel IDLabel = new JLabel("ID:");
        gbcCampos.gridx = 0;
        gbcCampos.gridy = 0;
        gbcCampos.anchor = GridBagConstraints.EAST;
        camposPanel.add(IDLabel, gbcCampos);

        txtID = new JTextField(15);
        txtID.setPreferredSize(new Dimension(140, 26));
        gbcCampos.gridx = 1;
        gbcCampos.gridy = 0;
        gbcCampos.anchor = GridBagConstraints.WEST;
        camposPanel.add(txtID, gbcCampos);

        JLabel contraLabel = new JLabel("Contraseña:");
        gbcCampos.gridx = 0;
        gbcCampos.gridy = 1;
        gbcCampos.anchor = GridBagConstraints.EAST;
        camposPanel.add(contraLabel, gbcCampos);

        txtContra = new JTextField(15);
        txtContra.setPreferredSize(new Dimension(140, 26));
        gbcCampos.gridx = 1;
        gbcCampos.gridy = 1;
        gbcCampos.anchor = GridBagConstraints.WEST;
        camposPanel.add(txtContra, gbcCampos);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(camposPanel, gbc);

        // Panel para el botón 
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        IniciarButton = new JButton("Iniciar Sesión");
        IniciarButton.setEnabled(true);
        IniciarButton.setBackground(new Color(30, 100, 200));
        IniciarButton.setForeground(Color.WHITE);
        IniciarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        IniciarButton.setPreferredSize(new Dimension(260, 40));
        IniciarButton.setFocusPainted(false);
        IniciarButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnPanel.add(IniciarButton);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnPanel, gbc);

        // Mensaje de advertencia
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        warningLabel = new JLabel("<html><font color='red'>Introduzca datos validos para ingresar</font></html>");
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        warningLabel.setVisible(false);
        add(warningLabel, gbc);

        //Olvidaste tu contraseña
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel olvidarLabel = new JLabel("<html><font color='blue'>¿Olvidaste tu contraseña o bloqueaste tu cuenta?</a></html>");
        olvidarLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        olvidarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(olvidarLabel, gbc);

        // Action listener para el botón: validar credenciales y abrir ventana correspondiente
        IniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = getID();
                if (id == null) id = ""; else id = id.trim();
                String contra = getContra();
                if (contra == null) contra = ""; else contra = contra.trim();

                // Ocultar advertencia por defecto
                warningLabel.setVisible(false);

                // Validar según rol esperado
                if (DatosInicio.esAdmin(id)) {
                    if (DatosInicio.validar(id, contra)) {
                        VentanaAdmin admin = new VentanaAdmin();
                        admin.setVisible(true);
                        dispose();
                        return;
                    } else {
                        warningLabel.setText("<html><font color='red'>ID o contraseña incorrectos</font></html>");
                        warningLabel.setVisible(true);
                        return;
                    }
                }

                if (DatosInicio.esAlumno(id)) {
                    if (DatosInicio.validar(id, contra)) {
                        Alumno alumno = new Alumno("", 0, 0.0);
                        VentanaAlumno ventanaAlumno = new VentanaAlumno(alumno);
                        ventanaAlumno.setVisible(true);
                        dispose();
                        return;
                    } else {
                        warningLabel.setText("<html><font color='red'>ID o contraseña incorrectos</font></html>");
                        warningLabel.setVisible(true);
                        return;
                    }
                }

                // Si no coincide ningún rol
                warningLabel.setText("<html><font color='red'>Introduzca datos validos para ingresar</font></html>");
                warningLabel.setVisible(true);
            }
        });
    }

    public String getID() {
            return txtID.getText();
    }

    public String getContra() {
            return txtContra.getText();
    }

    public JButton getBtnIniciar() {
        return IniciarButton;
    }
    
}
