package proyecto.evaluaciones.ui;

import proyecto.evaluaciones.servicio.*;
import proyecto.evaluaciones.dominio.*;
import proyecto.evaluaciones.excepciones.PersistenciaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private final GestorBancos gestorBancos;
    private final GestorEvaluaciones gestorEvaluaciones;
    private final GestorNotas gestorNotas;
    
    private JPanel panelPrincipal;
    private JButton btnGestionPreguntas;
    private JButton btnGestionEvaluaciones;
    private JButton btnGestionNotas;
    private JButton btnSalir;
    
    public VentanaPrincipal() {
        this.gestorBancos = new GestorBancos();
        this.gestorEvaluaciones = new GestorEvaluaciones();
        this.gestorNotas = new GestorNotas();
        
        initComponents();
        initEventHandlers();
        cargarDatosIniciales();
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
                // SIA2.8 - Try-catch para manejo de excepciones al guardar
                gestorNotas.guardarEnCSV("csvs/notas.csv");
                System.exit(0);
            } catch (PersistenciaException ex) {
                // SIA2.8 - Manejo específico de errores de persistencia
                int opcion = JOptionPane.showConfirmDialog(this, 
                    "Error al guardar los datos:\n" + ex.getMensajeUsuario() + 
                    "\n\n¿Desea salir sin guardar?", 
                    "Error al Guardar", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            } catch (Exception ex) {
                // SIA2.8 - Manejo de errores generales
                JOptionPane.showMessageDialog(this, 
                    "Error inesperado: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void cargarDatosIniciales() {
        try {
            // Crear datos iniciales
            Tema matematicas = new Tema();
            matematicas.setNombre("Matemáticas");
            matematicas.setDescripcion("Operaciones básicas");
            gestorBancos.crearBanco(matematicas);
            
            Pregunta p1 = new Pregunta("MAT-001", "¿Cuánto es 2 + 2?",
                java.util.Arrays.asList("A) 3", "B) 4", "C) 5"), "B", 1);
            gestorBancos.agregarPregunta("Matemáticas", p1);
            
            // Cargar notas desde CSV
            gestorNotas.cargarDesdeCSV("csvs/notas.csv");
            
        } catch (PersistenciaException e) {
            // SIA2.8 - Manejo específico de errores de persistencia
            JOptionPane.showMessageDialog(this, 
                "Advertencia al cargar datos:\n" + e.getMensajeUsuario(), 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos iniciales: " + e.getMessage(), 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}