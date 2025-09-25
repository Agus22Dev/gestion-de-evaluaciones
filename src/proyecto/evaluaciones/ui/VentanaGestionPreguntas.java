package proyecto.evaluaciones.ui;

import proyecto.evaluaciones.servicio.GestorBancos;
import proyecto.evaluaciones.dominio.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class VentanaGestionPreguntas extends JFrame {
    private final GestorBancos gestorBancos;
    
    // Componentes de la interfaz
    private JComboBox<String> cbTemas;
    private JTable tablaPrevias;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtEnunciado, txtOpcionA, txtOpcionB, txtOpcionC;
    private JComboBox<String> cbRespuestaCorrecta;
    private JSpinner spinnerDificultad;
    private JButton btnAgregar, btnEditar, btnEliminar, btnActualizar;
    
    private int filaSeleccionada = -1;
    
    public VentanaGestionPreguntas(GestorBancos gestorBancos) {
        this.gestorBancos = gestorBancos;
        initComponents();
        initEventHandlers();
        cargarTemas();
        actualizarTabla();
    }
    
    private void initComponents() {
        setTitle("Gestión de Preguntas y Bancos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel superior - Selección de tema
        JPanel panelTema = new JPanel(new FlowLayout());
        panelTema.add(new JLabel("Tema:"));
        cbTemas = new JComboBox<>();
        cbTemas.setPreferredSize(new Dimension(200, 25));
        panelTema.add(cbTemas);
        
        JButton btnNuevoTema = new JButton("Nuevo Tema");
        panelTema.add(btnNuevoTema);
        
        btnNuevoTema.addActionListener(e -> {
            try {
                String nombre = JOptionPane.showInputDialog(this, "Nombre del tema:");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    String descripcion = JOptionPane.showInputDialog(this, "Descripción:");
                    Tema tema = new Tema();
                    tema.setNombre(nombre.trim());
                    tema.setDescripcion(descripcion != null ? descripcion.trim() : "");
                    
                    if (gestorBancos.crearBanco(tema)) {
                        cargarTemas();
                        cbTemas.setSelectedItem(nombre);
                        JOptionPane.showMessageDialog(this, "Tema creado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "El tema ya existe.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al crear tema: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        add(panelTema, BorderLayout.NORTH);
        
        // Panel central - Tabla de preguntas
        String[] columnas = {"ID", "Enunciado", "Dificultad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPrevias = new JTable(modeloTabla);
        tablaPrevias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollTabla = new JScrollPane(tablaPrevias);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Preguntas Existentes"));
        add(scrollTabla, BorderLayout.CENTER);
        
        // Panel inferior - Formulario
        JPanel panelFormulario = createFormularioPanel();
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private JPanel createFormularioPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Pregunta"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        panel.add(txtId, gbc);
        
        // Enunciado
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Enunciado:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtEnunciado = new JTextField(30);
        panel.add(txtEnunciado, gbc);
        gbc.gridwidth = 1;
        
        // Opciones
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Opción A:"), gbc);
        gbc.gridx = 1;
        txtOpcionA = new JTextField(20);
        panel.add(txtOpcionA, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Opción B:"), gbc);
        gbc.gridx = 1;
        txtOpcionB = new JTextField(20);
        panel.add(txtOpcionB, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Opción C:"), gbc);
        gbc.gridx = 1;
        txtOpcionC = new JTextField(20);
        panel.add(txtOpcionC, gbc);
        
        // Respuesta correcta
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Respuesta:"), gbc);
        gbc.gridx = 1;
        cbRespuestaCorrecta = new JComboBox<>(new String[]{"A", "B", "C"});
        panel.add(cbRespuestaCorrecta, gbc);
        
        // Dificultad
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Dificultad:"), gbc);
        gbc.gridx = 1;
        spinnerDificultad = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        panel.add(spinnerDificultad, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnActualizar.setEnabled(false);
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 3;
        panel.add(panelBotones, gbc);
        
        return panel;
    }
    
    private void initEventHandlers() {
        // Evento cambio de tema
        cbTemas.addActionListener(e -> {
            try {
                actualizarTabla();
                limpiarFormulario();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al cargar preguntas: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Evento selección de tabla
        tablaPrevias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                try {
                    filaSeleccionada = tablaPrevias.getSelectedRow();
                    if (filaSeleccionada >= 0) {
                        cargarPreguntaEnFormulario();
                        btnEditar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        btnActualizar.setEnabled(true);
                        btnAgregar.setEnabled(false);
                    } else {
                        limpiarFormulario();
                        btnEditar.setEnabled(false);
                        btnEliminar.setEnabled(false);
                        btnActualizar.setEnabled(false);
                        btnAgregar.setEnabled(true);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error al seleccionar pregunta: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Botón agregar
        btnAgregar.addActionListener(e -> agregarPregunta());
        
        // Botón editar (SIA2.4)
        btnEditar.addActionListener(e -> editarPregunta());
        
        // Botón eliminar (SIA2.4)
        btnEliminar.addActionListener(e -> eliminarPregunta());
        
        // Botón actualizar
        btnActualizar.addActionListener(e -> {
            limpiarFormulario();
            tablaPrevias.clearSelection();
        });
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
            String temaSeleccionado = (String) cbTemas.getSelectedItem();
            if (temaSeleccionado != null) {
                BancoPreguntas banco = gestorBancos.getBanco(temaSeleccionado);
                if (banco != null) {
                    List<Pregunta> preguntas = banco.getPreguntas();
                    for (Pregunta p : preguntas) {
                        Object[] fila = {p.getId(), p.getEnunciado(), p.getDificultad()};
                        modeloTabla.addRow(fila);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar tabla: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarPreguntaEnFormulario() {
        try {
            String temaSeleccionado = (String) cbTemas.getSelectedItem();
            if (temaSeleccionado != null && filaSeleccionada >= 0) {
                BancoPreguntas banco = gestorBancos.getBanco(temaSeleccionado);
                if (banco != null) {
                    List<Pregunta> preguntas = banco.getPreguntas();
                    if (filaSeleccionada < preguntas.size()) {
                        Pregunta p = preguntas.get(filaSeleccionada);
                        txtId.setText(p.getId());
                        txtEnunciado.setText(p.getEnunciado());
                        
                        List<String> opciones = p.getOpciones();
                        if (opciones.size() >= 3) {
                            txtOpcionA.setText(opciones.get(0).substring(3)); // Remove "A) "
                            txtOpcionB.setText(opciones.get(1).substring(3)); // Remove "B) "
                            txtOpcionC.setText(opciones.get(2).substring(3)); // Remove "C) "
                        }
                        
                        cbRespuestaCorrecta.setSelectedItem(p.getRespuestaCorrecta());
                        spinnerDificultad.setValue(p.getDificultad());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar pregunta: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarPregunta() {
        try {
            String temaSeleccionado = (String) cbTemas.getSelectedItem();
            if (temaSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un tema.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!validarFormulario()) return;
            
            String id = txtId.getText().trim();
            String enunciado = txtEnunciado.getText().trim();
            List<String> opciones = Arrays.asList(
                "A) " + txtOpcionA.getText().trim(),
                "B) " + txtOpcionB.getText().trim(),
                "C) " + txtOpcionC.getText().trim()
            );
            String respuesta = (String) cbRespuestaCorrecta.getSelectedItem();
            int dificultad = (Integer) spinnerDificultad.getValue();
            
            Pregunta nuevaPregunta = new Pregunta(id, enunciado, opciones, respuesta, dificultad);
            gestorBancos.agregarPregunta(temaSeleccionado, nuevaPregunta);
            
            actualizarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Pregunta agregada exitosamente.");
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error de validación: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // SIA2.4 - Funcionalidad de edición
    private void editarPregunta() {
        try {
            String temaSeleccionado = (String) cbTemas.getSelectedItem();
            if (temaSeleccionado == null || filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una pregunta para editar.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!validarFormulario()) return;
            
            BancoPreguntas banco = gestorBancos.getBanco(temaSeleccionado);
            if (banco != null) {
                List<Pregunta> preguntas = banco.getPreguntas();
                if (filaSeleccionada < preguntas.size()) {
                    Pregunta preguntaExistente = preguntas.get(filaSeleccionada);
                    
                    // Actualizar la pregunta existente
                    preguntaExistente.setId(txtId.getText().trim());
                    preguntaExistente.setEnunciado(txtEnunciado.getText().trim());
                    preguntaExistente.setOpciones(Arrays.asList(
                        "A) " + txtOpcionA.getText().trim(),
                        "B) " + txtOpcionB.getText().trim(),
                        "C) " + txtOpcionC.getText().trim()
                    ));
                    preguntaExistente.setRespuestaCorrecta((String) cbRespuestaCorrecta.getSelectedItem());
                    preguntaExistente.setDificultad((Integer) spinnerDificultad.getValue());
                    
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Pregunta editada exitosamente.");
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al editar pregunta: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // SIA2.4 - Funcionalidad de eliminación
    private void eliminarPregunta() {
        try {
            String temaSeleccionado = (String) cbTemas.getSelectedItem();
            if (temaSeleccionado == null || filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una pregunta para eliminar.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar esta pregunta?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirmacion == JOptionPane.YES_OPTION) {
                String idPregunta = txtId.getText().trim();
                if (gestorBancos.eliminarPregunta(gestorBancos.listarTemasBancos().stream()
                    .filter(t -> t.getNombre().equals(temaSeleccionado))
                    .findFirst().orElse(null), idPregunta)) {
                    
                    actualizarTabla();
                    limpiarFormulario();
                    JOptionPane.showMessageDialog(this, "Pregunta eliminada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la pregunta.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar pregunta: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarFormulario() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("El ID no puede estar vacío");
            }
            if (txtEnunciado.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("El enunciado no puede estar vacío");
            }
            if (txtOpcionA.getText().trim().isEmpty() || 
                txtOpcionB.getText().trim().isEmpty() || 
                txtOpcionC.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Todas las opciones deben estar completas");
            }
            return true;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtEnunciado.setText("");
        txtOpcionA.setText("");
        txtOpcionB.setText("");
        txtOpcionC.setText("");
        cbRespuestaCorrecta.setSelectedIndex(0);
        spinnerDificultad.setValue(1);
        filaSeleccionada = -1;
        
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnAgregar.setEnabled(true);
    }
}