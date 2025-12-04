/*
 * Clase: Urgencias
 * Breve: Ventana que muestra botones para llamadas de emergencia y servicios médicos.
 * Uso: Interfaz simple para simular llamadas o acciones de urgencia.
 */
import javax.swing.*;
import java.awt.*;
 
public class Urgencias extends JFrame {
 
    private JButton ServiciosButton, EmergenciaButton;
   
    public Urgencias() {
        setTitle("URGENCIAS");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
        // Servicios Médicos UDLAP
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
 
        ServiciosButton = new JButton("Llamar Servicios Médicos UDLAP");
        ServiciosButton.setEnabled(true);
        ServiciosButton.setBackground(new Color(250, 195, 115));
        ServiciosButton.setForeground(Color.BLACK);
        ServiciosButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        ServiciosButton.setPreferredSize(new Dimension(320, 48));
        ServiciosButton.setOpaque(true);
        ServiciosButton.setBorder(BorderFactory.createLineBorder(new Color(120,160,200), 1));
        add(ServiciosButton, gbc);
 
        // 911
        gbc.gridy = 1;
        EmergenciaButton = new JButton("Llamar 911");
        EmergenciaButton.setEnabled(true);
        EmergenciaButton.setBackground(new Color(250, 195, 115));
        EmergenciaButton.setForeground(Color.BLACK);
        EmergenciaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        EmergenciaButton.setPreferredSize(new Dimension(320, 48));
        EmergenciaButton.setOpaque(true);
        EmergenciaButton.setBorder(BorderFactory.createLineBorder(new Color(120,160,200), 1));
        add(EmergenciaButton, gbc);
 
        //Listeners de los botones
        ServiciosButton.addActionListener(e -> mostrarSimulacionServicios());
        EmergenciaButton.addActionListener(e -> mostrarSimulacionLlamada911());
    }
 
    //Simulación de llamada a Servicios Médicos UDLAP
    private void mostrarSimulacionServicios() {
        JOptionPane.showOptionDialog(
            this,
            "Llamando a Servicios Médicos UDLAP (simulación)...",
            "Simulación de llamada",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Colgar"},
            "Colgar"
        );
    }
 
    //Simulación de llamada al 911
    private void mostrarSimulacionLlamada911() {
        JOptionPane.showOptionDialog(
            this,
            "Llamando al 911 (simulación)...",
            "Simulación de llamada",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Colgar"},
            "Colgar"
        );
    }
 
    //Para el test
    public JButton getServiciosButton() {
    return ServiciosButton;
    }
 
    public JButton getEmergenciaButton() {
        return EmergenciaButton;
    }
}