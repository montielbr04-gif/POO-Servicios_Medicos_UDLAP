/*
 * Clase: VentanaAlumno
 * Breve: Panel principal para alumnos con botones a funciones como agendar o modificar cita.
 * Uso: Interfaz de usuario para estudiantes que usan los servicios médicos.
 */
import javax.swing.*;
import java.awt.*;

public class VentanaAlumno extends JFrame {

    private JButton AgendarButton, ChatButton, UrgenciaButton, ModificarButton;
    public Alumno alumno;
    
    public VentanaAlumno(Alumno alumno) {
        this.alumno = alumno;
        setTitle("Servicios Médicos UDLAP");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(12, 15, 15, 15));

        // Espaciador superior para centrar verticalmente
        panelCentro.add(Box.createVerticalGlue());

        // Título
        JLabel titleLabel = new JLabel("SERVICIOS MÉDICOS UDLAP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 60, 90));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        panelCentro.add(titleLabel);

        //Agendar Cita
        AgendarButton = new JButton("Agendar Cita");
        AgendarButton.setEnabled(true);
        AgendarButton.setBackground(new Color(250, 195, 115));
        AgendarButton.setForeground(Color.BLACK);
        AgendarButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        AgendarButton.setPreferredSize(new Dimension(280, 40));
        AgendarButton.setMaximumSize(new Dimension(280, 40));
        AgendarButton.setOpaque(true);
        AgendarButton.setFocusPainted(false);
        AgendarButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        AgendarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        AgendarButton.addActionListener(e -> {
            AgendarCita agendarWindow = new AgendarCita(alumno);
            agendarWindow.setVisible(true);
        });
        panelCentro.add(AgendarButton);
        panelCentro.add(Box.createVerticalStrut(8));

        //Modificar Cita
        ModificarButton = new JButton("Modificar Cita");
        ModificarButton.setEnabled(true);
        ModificarButton.setBackground(new Color(250, 195, 115));
        ModificarButton.setForeground(Color.BLACK);
        ModificarButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ModificarButton.setPreferredSize(new Dimension(280, 40));
        ModificarButton.setMaximumSize(new Dimension(280, 40));
        ModificarButton.setOpaque(true);
        ModificarButton.setFocusPainted(false);
        ModificarButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        ModificarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ModificarButton.addActionListener(e -> {
            ModificarCita modificarWindow = new ModificarCita(alumno);
            modificarWindow.setVisible(true);
        });
        panelCentro.add(ModificarButton);
        panelCentro.add(Box.createVerticalStrut(8));

        //ChatBot
        ChatButton = new JButton("Chatbot");
        ChatButton.setEnabled(true);
        ChatButton.setBackground(new Color(250, 195, 115));
        ChatButton.setForeground(Color.BLACK);
        ChatButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ChatButton.setPreferredSize(new Dimension(280, 40));
        ChatButton.setMaximumSize(new Dimension(280, 40));
        ChatButton.setOpaque(true);
        ChatButton.setFocusPainted(false);
        ChatButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        ChatButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ChatButton.addActionListener(e -> {
            Chatbot chatWindow = new Chatbot();
            chatWindow.setVisible(true);
        });
        panelCentro.add(ChatButton);
        panelCentro.add(Box.createVerticalStrut(8));

        //Urgencias
        UrgenciaButton = new JButton("Urgencia");
        UrgenciaButton.setEnabled(true);
        UrgenciaButton.setBackground(new Color(250, 195, 115));
        UrgenciaButton.setForeground(Color.BLACK);
        UrgenciaButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        UrgenciaButton.setPreferredSize(new Dimension(280, 40));
        UrgenciaButton.setMaximumSize(new Dimension(280, 40));
        UrgenciaButton.setOpaque(true);
        UrgenciaButton.setFocusPainted(false);
        UrgenciaButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        UrgenciaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        UrgenciaButton.addActionListener(e -> {
            Urgencias urgencias = new Urgencias();
            urgencias.setVisible(true);
        });
        panelCentro.add(UrgenciaButton);

        // Espaciador inferior para centrar verticalmente
        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);
	}
}
