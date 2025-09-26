package proyecto.evaluaciones.ui;

import proyecto.evaluaciones.servicio.*;
import proyecto.evaluaciones.dominio.*;
import proyecto.evaluaciones.excepciones.PersistenciaException; // Add this import

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionEvaluaciones extends JFrame {
    private final GestorEvaluaciones gestorEvaluaciones;
    private final GestorBancos gestorBancos;
    
    private JTable tablaEvaluaciones;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtTitulo, txtFecha;
    private JComboBox<String> cbTemas;
    private JSpinner spinnerPonderacion;
    private JButton btnCrear, btnVer, btnActualizar, btnEliminar;
    
    public VentanaGestionEvaluaciones(GestorEvaluaciones gestorEvaluaciones, GestorBancos gestorBancos) {
        this.gestorEvaluaciones = gestorEvaluaciones;
        this.gestorBancos = gestorBancos;
        initComponents();
        initEventHandlers();
        cargarTemas();
        actualizarTabla();
    }
    
    private void initComponents() {
        setTitle("Gestión de Evaluaciones");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel superior - Tabla de evaluaciones
        String[] columnas = {"ID", "Título", "Fecha", "Tema", "Nº Preguntas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEvaluaciones = new JTable(modeloTabla);
        
        JScrollPane scrollTabla = new JScrollPane(tablaEvaluaciones);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Evaluaciones Existentes"));
        add(scrollTabla, BorderLayout.CENTER);
        
        // Panel inferior - Formulario
        JPanel panelFormulario = createFormularioPanel();
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private JPanel createFormularioPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Nueva Evaluación"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        panel.add(txtId, gbc);
        
        // Título
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 3;
        txtTitulo = new JTextField(20);
        panel.add(txtTitulo, gbc);
        
        // Fecha
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        txtFecha = new JTextField(15);
        txtFecha.setToolTipText("Formato: YYYY-MM-DD");
        panel.add(txtFecha, gbc);
        
        // Tema
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 3;
        cbTemas = new JComboBox<>();
        cbTemas.setPreferredSize(new Dimension(150, 25));
        panel.add(cbTemas, gbc);
        
        // Ponderación
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Ponderación:"), gbc);
        gbc.gridx = 1;
        spinnerPonderacion = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        panel.add(spinnerPonderacion, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear Evaluación");
        btnVer = new JButton("Ver Detalles");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar Lista");
        
        btnVer.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        panelBotones.add(btnCrear);
        panelBotones.add(btnVer);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        panel.add(panelBotones, gbc);
        
        return panel;
    }
    
    private void initEventHandlers() {
        // Selección de tabla
        tablaEvaluaciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean haySeleccion = tablaEvaluaciones.getSelectedRow() >= 0;
                btnVer.setEnabled(haySeleccion);
                btnEliminar.setEnabled(haySeleccion);
            }
        });
        
        // Botón crear
        btnCrear.addActionListener(e -> crearEvaluacion());
        
        // Botón ver
        btnVer.addActionListener(e -> verDetallesEvaluacion());
        
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
        
        // Botón eliminar
        btnEliminar.addActionListener(e -> eliminarEvaluacion());
    }
    
    private void cargarTemas() {
        try {
            cbTemas.removeAllItems();
            List<Tema> temas = gestorBancos.listarTemasBancos();
            for (Tema tema : temas) {
                cbTemas.addItem(tema.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar temas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarTabla() {
        try {
            modeloTabla.setRowCount(0);
            List<Evaluacion> evaluaciones = gestorEvaluaciones.listar();
            
            for (Evaluacion eval : evaluaciones) {
                String tema = eval.getTema() != null ? eval.getTema().getNombre() : "N/A";
                int numPreguntas = eval.getPreguntasSeleccionadas().size();
                
                Object[] fila = {
                    eval.getId(),
                    eval.getTitulo(),
                    eval.getFecha(),
                    tema,
                    numPreguntas
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar tabla: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearEvaluacion() {
        try {
            if (!validarFormulario()) return;
            
            String id = txtId.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String fecha = txtFecha.getText().trim();
            String temaSeleccionado = (String) cbTemas.getSelectedItem();
            double ponderacion = (Double) spinnerPonderacion.getValue();
            
            // Crear tema
            Tema tema = new Tema();
            tema.setNombre(temaSeleccionado);
            
            // Crear evaluación
            Evaluacion evaluacion = gestorEvaluaciones.crearEvaluacion(id, titulo, fecha, tema);
            evaluacion.setPonderacion(ponderacion);
            
            // Agregar preguntas del banco
            BancoPreguntas banco = gestorBancos.getBanco(temaSeleccionado);
            if (banco != null) {
                List<Pregunta> preguntas = banco.getPreguntas();
                for (Pregunta pregunta : preguntas) {
                    evaluacion.agregarPregunta(pregunta);
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Evaluación creada con " + preguntas.size() + " preguntas.");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Evaluación creada sin preguntas (banco vacío).");
            }
            
            actualizarTabla();
            limpiarFormulario();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Error de validación: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void verDetallesEvaluacion() {
        try {
            int filaSeleccionada = tablaEvaluaciones.getSelectedRow();
            if (filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una evaluación.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            gestorEvaluaciones.obtenerPorId(id).ifPresentOrElse(
                evaluacion -> {
                    StringBuilder detalles = new StringBuilder();
                    detalles.append("ID: ").append(evaluacion.getId()).append("\n");
                    detalles.append("Título: ").append(evaluacion.getTitulo()).append("\n");
                    detalles.append("Fecha: ").append(evaluacion.getFecha()).append("\n");
                    detalles.append("Tema: ").append(evaluacion.getTema().getNombre()).append("\n");
                    detalles.append("Ponderación: ").append(evaluacion.getPonderacion()).append("\n\n");
                    detalles.append("Preguntas (").append(evaluacion.getPreguntasSeleccionadas().size()).append("):\n");
                    
                    int contador = 1;
                    for (Pregunta p : evaluacion.getPreguntasSeleccionadas()) {
                        detalles.append(contador++).append(". ").append(p.getEnunciado()).append("\n");
                    }
                    
                    JTextArea textArea = new JTextArea(detalles.toString());
                    textArea.setEditable(false);
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));
                    
                    JOptionPane.showMessageDialog(this, scrollPane, 
                        "Detalles de Evaluación", JOptionPane.INFORMATION_MESSAGE);
                },
                () -> JOptionPane.showMessageDialog(this, "Evaluación no encontrada.", 
                    "Error", JOptionPane.ERROR_MESSAGE)
            );
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al mostrar detalles: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarFormulario() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("El ID no puede estar vacío");
            }
            if (txtTitulo.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("El título no puede estar vacío");
            }
            if (txtFecha.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("La fecha no puede estar vacía");
            }
            if (cbTemas.getSelectedItem() == null) {
                throw new IllegalArgumentException("Seleccione un tema");
            }
            
            // Validar formato de fecha básico (YYYY-MM-DD)
            String fecha = txtFecha.getText().trim();
            if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD");
            }
            
            return true;
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtTitulo.setText("");
        txtFecha.setText("");
        spinnerPonderacion.setValue(1.0);
        if (cbTemas.getItemCount() > 0) {
            cbTemas.setSelectedIndex(0);
        }
    }
    
    private void eliminarEvaluacion() {
        try {
            int filaSeleccionada = tablaEvaluaciones.getSelectedRow();
            if (filaSeleccionada < 0) {
                return;
            }

            String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            int confirmacion = JOptionPane.showConfirmDialog(this,
                String.format("¿Está seguro que desea eliminar la evaluación '%s' (%s)?", titulo, id),
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (gestorEvaluaciones.eliminarEvaluacion(id)) {
                    actualizarTabla();
                    limpiarFormulario();
                    JOptionPane.showMessageDialog(this,
                        "Evaluación eliminada correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                    // Persistir cambios inmediatamente
                    try {
                        gestorEvaluaciones.guardarEnCSV();
                    } catch (PersistenciaException ex) {
                        JOptionPane.showMessageDialog(this,
                            "Error al guardar cambios: " + ex.getMensajeUsuario(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar evaluación: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}