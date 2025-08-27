package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.RegistroNotas;

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
}
