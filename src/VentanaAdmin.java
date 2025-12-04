/*
 * Clase: VentanaAdmin
 * Breve: Panel para usuarios administradores con opciones de gestión.
 * Uso: Permite acceder a funciones como ver registros o solicitar recursos.
 */
import javax.swing.*;
import java.awt.*;

public class VentanaAdmin extends JFrame {

    private JButton RecursosButton, SobrecargaButton;
    
	public VentanaAdmin() {
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
        JLabel titleLabel = new JLabel("PANEL ADMINISTRATIVO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 60, 90));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        panelCentro.add(titleLabel);

        // Ver registros guardados
        JButton verRegistrosBtn = new JButton("Ver Registros");
        verRegistrosBtn.setEnabled(true);
        verRegistrosBtn.setBackground(new Color(30, 100, 200));
        verRegistrosBtn.setForeground(Color.WHITE);
        verRegistrosBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        verRegistrosBtn.setPreferredSize(new Dimension(280, 40));
        verRegistrosBtn.setMaximumSize(new Dimension(280, 40));
        verRegistrosBtn.setOpaque(true);
        verRegistrosBtn.setFocusPainted(false);
        verRegistrosBtn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        verRegistrosBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        verRegistrosBtn.addActionListener(e -> {
            VerRegistros viewer = new VerRegistros();
            viewer.setVisible(true);
        });
        panelCentro.add(verRegistrosBtn);
        panelCentro.add(Box.createVerticalStrut(8));

        //Falta de recursos
        RecursosButton = new JButton("Solicitar Recursos");
        RecursosButton.setEnabled(true);
        RecursosButton.setBackground(new Color(250, 195, 115));
        RecursosButton.setForeground(Color.BLACK);
        RecursosButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        RecursosButton.setPreferredSize(new Dimension(280, 40));
        RecursosButton.setMaximumSize(new Dimension(280, 40));
        RecursosButton.setOpaque(true);
        RecursosButton.setFocusPainted(false);
        RecursosButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        RecursosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        RecursosButton.addActionListener(e -> {InventarioRecursos inventario = new InventarioRecursos();
        java.util.List<RecursoDisponible> faltantes = inventario.getEnFalta();
        FaltaRecursos ventana = new FaltaRecursos(faltantes);
        ventana.setVisible(true);
        });
        panelCentro.add(RecursosButton);
        panelCentro.add(Box.createVerticalStrut(8));

        //Sobrecarga
        SobrecargaButton = new JButton("Modo Sobrecarga");
        SobrecargaButton.setEnabled(true);
        SobrecargaButton.setBackground(new Color(250, 195, 115));
        SobrecargaButton.setForeground(Color.BLACK);
        SobrecargaButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        SobrecargaButton.setPreferredSize(new Dimension(280, 40));
        SobrecargaButton.setMaximumSize(new Dimension(280, 40));
        SobrecargaButton.setOpaque(true);
        SobrecargaButton.setFocusPainted(false);
        SobrecargaButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        SobrecargaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        SobrecargaButton.addActionListener(e -> {
            Sobrecarga sobrecargaWindow = new Sobrecarga();
            sobrecargaWindow.setVisible(true);
        });
        panelCentro.add(SobrecargaButton);

        panelCentro.add(Box.createVerticalStrut(8));

        // (Botón de reinicio de BD eliminado por seguridad)

        // Espaciador inferior para centrar verticalmente
        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);

        
	}
}
