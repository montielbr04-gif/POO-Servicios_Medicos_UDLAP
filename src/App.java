/*
 * Clase: App
 * Breve: Punto de entrada de la aplicación. Crea las tablas y muestra la ventana principal.
 */
public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Nos aseguramos que las tablas existan
                DatabaseHelper.createTables();

                VentanaGeneral ventana = new VentanaGeneral();
                ventana.setVisible(true);
            }
        });
    }
}
