package proyecto.evaluaciones.ui;

import proyecto.evaluaciones.servicio.GestorNotas;
import proyecto.evaluaciones.dominio.RegistroNotas;
import proyecto.evaluaciones.excepciones.ValidacionException;
import proyecto.evaluaciones.excepciones.PersistenciaException; // Agregar este import

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionNotas extends JFrame {
    private final GestorNotas gestorNotas;
    
    private JTable tablaNotas;
    private DefaultTableModel modeloTabla;
    private JTextField txtEvaluacionId, txtRutEstudiante, txtBuscarEvaluacion;
    private JSpinner spinnerNota;
    private JButton btnRegistrar, btnBuscar, btnActualizar, btnExportar;
    
    public VentanaGestionNotas(GestorNotas gestorNotas) {
        this.gestorNotas = gestorNotas;
        initComponents();
        initEventHandlers();
        actualizarTabla();
    }
    
    private void initComponents() {
        setTitle("Gestión de Notas");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel superior - Registro de notas
        JPanel panelRegistro = createPanelRegistro();
        add(panelRegistro, BorderLayout.NORTH);
        
        // Panel central - Tabla de notas
        String[] columnas = {"ID Evaluación", "RUT Estudiante", "Nota"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaNotas = new JTable(modeloTabla);
        
        JScrollPane scrollTabla = new JScrollPane(tablaNotas);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Registro de Notas"));
        add(scrollTabla, BorderLayout.CENTER);
        
        // Panel inferior - Búsqueda
        JPanel panelBusqueda = createPanelBusqueda();
        add(panelBusqueda, BorderLayout.SOUTH);
    }
    
    private JPanel createPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Nueva Nota"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID Evaluación
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Evaluación:"), gbc);
        gbc.gridx = 1;
        txtEvaluacionId = new JTextField(15);
        panel.add(txtEvaluacionId, gbc);
        
        // RUT Estudiante
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("RUT Estudiante:"), gbc);
        gbc.gridx = 3;
        txtRutEstudiante = new JTextField(15);
        panel.add(txtRutEstudiante, gbc);
        
        // Nota
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nota (1.0-7.0):"), gbc);
        gbc.gridx = 1;
        spinnerNota = new JSpinner(new SpinnerNumberModel(4.0, 1.0, 7.0, 0.1));
        panel.add(spinnerNota, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnRegistrar = new JButton("Registrar Nota");
        btnActualizar = new JButton("Actualizar Lista");
        btnExportar = new JButton("Exportar CSV");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportar);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 4;
        panel.add(panelBotones, gbc);
        
        return panel;
    }
    
    private JPanel createPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Buscar Notas por Evaluación"));
        
        panel.add(new JLabel("ID Evaluación:"));
        txtBuscarEvaluacion = new JTextField(15);
        panel.add(txtBuscarEvaluacion);
        
        btnBuscar = new JButton("Buscar");
        panel.add(btnBuscar);
        
        return panel;
    }
    
    private void initEventHandlers() {
        // Botón registrar
        btnRegistrar.addActionListener(e -> registrarNota());
        
        // Botón buscar
        btnBuscar.addActionListener(e -> buscarNotasPorEvaluacion());
        
        // Botón actualizar
        btnActualizar.addActionListener(e -> {
            try {
                actualizarTabla();
                limpiarFormulario();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al actualizar: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Botón exportar
        btnExportar.addActionListener(e -> exportarCSV());
    }
    
    private void registrarNota() {
        try {
            String evaluacionId = txtEvaluacionId.getText().trim();
            String rutEstudiante = txtRutEstudiante.getText().trim();
            double nota = (Double) spinnerNota.getValue();
            
            // SIA2.8 - Try-catch con excepciones personalizadas
            gestorNotas.registrarNota(evaluacionId, rutEstudiante, nota);
            
            actualizarTabla();
            limpiarFormulario();
            
            JOptionPane.showMessageDialog(this, "Nota registrada exitosamente.");
            
        } catch (ValidacionException e) {
            // SIA2.8 - Manejo específico de errores de validación
            JOptionPane.showMessageDialog(this, 
                "Error de validación:\n" + e.getMensajeUsuario(), 
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // SIA2.8 - Manejo de errores generales
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarNotasPorEvaluacion() {
        try {
            String evaluacionId = txtBuscarEvaluacion.getText().trim();
            if (evaluacionId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el ID de evaluación.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<RegistroNotas> notas = gestorNotas.listarPorEvaluacion(evaluacionId);
            
            if (notas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron notas para esta evaluación.", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Mostrar resultados en la tabla
            modeloTabla.setRowCount(0);
            for (RegistroNotas registro : notas) {
                Object[] fila = {
                    registro.getEvaluacionId(),
                    registro.getRutEstudiante(),
                    registro.getNota()
                };
                modeloTabla.addRow(fila);
            }
            
            // Calcular estadísticas básicas
            double suma = notas.stream().mapToDouble(RegistroNotas::getNota).sum();
            double promedio = suma / notas.size();
            double notaMax = notas.stream().mapToDouble(RegistroNotas::getNota).max().orElse(0);
            double notaMin = notas.stream().mapToDouble(RegistroNotas::getNota).min().orElse(0);
            
            String estadisticas = String.format(
                "Estadísticas para evaluación %s:\n" +
                "Total de notas: %d\n" +
                "Promedio: %.2f\n" +
                "Nota máxima: %.1f\n" +
                "Nota mínima: %.1f",
                evaluacionId, notas.size(), promedio, notaMax, notaMin
            );
            
            JOptionPane.showMessageDialog(this, estadisticas, 
                "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar notas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportarCSV() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar archivo CSV");
            fileChooser.setSelectedFile(new java.io.File("notas_export.csv"));
            
            int userSelection = fileChooser.showSaveDialog(this);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                String rutaArchivo = fileToSave.getAbsolutePath();
                
                if (!rutaArchivo.toLowerCase().endsWith(".csv")) {
                    rutaArchivo += ".csv";
                }
                
                // SIA2.8 - Try-catch con excepciones personalizadas
                gestorNotas.guardarEnCSV(rutaArchivo);
                
                JOptionPane.showMessageDialog(this, 
                    "Archivo exportado exitosamente a: " + rutaArchivo, 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (PersistenciaException e) {
            // SIA2.8 - Manejo específico de errores de persistencia
            JOptionPane.showMessageDialog(this, 
                "Error de persistencia:\n" + e.getMensajeUsuario(), 
                "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // SIA2.8 - Manejo de errores generales
            JOptionPane.showMessageDialog(this, 
                "Error al exportar CSV: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarTabla() {
        try {
            // Para mostrar todas las notas, podríamos necesitar un método en GestorNotas
            // Por ahora, limpiamos la tabla cuando se actualiza
            modeloTabla.setRowCount(0);
            
            // Nota: Aquí se podría implementar un método listarTodas() en GestorNotas
            // para mostrar todas las notas registradas
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar tabla: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarFormularioRegistro() {
        try {
            if (txtEvaluacionId.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("El ID de evaluación no puede estar vacío");
            }
            if (txtRutEstudiante.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("El RUT del estudiante no puede estar vacío");
            }
            
            // Validar formato básico de RUT (opcional)
            String rut = txtRutEstudiante.getText().trim();
            if (!rut.matches("\\d{7,8}-[\\dkK]")) {
                throw new IllegalArgumentException("Formato de RUT inválido. Use: 12345678-9");
            }
            
            double nota = (Double) spinnerNota.getValue();
            if (nota < 1.0 || nota > 7.0) {
                throw new IllegalArgumentException("La nota debe estar entre 1.0 y 7.0");
            }
            
            return true;
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void limpiarFormulario() {
        txtEvaluacionId.setText("");
        txtRutEstudiante.setText("");
        spinnerNota.setValue(4.0);
        txtBuscarEvaluacion.setText("");
    }
}