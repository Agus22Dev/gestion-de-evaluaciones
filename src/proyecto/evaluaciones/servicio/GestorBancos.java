package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.BancoPreguntas;
import proyecto.evaluaciones.dominio.Pregunta;
import proyecto.evaluaciones.dominio.Tema;

import java.util.*;

public class GestorBancos {
    private final Map<Tema, BancoPreguntas> bancos = new LinkedHashMap<>();

    public boolean crearBanco(Tema tema) {
        if (bancos.containsKey(tema)) return false;
        bancos.put(tema, new BancoPreguntas(tema.getNombre(), tema));
        return true;
    }

    public Optional<BancoPreguntas> obtenerBanco(Tema tema) {
        return Optional.ofNullable(bancos.get(tema));
    }

    public List<Tema> listarTemasBancos() {
        return new ArrayList<>(bancos.keySet());
    }

    public void agregarPregunta(Tema tema, Pregunta p) {
        BancoPreguntas banco = bancos.computeIfAbsent(tema, t -> new BancoPreguntas(t.getNombre(), t));
        banco.agregarPregunta(p);
    }

    public List<Pregunta> listarPreguntas(Tema tema) {
        BancoPreguntas b = bancos.get(tema);
        return (b == null) ? Collections.emptyList() : b.getPreguntas();
    }

    public Map<Tema, BancoPreguntas> verBancos() {
        return Collections.unmodifiableMap(bancos);
    }

    public boolean eliminarPregunta(Tema tema, String idPregunta) {
        BancoPreguntas b = bancos.get(tema);
        return (b != null) && b.eliminarPreguntaPorId(idPregunta);
    }
}
