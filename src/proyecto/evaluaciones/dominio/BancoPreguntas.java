package proyecto.evaluaciones.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BancoPreguntas {
    private String nombre;
    private Tema tema;
    private final List<Pregunta> preguntas = new ArrayList<>();

    public BancoPreguntas(String nombre) {
        this(nombre, null);
    }

    public BancoPreguntas(String nombre, Tema tema) {
        this.nombre = Objects.requireNonNull(nombre, "nombre no puede ser null");
        this.tema = tema;
    }

    public void agregarPregunta(Pregunta p) {
        preguntas.add(Objects.requireNonNull(p));
    }

    public Pregunta agregarPregunta(String id, String enunciado, List<String> opciones, String respuestaCorrecta, int dificultad) {
        Pregunta q = new Pregunta(id, enunciado, opciones, respuestaCorrecta, dificultad);
        preguntas.add(q);
        return q;
    }

    public boolean eliminarPreguntaPorId(String id) {
        return preguntas.removeIf(q -> Objects.equals(q.getId(), id));
    }


    public List<Pregunta> getPreguntas() {
        return Collections.unmodifiableList(preguntas);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = Objects.requireNonNull(nombre); }

    public Tema getTema() { return tema; }
    public void setTema(Tema tema) { this.tema = tema; }





}
