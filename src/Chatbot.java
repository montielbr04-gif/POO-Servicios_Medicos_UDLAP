    /*
    * Clase: Chatbot
    * Breve: Interfaz sencilla tipo chat que ayuda con síntomas y guía para agendar citas.
    * Uso: Ofrece opciones predefinidas y guarda reportes simples en archivo.
    */
    import javax.swing.*;
    import javax.swing.border.EmptyBorder;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.io.FileWriter;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.HashMap;
    import java.util.Map;

    public class Chatbot extends JFrame {
        private final JTextArea taChat;
        private final JButton btnReportes;
        private final JPanel panelOpciones;
        private final Map<String, ConversationNode> nodes;

        public Chatbot() {
            setTitle("Chatbot Médico - UDLAP");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10,10));

            // Nodos de conversación (árbol simple)
            nodes = new HashMap<>();
            nodes.put("root", new ConversationNode("Selecciona una opción:", new String[]{"Síntomas","Agendar cita","Información general"}));
            nodes.put("Síntomas", new ConversationNode("Selecciona el síntoma más cercano:", new String[]{"Fiebre","Dolor de cabeza"}));
            nodes.put("Fiebre", new ConversationNode("La fiebre puede deberse a infección o deshidratación. ¿Deseas agendar cita?", new String[]{"Sí, agendar cita","Volver al inicio"}));
            nodes.put("Dolor de cabeza", new ConversationNode("Un dolor de cabeza puede ser por estrés. ¿Quieres más info?", new String[]{"Sí, más info","Volver al inicio"}));
            nodes.put("Náuseas", new ConversationNode("Para náuseas, se recomienda reposo e hidratación.", new String[]{"Volver al inicio"}));
            nodes.put("Sí, agendar cita", new ConversationNode("Puedes agendar desde el botón 'Agendar Cita' en tu panel.", new String[]{"Volver al inicio"}));
            nodes.put("Sí, más info", new ConversationNode("Prueba con analgésicos y descanso. Si empeora, agendar cita.", new String[]{"Volver al inicio"}));
            nodes.put("Agendar cita", new ConversationNode("¿Qué tipo de cita deseas?", new String[]{"Cita médica","Vacunación","Volver al inicio"}));
            nodes.put("Cita médica", new ConversationNode("Puedes agendar desde la opción 'Agendar Cita'.", new String[]{"Volver al inicio"}));
            nodes.put("Vacunación", new ConversationNode("Consulta disponibilidad en la clínica.", new String[]{"Volver al inicio"}));
            nodes.put("Información general", new ConversationNode("¿Qué información necesitas?", new String[]{"Horario","Contacto","Volver al inicio"}));
            nodes.put("Horario", new ConversationNode("Horario: Lunes-Viernes 8:00-17:00.", new String[]{"Volver al inicio"}));
            nodes.put("Contacto", new ConversationNode("Tel: 123-456-7890", new String[]{"Volver al inicio"}));
            nodes.put("Volver al inicio", new ConversationNode("Volviendo al menú principal.", new String[]{"Síntomas","Agendar cita","Información general"}));

            // PANEL SUPERIOR (BOTÓN DE REPORTES)
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            btnReportes = new JButton("Ver Reportes");
            btnReportes.setBackground(new Color(30, 100, 200));
            btnReportes.setForeground(Color.WHITE);
            btnReportes.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnReportes.setFocusPainted(false);
            btnReportes.addActionListener(this::abrirReportes);
            topPanel.add(btnReportes);
            add(topPanel, BorderLayout.NORTH);

            // CHAT
            taChat = new JTextArea();
            taChat.setEditable(false);
            taChat.setFont(new Font("Arial", Font.PLAIN, 14));
            taChat.setBorder(new EmptyBorder(10,10,10,10));
            add(new JScrollPane(taChat), BorderLayout.CENTER);

            // PANEL INFERIOR: opciones como botones
            panelOpciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            add(panelOpciones, BorderLayout.SOUTH);

            // Iniciar en root
            appendBot("Chatbot: Bienvenido al Chatbot.\n");
            updateOptions("root");
        }

        private void updateOptions(String key) {
            panelOpciones.removeAll();
            ConversationNode node = nodes.get(key);
            if (node == null) return;
            // mostrar respuesta del bot
            appendBot("Chatbot: " + node.response + "\n\n");

            for (String option : node.options) {
                JButton b = new JButton(option);
                b.setBackground(new Color(30, 100, 200));
                b.setForeground(Color.WHITE);
                b.setFont(new Font("Segoe UI", Font.BOLD, 12));
                b.setPreferredSize(new Dimension(150, 36));
                b.setOpaque(true);
                b.setFocusPainted(false);
                b.addActionListener(evt -> handleOption(option));
                panelOpciones.add(b);
            }
            panelOpciones.revalidate();
            panelOpciones.repaint();
        }

        private void handleOption(String option) {
            appendUser(option);
            // guardar reporte
            ConversationNode next = nodes.get(option);
            String response = next != null ? next.response : "";
            guardarReporte(option, response);
            // si opción es "Volver al inicio" o existe nodo, actualizar
            if (nodes.containsKey(option)) {
                updateOptions(option);
            } else {
                // no definido, volver al root
                updateOptions("root");
            }
        }

        private void appendUser(String text) {
            taChat.append("Usuario: " + text + "\n");
        }

        private void appendBot(String text) {
            taChat.append(text);
        }

        private void guardarReporte(String usuario, String bot) {
            try (FileWriter fw = new FileWriter("reportes.txt", true)) {
                fw.write("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "]\n");
                fw.write("Usuario: " + usuario + "\n");
                fw.write("Chatbot: " + bot + "\n\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void abrirReportes(ActionEvent e) {
            try {
                Desktop.getDesktop().open(new java.io.File("reportes.txt"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No hay reportes aún.");
            }
        }

        private static class ConversationNode {
            final String response;
            final String[] options;

            ConversationNode(String response, String[] options) {
                this.response = response;
                this.options = options;
            }
        }
    }

    