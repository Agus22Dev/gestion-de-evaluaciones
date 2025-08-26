package proyecto.evaluaciones.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Evaluacion {
    private String id;
    private String titulo;
    private String fecha;
    private Tema tema;
    private final List<Pregunta> preguntasSeleccionadas = new ArrayList<>();
    private double ponderacion;

    public Evaluacion() {}

    public Evaluacion(String id, String titulo, String fecha, Tema tema, double ponderacion) {
        this.id = Objects.requireNonNull(id);
        this.titulo = Objects.requireNonNull(titulo);
        this.fecha = Objects.requireNonNull(fecha);
        this.tema = tema;
        this.ponderacion = ponderacion;
    }

    public void agregarPregunta(Pregunta p) {
        preguntasSeleccionadas.add(Objects.requireNonNull(p));
    }

    public Pregunta agregarPregunta(String id, String enunciado, List<String> opciones, String respuestaCorrecta, int dificultad) {
        Pregunta q = new Pregunta(id, enunciado, opciones, respuestaCorrecta, dificultad);
        preguntasSeleccionadas.add(q);
        return q;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public Tema getTema() { return tema; }
    public void setTema(Tema tema) { this.tema = tema; }

    public List<Pregunta> getPreguntasSeleccionadas() {
        return Collections.unmodifiableList(preguntasSeleccionadas);
    }

    public double getPonderacion() { return ponderacion; }
    public void setPonderacion(double ponderacion) { this.ponderacion = ponderacion; }


}
