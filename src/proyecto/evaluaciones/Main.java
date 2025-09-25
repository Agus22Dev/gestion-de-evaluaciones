package proyecto.evaluaciones;

import proyecto.evaluaciones.ui.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // SIA2.3 - Configurar Look and Feel para mejor apariencia
        try {
            // Usar el Look and Feel nativo del sistema operativo
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si no se puede establecer, usar el por defecto
            System.err.println("No se pudo establecer el Look and Feel: " + e.getMessage());
        }
        
        // SIA2.3 - Ejecutar interfaz gráfica en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("=== Sistema de Evaluaciones Digitales ===");
                System.out.println("Iniciando interfaz gráfica...");
                
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
                
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
                
                // Mostrar error al usuario
                JOptionPane.showMessageDialog(null, 
                    "Error crítico al iniciar la aplicación:\n" + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}

