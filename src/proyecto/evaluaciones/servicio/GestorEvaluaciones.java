package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.BancoPreguntas;
import proyecto.evaluaciones.dominio.Evaluacion;
import proyecto.evaluaciones.dominio.Pregunta;
import proyecto.evaluaciones.dominio.Tema;
import proyecto.evaluaciones.excepciones.PersistenciaException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorEvaluaciones {
    private final Map<String, Evaluacion> evaluaciones = new LinkedHashMap<>();

    public Evaluacion crearEvaluacion(String id, String titulo, String fecha, Tema tema) {
        // Validar que el ID no exista
        if (evaluaciones.containsKey(id)) {
            throw new IllegalArgumentException("Ya existe una evaluación con ID: " + id);
        }

        // Crear la evaluación
        Evaluacion ev = new Evaluacion(id, titulo, fecha, tema, 1.0);
        evaluaciones.put(id, ev);

        // Obtener preguntas del banco correspondiente
        GestorBancos gestorBancos = new GestorBancos(); // Idealmente inyectar como dependencia
        BancoPreguntas banco = gestorBancos.getBanco(tema.getNombre());
        
        if (banco != null) {
            // Agregar todas las preguntas del banco a la evaluación
            for (Pregunta pregunta : banco.getPreguntas()) {
                ev.agregarPregunta(pregunta);
            }
        }

        return ev;
    }

    public Optional<Evaluacion> obtenerPorId(String id) {
        return Optional.ofNullable(evaluaciones.get(id));
    }

    public List<Evaluacion> listar() {
        return new ArrayList<>(evaluaciones.values());
    }

    public void guardarEnCSV() throws PersistenciaException {
        try (FileWriter writer = new FileWriter("csvs/evaluaciones.csv")) {
            writer.write("id,titulo,fecha,tema,ponderacion,preguntas\n");
            for (Evaluacion eval : evaluaciones.values()) {
                // Concatenar IDs de preguntas separados por ;
                String preguntasIds = eval.getPreguntasSeleccionadas().stream()
                    .map(Pregunta::getId)
                    .collect(Collectors.joining(";"));
                
                writer.write(String.format("%s,%s,%s,%s,%.2f,%s\n",
                    eval.getId(),
                    eval.getTitulo(),
                    eval.getFecha(),
                    eval.getTema().getNombre(),
                    eval.getPonderacion(),
                    preguntasIds));
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar evaluaciones", e);
        }
    }

    public void cargarDesdeCSV(GestorBancos gestorBancos) throws PersistenciaException {
        try (BufferedReader br = new BufferedReader(new FileReader("csvs/evaluaciones.csv"))) {
            evaluaciones.clear();
            String line = br.readLine(); // skip header
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Tema tema = new Tema();
                tema.setNombre(parts[3]);
                
                Evaluacion eval = new Evaluacion(
                    parts[0], // id
                    parts[1], // titulo
                    parts[2], // fecha
                    tema,
                    Double.parseDouble(parts[4]) // ponderacion
                );

                // Cargar preguntas desde el banco correspondiente
                BancoPreguntas banco = gestorBancos.getBanco(tema.getNombre());
                if (banco != null) {
                    for (Pregunta p : banco.getPreguntas()) {
                        eval.agregarPregunta(p);
                    }
                }
                
                evaluaciones.put(eval.getId(), eval);
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al cargar evaluaciones", e);
        }
    }

    public boolean eliminarEvaluacion(String id) {
        return evaluaciones.remove(id) != null;
    }
}
