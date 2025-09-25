package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.RegistroNotas;
import proyecto.evaluaciones.excepciones.PersistenciaException;
import proyecto.evaluaciones.excepciones.ValidacionException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
}
