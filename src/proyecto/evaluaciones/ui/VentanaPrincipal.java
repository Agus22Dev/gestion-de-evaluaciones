package proyecto.evaluaciones.ui;

import proyecto.evaluaciones.servicio.*;
import proyecto.evaluaciones.excepciones.PersistenciaException; // Agregar este import

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.File;

public class VentanaPrincipal extends JFrame {
    private final GestorBancos gestorBancos = new GestorBancos();
    private final GestorEvaluaciones gestorEvaluaciones = new GestorEvaluaciones();
    private final GestorNotas gestorNotas = new GestorNotas();
    
    private JPanel panelPrincipal;
    private JButton btnGestionPreguntas;
    private JButton btnGestionEvaluaciones;
    private JButton btnGestionNotas;
    private JButton btnSalir;
    
    public VentanaPrincipal() {
        
        initComponents();
        initEventHandlers();
        cargarDatosIniciales();
        inicializarDirectorios();
    }
    
    private void initComponents() {
        setTitle("Sistema de Evaluaciones Digitales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel lblTitulo = new JLabel("Sistema de Evaluaciones Digitales");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Botones principales
        btnGestionPreguntas = new JButton("Gestión de Preguntas y Bancos");
        btnGestionPreguntas.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelPrincipal.add(btnGestionPreguntas, gbc);
        
        btnGestionEvaluaciones = new JButton("Gestión de Evaluaciones");
        btnGestionEvaluaciones.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 2;
        panelPrincipal.add(btnGestionEvaluaciones, gbc);
        
        btnGestionNotas = new JButton("Gestión de Notas");
        btnGestionNotas.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 3;
        panelPrincipal.add(btnGestionNotas, gbc);
        
        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(300, 40));
        gbc.gridy = 4;
        panelPrincipal.add(btnSalir, gbc);
        
        add(panelPrincipal);
    }
    
    private void initEventHandlers() {
        btnGestionPreguntas.addActionListener(e -> {
            try {
                new VentanaGestionPreguntas(gestorBancos).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir gestión de preguntas: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnGestionEvaluaciones.addActionListener(e -> {
            try {
                new VentanaGestionEvaluaciones(gestorEvaluaciones, gestorBancos).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir gestión de evaluaciones: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnGestionNotas.addActionListener(e -> {
            try {
                new VentanaGestionNotas(gestorNotas).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir gestión de notas: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnSalir.addActionListener(e -> {
            try {
                // Asegurarnos que existan las carpetas
                File dirCsvs = new File("csvs");
                File dirReportes = new File("reportes");
                if (!dirCsvs.exists()) dirCsvs.mkdirs();
                if (!dirReportes.exists()) dirReportes.mkdirs();

                // Generar reporte
                String reportPath = "reportes/reporte_" + 
                    java.time.LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm")) + 
                    ".txt";
                
                // Guardar todos los datos
                try {
                    gestorBancos.guardarEnCSV();
                    gestorEvaluaciones.guardarEnCSV();
                    gestorNotas.guardarEnCSV("csvs/notas.csv");
                    
                    // Generar reporte final
                    gestorNotas.generarReporteTXT(reportPath, gestorBancos, gestorEvaluaciones);
                    
                    JOptionPane.showMessageDialog(this,
                        "Datos guardados y reporte generado en: " + reportPath,
                        "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        
                    System.exit(0);
                } catch (PersistenciaException ex) {
                    int opcion = JOptionPane.showConfirmDialog(this, 
                        "Error al guardar los datos:\n" + ex.getMensajeUsuario() + 
                        "\n\n¿Desea salir sin guardar?", 
                        "Error al Guardar", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.WARNING_MESSAGE);
                    
                    if (opcion == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void cargarDatosIniciales() {
        try {
            // Cargar datos desde CSV
            gestorBancos.cargarDesdeCSV();
            // Pass gestorBancos as parameter
            gestorEvaluaciones.cargarDesdeCSV(gestorBancos);
            gestorNotas.cargarDesdeCSV("csvs/notas.csv");
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(this,
                "Advertencia al cargar datos:\n" + e.getMensajeUsuario(),
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void inicializarDirectorios() {
        File dirReportes = new File("reportes");
        if (!dirReportes.exists()) {
            dirReportes.mkdirs();
        }
    }
}