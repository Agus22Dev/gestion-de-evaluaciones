package proyecto.evaluaciones.dominio;

import java.util.List;

public class Evaluacion {
    private String id;
    private String titulo;
    private String fecha;
    private Tema tema;
    private List<Pregunta> preguntasSeleccionadas;
    private double ponderacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public List<Pregunta> getPreguntasSeleccionadas() {
        return preguntasSeleccionadas;
    }

    public void setPreguntasSeleccionadas(List<Pregunta> preguntasSeleccionadas) {
        this.preguntasSeleccionadas = preguntasSeleccionadas;
    }

    public double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(double ponderacion) {
        this.ponderacion = ponderacion;
    }
}
