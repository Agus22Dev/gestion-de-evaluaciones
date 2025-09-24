package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.RegistroNotas;

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

    public void registrarNota(String evaluacionId, String rutEstudiante, double nota) {
        RegistroNotas r = new RegistroNotas();
        r.setEvaluacionId(evaluacionId);
        r.setRutEstudiante(rutEstudiante);
        r.setNota(nota);
        registros.add(r);
    }

    public List<RegistroNotas> listarPorEvaluacion(String evaluacionId) {
        return registros.stream()
                .filter(r -> Objects.equals(r.getEvaluacionId(), evaluacionId))
                .collect(Collectors.toList());
    }

    public void guardarEnCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("evaluacionId,rutEstudiante,nota\n");

            for (RegistroNotas r : registros) {
                writer.write(r.getEvaluacionId() + "," +
                        r.getRutEstudiante() + "," +
                        r.getNota() + "\n");
            }
            System.out.println(">>> Notas guardadas en " + filename);
        } catch (IOException e) {
            System.err.println("Error al guardar notas: " + e.getMessage());
        }
    }

    public void cargarDesdeCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Header überspringen

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    RegistroNotas r = new RegistroNotas();
                    r.setEvaluacionId(parts[0]);
                    r.setRutEstudiante(parts[1]);
                    r.setNota(Double.parseDouble(parts[2]));
                    registros.add(r);
                }
            }
            System.out.println(">>> Notas cargadas desde " + filename);
        } catch (IOException e) {
            System.err.println("Error al cargar notas: " + e.getMessage());
        }
    }
}
