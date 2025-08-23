package proyecto.evaluaciones.dominio;

import java.util.List;

public class BancoPreguntas {
    private Tema tema;
    private List<Pregunta> preguntas;

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}