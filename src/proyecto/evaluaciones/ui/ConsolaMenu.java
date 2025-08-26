package proyecto.evaluaciones.ui;

import proyecto.evaluaciones.dominio.*;
import proyecto.evaluaciones.servicio.*;

import java.util.*;

public class ConsolaMenu {
    private final Scanner sc = new Scanner(System.in);


    private final GestorBancos gestorBancos = new GestorBancos();
    private final GestorEvaluaciones gestorEvals = new GestorEvaluaciones();
    private final GestorNotas gestorNotas = new GestorNotas();

    private void seedMinimo() {
        // ----- Crear un Tema -----
        Tema mat = new Tema();
        mat.setNombre("Matemáticas");
        mat.setDescripcion("Operaciones básicas");
        gestorBancos.crearBanco(mat);

        // ----- Crear una Pregunta -----
        Pregunta p1 = new Pregunta();
        p1.setId("MAT-001");
        p1.setEnunciado("¿Cuánto es 2 + 2?");
        p1.setOpciones(Arrays.asList("A) 3", "B) 4", "C) 5"));
        p1.setRespuestaCorrecta("B");
        p1.setDificultad(1);
        gestorBancos.agregarPregunta("Matemáticas", p1);

        // ----- Crear un Estudiante -----
        Estudiante e1 = new Estudiante();
        e1.setRut("11111111-1");
        e1.setNombre("Ana Pérez");

        System.out.println(">>> Datos iniciales cargados (1 tema, 1 pregunta y 1 estudiante).");
    }

    public void iniciar() {
        seedMinimo();

        int op;
        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Crear Tema + Banco");
            System.out.println("2. Agregar Pregunta a Banco (SIA1.8 Inserción)");
            System.out.println("3. Listar Preguntas de Banco (SIA1.8 Listado)");
            System.out.println("4. Crear Evaluación desde Banco");
            System.out.println("5. Registrar Nota");
            System.out.println("6. Listar Notas de Evaluación");
            System.out.println("9. Salir");
            System.out.print("Opción: ");
            op = leerEntero();

            switch (op) {
                case 1:
                    crearTemaYBanco();
                    break;
                case 2:
                    agregarPreguntaABanco();
                    break;
                case 3:
                    listarPreguntasDeBanco();
                    break;
                case 4:
                    crearEvaluacionDesdeBanco();
                    break;
                case 5:
                    registrarNota();
                    break;
                case 6:
                    listarNotas();
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (op != 9);

    }


    private void seedMinimo() {
        Tema mat = new Tema();
        mat.setNombre("Matemáticas");
        mat.setDescripcion("Aritmética básica");

        gestorBancos.crearBanco(mat);

        Pregunta p = new Pregunta();
        p.setId("MAT-001");
        p.setEnunciado("2 + 2 = ?");
        p.setOpciones(Arrays.asList("A) 3", "B) 4", "C) 5"));
        p.setRespuestaCorrecta("B");
        p.setDificultad(1);

        gestorBancos.agregarPregunta("Matemáticas", p);
    }


    private void crearTemaYBanco() {
        System.out.print("Nombre del tema: ");
        String nombre = sc.next();
        sc.nextLine(); // limpiar buffer
        System.out.print("Descripción: ");
        String desc = sc.nextLine();

        Tema t = new Tema();
        t.setNombre(nombre);
        t.setDescripcion(desc);

        gestorBancos.crearBanco(t);
        System.out.println("Banco creado para tema: " + nombre);
    }

    private void agregarPreguntaABanco() {
        System.out.print("Tema existente: ");
        String tema = sc.next();
        sc.nextLine();

        Pregunta p = new Pregunta();
        System.out.print("ID pregunta: ");
        p.setId(sc.next());
        sc.nextLine();

        System.out.print("Enunciado: ");
        p.setEnunciado(sc.nextLine());

        List<String> ops = new ArrayList<>();
        for (char c = 'A'; c <= 'C'; c++) {
            System.out.print("Opción " + c + ": ");
            ops.add(c + ") " + sc.nextLine());
        }
        p.setOpciones(ops);

        System.out.print("Respuesta correcta (A/B/C): ");
        p.setRespuestaCorrecta(sc.next());

        System.out.print("Dificultad (1-3): ");
        p.setDificultad(leerEntero());

        try {
            gestorBancos.agregarPregunta(tema, p);
            System.out.println("Pregunta agregada.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarPreguntasDeBanco() {
        System.out.print("Tema a listar: ");
        String tema = sc.next();

        BancoPreguntas banco = gestorBancos.getBanco(tema);
        if (banco == null) {
            System.out.println("No existe ese banco.");
            return;
        }

        int i = 1;
        for (Pregunta q : banco.getPreguntas()) {
            System.out.println(i++ + ". " + q.getId() + " - " + q.getEnunciado());
        }
        if (i == 1) System.out.println("(sin preguntas)");
    }

    private void crearEvaluacionDesdeBanco() {
        System.out.print("ID evaluación: ");
        String id = sc.next();
        System.out.print("Título: ");
        String titulo = sc.next();
        System.out.print("Fecha (aaaa-mm-dd): ");
        String fecha = sc.next();
        System.out.print("Tema origen: ");
        String tema = sc.next();

        BancoPreguntas banco = gestorBancos.getBanco(tema);
        if (banco == null) {
            System.out.println("No existe banco para ese tema.");
            return;
        }

        Tema t = new Tema();
        t.setNombre(tema);
        var ev = gestorEvals.crearEvaluacion(id, titulo, fecha, t);

        ev.agregarPregunta(banco.getPreguntas(), 1);
        System.out.println("Evaluación creada con " + banco.getPreguntas().size() + " preguntas.");
    }

    private void registrarNota() {
        System.out.print("ID evaluación: ");
        String ev = sc.next();
        System.out.print("RUT estudiante: ");
        String rut = sc.next();
        System.out.print("Nota (1.0-7.0): ");
        double n = sc.nextDouble();

        gestorNotas.registrarNota(ev, rut, n);
        System.out.println("Nota registrada.");
    }

    private void listarNotas() {
        System.out.print("ID evaluación: ");
        String ev = sc.next();
        var notas = gestorNotas.listarPorEvaluacion(ev);

        if (notas.isEmpty()) {
            System.out.println("(sin notas)");
            return;
        }

        notas.forEach(r -> System.out.println(r.getRutEstudiante() + " -> " + r.getNota()));
    }


    private int leerEntero() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Número válido: ");
        }
        return sc.nextInt();
    }
}
