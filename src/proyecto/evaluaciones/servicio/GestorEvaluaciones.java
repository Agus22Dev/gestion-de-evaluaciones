package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.Evaluacion;
import proyecto.evaluaciones.dominio.Tema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GestorEvaluaciones {
    private final Map<String, Evaluacion> evaluaciones = new LinkedHashMap<>();

    public Evaluacion crearEvaluacion(String id, String titulo, String fecha, Tema tema) {
        Evaluacion ev = new Evaluacion(id, titulo, fecha, tema, 1.0);
        evaluaciones.put(id, ev);
        return ev;
    }

    public Optional<Evaluacion> obtenerPorId(String id) {
        return Optional.ofNullable(evaluaciones.get(id));
    }

    public List<Evaluacion> listar() {
        return new ArrayList<>(evaluaciones.values());
    }
}
