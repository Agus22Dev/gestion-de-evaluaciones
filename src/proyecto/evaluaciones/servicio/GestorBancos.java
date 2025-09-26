package proyecto.evaluaciones.servicio;

import proyecto.evaluaciones.dominio.BancoPreguntas;
import proyecto.evaluaciones.dominio.Pregunta;
import proyecto.evaluaciones.dominio.Tema;
import proyecto.evaluaciones.excepciones.PersistenciaException; // Agregar este import

import java.io.*;
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

    public BancoPreguntas getBanco(String nombreTema) {
        for (Map.Entry<Tema, BancoPreguntas> entry : bancos.entrySet()) {
            Tema t = entry.getKey();
            if (t != null && Objects.equals(t.getNombre(), nombreTema)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void agregarPregunta(String nombreTema, Pregunta p) {
        BancoPreguntas banco = getBanco(nombreTema);
        if (banco == null) {
            Tema t = new Tema();
            t.setNombre(nombreTema);
            banco = new BancoPreguntas(nombreTema, t);
            bancos.put(t, banco);
        }
        banco.agregarPregunta(p);
    }

    public void guardarEnCSV() throws PersistenciaException {
        try {
            // Guardar temas
            try (FileWriter writerTemas = new FileWriter("csvs/temas.csv")) {
                writerTemas.write("nombre,descripcion\n");
                for (Tema tema : bancos.keySet()) {
                    writerTemas.write(String.format("%s,%s\n",
                        tema.getNombre(),
                        tema.getDescripcion()));
                }
            }

            // Guardar preguntas
            try (FileWriter writerPreguntas = new FileWriter("csvs/preguntas.csv")) {
                writerPreguntas.write("tema,id,enunciado,opcionA,opcionB,opcionC,opcionD,respuestaCorrecta,dificultad\n");
                for (Map.Entry<Tema, BancoPreguntas> entry : bancos.entrySet()) {
                    String tema = entry.getKey().getNombre();
                    for (Pregunta p : entry.getValue().getPreguntas()) {
                        writerPreguntas.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d\n",
                            tema,
                            p.getId(),
                            p.getEnunciado(),
                            p.getOpciones().get(0),
                            p.getOpciones().get(1),
                            p.getOpciones().get(2),
                            p.getOpciones().get(3),
                            p.getRespuestaCorrecta(),
                            p.getDificultad()));
                    }
                }
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar bancos/preguntas", e);
        }
    }

    public void cargarDesdeCSV() throws PersistenciaException {
        try {
            bancos.clear();
            
            // Cargar temas
            try (BufferedReader brTemas = new BufferedReader(new FileReader("csvs/temas.csv"))) {
                String line = brTemas.readLine(); // skip header
                while ((line = brTemas.readLine()) != null) {
                    String[] parts = line.split(",");
                    Tema tema = new Tema();
                    tema.setNombre(parts[0]);
                    tema.setDescripcion(parts[1]);
                    crearBanco(tema);
                }
            }

            // Cargar preguntas
            try (BufferedReader brPreguntas = new BufferedReader(new FileReader("csvs/preguntas.csv"))) {
                String line = brPreguntas.readLine(); // skip header
                while ((line = brPreguntas.readLine()) != null) {
                    String[] parts = line.split(",");
                    String tema = parts[0];
                    Pregunta p = new Pregunta(
                        parts[1], // id
                        parts[2], // enunciado
                        Arrays.asList(parts[3], parts[4], parts[5], parts[6]), // 4 opciones
                        parts[7], // respuesta correcta
                        Integer.parseInt(parts[8]) // dificultad
                    );
                    agregarPregunta(tema, p);
                }
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error al cargar bancos/preguntas", e);
        }
    }
}
