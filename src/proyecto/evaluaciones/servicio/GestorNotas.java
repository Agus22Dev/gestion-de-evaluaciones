package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.BancoPreguntas;
import proyecto.evaluaciones.dominio.Evaluacion;
import proyecto.evaluaciones.dominio.RegistroNotas;
import proyecto.evaluaciones.dominio.Tema;
import proyecto.evaluaciones.excepciones.PersistenciaException;
import proyecto.evaluaciones.excepciones.ValidacionException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import proyecto.evaluaciones.dominio.Pregunta;

public class GestorNotas {
    private final List<RegistroNotas> registros = new ArrayList<>();

    public void registrarNota(String evaluacionId, String rutEstudiante, double nota) throws ValidacionException {
        try {
            // SIA2.8 - Validación con excepciones personalizadas
            if (evaluacionId == null || evaluacionId.trim().isEmpty()) {
                throw new ValidacionException("evaluacionId", evaluacionId, "El ID de evaluación no puede estar vacío");
            }
            if (rutEstudiante == null || rutEstudiante.trim().isEmpty()) {
                throw new ValidacionException("rutEstudiante", rutEstudiante, "El RUT del estudiante no puede estar vacío");
            }
            if (nota < 1.0 || nota > 7.0) {
                throw new ValidacionException("nota", String.valueOf(nota), "La nota debe estar entre 1.0 y 7.0");
            }
            
            // Validar formato de RUT básico
            if (!rutEstudiante.matches("\\d{7,8}-[\\dkK]")) {
                throw new ValidacionException("rutEstudiante", rutEstudiante, "Formato de RUT inválido. Use: 12345678-9");
            }
            
            RegistroNotas r = new RegistroNotas();
            r.setEvaluacionId(evaluacionId.trim());
            r.setRutEstudiante(rutEstudiante.trim().toUpperCase());
            r.setNota(nota);
            registros.add(r);
            
        } catch (ValidacionException e) {
            throw e; // Re-lanzar excepción de validación
        } catch (Exception e) {
            throw new ValidacionException("Error inesperado al registrar nota: " + e.getMessage(), e);
        }
    }

    public List<RegistroNotas> listarPorEvaluacion(String evaluacionId) {
        return registros.stream()
                .filter(r -> Objects.equals(r.getEvaluacionId(), evaluacionId))
                .collect(Collectors.toList());
    }

    public void guardarEnCSV(String filename) throws PersistenciaException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("evaluacionId,rutEstudiante,nota\n");

            for (RegistroNotas r : registros) {
                writer.write(r.getEvaluacionId() + "," +
                        r.getRutEstudiante() + "," +
                        r.getNota() + "\n");
            }
            System.out.println(">>> Notas guardadas en " + filename);
        } catch (IOException e) {
            throw new PersistenciaException(filename, "guardar", 
                "No se pudo escribir el archivo CSV", e);
        } catch (Exception e) {
            throw new PersistenciaException(filename, "guardar", 
                "Error inesperado al guardar: " + e.getMessage(), e);
        }
    }

    public void cargarDesdeCSV(String filename) throws PersistenciaException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Saltar header

            int lineaNumero = 1;
            while ((line = br.readLine()) != null) {
                lineaNumero++;
                try {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        RegistroNotas r = new RegistroNotas();
                        r.setEvaluacionId(parts[0].trim());
                        r.setRutEstudiante(parts[1].trim());
                        r.setNota(Double.parseDouble(parts[2].trim()));
                        registros.add(r);
                    } else {
                        System.err.println("Línea " + lineaNumero + " con formato incorrecto: " + line);
                    }
                } catch (NumberFormatException e) {
                    throw new PersistenciaException(filename, "cargar", 
                        "Formato de nota inválido en línea " + lineaNumero + ": " + line, e);
                }
            }
            System.out.println(">>> Notas cargadas desde " + filename);
        } catch (IOException e) {
            // No lanzar excepción si el archivo no existe (primera ejecución)
            if (e instanceof java.io.FileNotFoundException) {
                System.out.println(">>> Archivo " + filename + " no encontrado. Se creará al guardar.");
            } else {
                throw new PersistenciaException(filename, "cargar", 
                    "No se pudo leer el archivo CSV", e);
            }
        } catch (PersistenciaException e) {
            throw e; // Re-lanzar nuestras excepciones personalizadas
        } catch (Exception e) {
            throw new PersistenciaException(filename, "cargar", 
                "Error inesperado al cargar: " + e.getMessage(), e);
        }
    }

    public void generarReporteTXT(String filename, GestorBancos gestorBancos, GestorEvaluaciones gestorEvals) throws PersistenciaException {
        try (FileWriter writer = new FileWriter(filename)) {
            // Encabezado (mantener como está)
            writer.write("====================================================\n");
            writer.write("          REPORTE DEL SISTEMA DE EVALUACIONES        \n");
            writer.write("====================================================\n");
            writer.write("Generado el: " + java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n\n");

            // 1. Temas y Bancos - Estructura consistente
            writer.write("1. TEMAS Y BANCOS DE PREGUNTAS\n");
            writer.write("============================\n");
            List<Tema> temas = gestorBancos.listarTemasBancos();
            writer.write(String.format("Total de temas registrados: %d\n\n", temas.size()));
            
            for (Tema tema : temas) {
                BancoPreguntas banco = gestorBancos.getBanco(tema.getNombre());
                writer.write(String.format("TEMA: %s\n", tema.getNombre()));
                writer.write(String.format("Descripción: %s\n", tema.getDescripcion()));
                writer.write(String.format("Cantidad de preguntas: %d\n", 
                    (banco != null ? banco.getPreguntas().size() : 0)));
                
                if (banco != null && !banco.getPreguntas().isEmpty()) {
                    writer.write("Preguntas disponibles:\n");
                    for (Pregunta p : banco.getPreguntas()) {
                        writer.write(String.format("- ID: %s\n", p.getId()));
                        writer.write(String.format("  Dificultad: %d/5\n", p.getDificultad()));
                        writer.write(String.format("  Enunciado: %s\n", p.getEnunciado()));
                        writer.write("  Opciones:\n");
                        for (int i = 0; i < p.getOpciones().size(); i++) {
                            String marca = p.getRespuestaCorrecta().equals(String.valueOf((char)('A' + i))) ? " ✓" : "";
                            writer.write(String.format("    %s%s\n", p.getOpciones().get(i), marca));
                        }
                        writer.write("  Respuesta correcta: " + p.getRespuestaCorrecta() + "\n");
                        writer.write("\n");
                    }
                } else {
                    writer.write("No hay preguntas registradas para este tema.\n");
                }
                writer.write("----------------------------\n\n");
            }

            // 2. Evaluaciones - También hacerlo consistente
            writer.write("2. EVALUACIONES PROGRAMADAS\n");
            writer.write("=========================\n");
            List<Evaluacion> evaluaciones = gestorEvals.listar();
            writer.write(String.format("Total de evaluaciones: %d\n\n", evaluaciones.size()));
            
            for (Evaluacion eval : evaluaciones) {
                writer.write(String.format("EVALUACIÓN: %s\n", eval.getTitulo()));
                writer.write(String.format("ID: %s\n", eval.getId()));
                writer.write(String.format("Fecha: %s\n", eval.getFecha()));
                writer.write(String.format("Tema: %s\n", eval.getTema().getNombre()));
                writer.write(String.format("Ponderación: %.1f\n", eval.getPonderacion()));
                writer.write(String.format("Cantidad de preguntas: %d\n", eval.getPreguntasSeleccionadas().size()));
                
                if (!eval.getPreguntasSeleccionadas().isEmpty()) {
                    writer.write("Preguntas incluidas:\n");
                    for (Pregunta p : eval.getPreguntasSeleccionadas()) {
                        writer.write(String.format("- ID: %s\n", p.getId()));
                        writer.write(String.format("  Dificultad: %d/3\n", p.getDificultad()));
                        writer.write(String.format("  Enunciado: %s\n", p.getEnunciado()));
                        writer.write("  Respuesta correcta: " + p.getRespuestaCorrecta() + "\n");
                    }
                } else {
                    writer.write("No hay preguntas asignadas a esta evaluación.\n");
                }
                writer.write("----------------------------\n\n");
            }

            // 3. Registro de Calificaciones (mantener como está)
            writer.write("\n3. REGISTRO DE CALIFICACIONES\n");
            writer.write("===========================\n");
            writer.write("Total de registros: " + registros.size() + "\n\n");
            
            // Agrupar notas por evaluación
            Map<String, List<Double>> notasPorEvaluacion = new HashMap<>();
            Map<String, Set<String>> alumnosPorEvaluacion = new HashMap<>();
            
            for (RegistroNotas registro : registros) {
                notasPorEvaluacion
                    .computeIfAbsent(registro.getEvaluacionId(), k -> new ArrayList<>())
                    .add(registro.getNota());
                    
                alumnosPorEvaluacion
                    .computeIfAbsent(registro.getEvaluacionId(), k -> new HashSet<>())
                    .add(registro.getRutEstudiante());
            }
            
            // Estadísticas detalladas por evaluación
            for (Map.Entry<String, List<Double>> entry : notasPorEvaluacion.entrySet()) {
                String evalId = entry.getKey();
                List<Double> notas = entry.getValue();
                Set<String> alumnos = alumnosPorEvaluacion.get(evalId);
                
                // Calcular estadísticas
                double promedio = notas.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                double notaMax = notas.stream().mapToDouble(Double::doubleValue).max().orElse(0);
                double notaMin = notas.stream().mapToDouble(Double::doubleValue).min().orElse(0);
                
                writer.write("EVALUACIÓN: " + evalId + "\n");
                writer.write(String.format("- Total de alumnos: %d\n", alumnos.size()));
                writer.write(String.format("- Promedio: %.1f\n", promedio));
                writer.write(String.format("- Nota más alta: %.1f\n", notaMax));
                writer.write(String.format("- Nota más baja: %.1f\n", notaMin));
                writer.write("- Distribución de notas:\n");
                
                // Distribución de notas
                Map<String, Long> distribucion = notas.stream()
                    .collect(Collectors.groupingBy(n -> {
                        if (n >= 6.0) return "6.0-7.0";
                        if (n >= 5.0) return "5.0-5.9";
                        if (n >= 4.0) return "4.0-4.9";
                        return "1.0-3.9";
                    }, Collectors.counting()));
                
                distribucion.forEach((rango, cantidad) -> {
                    try {
                        writer.write(String.format("  %s: %d alumno(s)\n", rango, cantidad));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.write("----------------------------\n");
            }

            System.out.println(">>> Reporte detallado generado en " + filename);
        } catch (IOException e) {
            throw new PersistenciaException(filename, "generar reporte", 
                "No se pudo escribir el archivo de reporte", e);
        }
    }
}
