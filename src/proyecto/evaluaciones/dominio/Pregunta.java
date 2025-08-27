package proyecto.evaluaciones.dominio;

import java.util.List;
import java.util.Objects;

public class Pregunta {
    private String id;
    private String enunciado;
    private List<String> opciones;
    private String respuestaCorrecta;
    private int dificultad;



    public Pregunta(String id, String enunciado, List<String> opciones, String respuestaCorrecta, int dificultad) {
        this.id = Objects.requireNonNull(id);
        this.enunciado = Objects.requireNonNull(enunciado);
        this.opciones = Objects.requireNonNull(opciones);
        this.respuestaCorrecta = Objects.requireNonNull(respuestaCorrecta);
        this.dificultad = dificultad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public List<String> getOpciones() { return opciones; }
    public void setOpciones(List<String> opciones) { this.opciones = opciones; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(String respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }
    public int getDificultad() { return dificultad; }
    public void setDificultad(int dificultad) { this.dificultad = dificultad; }


}
