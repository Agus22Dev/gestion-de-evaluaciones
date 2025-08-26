package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.BancoPreguntas;
import proyecto.evaluaciones.dominio.Pregunta;

import java.util.*;

public class GestorBancos {
    private final Map<String, BancoPreguntas> bancos = new LinkedHashMap<>();

    public boolean crearBanco(String nombreBanco) {
        String key = nombreBanco.trim();
        if (bancos.containsKey(key)) return false;
        bancos.put(key, new BancoPreguntas(key));
        return true;
    }

    public Optional<BancoPreguntas> obtenerBanco(String nombreBanco) {
        return Optional.ofNullable(bancos.get(nombreBanco));
    }

    public List<String> listarNombresBancos() {
        return new ArrayList<>(bancos.keySet());
    }

    public void agregarPregunta(String nombreBanco, Pregunta p) {
        BancoPreguntas banco = bancos.computeIfAbsent(nombreBanco, BancoPreguntas::new);
        banco.agregarPregunta(p);
    }

    public List<Pregunta> listarPreguntas(String nombreBanco) {
        BancoPreguntas b = bancos.get(nombreBanco);
        return (b == null) ? Collections.emptyList() : b.getPreguntas();
    }

    public Map<String, BancoPreguntas> verBancos() {
        return Collections.unmodifiableMap(bancos);
    }
}

