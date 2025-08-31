# 📚 Sistema de Evaluaciones Digitales

Este proyecto implementa un **Sistema de Evaluaciones Digitales** para un colegio.  
Su finalidad es **gestionar bancos de preguntas**, **crear evaluaciones** y **registrar notas de estudiantes** de manera estructurada, utilizando **Java 11** y el **Java Collections Framework (Map + List)**.  

El sistema funciona mediante **consola**, con un menú interactivo que permite agregar y listar preguntas desde los bancos, cumpliendo con los requisitos de la asignatura *Programación Avanzada (ICI2241)*.

---

## 🚀 Funcionalidades principales

- **Gestión de bancos de preguntas**
  - Crear bancos de preguntas por tema (ej: Matemáticas, Lenguaje).
  - Agregar preguntas a un banco.
  - Listar preguntas registradas.

- **Creación de evaluaciones**
  - Instanciar evaluaciones a partir de un banco de preguntas.

- **Registro de notas**
  - Asociar notas a estudiantes y evaluaciones.
  - Consultar resultados por estudiante o por evaluación.

---

## 🛠️ Estructura del proyecto

El proyecto está organizado en **paquetes Java** siguiendo un diseño modular:

- `dominio/` → Clases del modelo principal.
  - **Tema** → Representa un área de conocimiento.
  - **Pregunta** → Define enunciado, opciones, respuesta correcta y dificultad.
  - **BancoPreguntas** → Agrupa preguntas asociadas a un tema.
    - Métodos:  
      - `agregarPregunta(Pregunta p)`  
      - `agregarPregunta(String id, String enunciado, List<String> opciones, String respuestaCorrecta, int dificultad)` *(sobrecarga)*  
      - `getPreguntas()`
  - **Evaluacion** → Instancia de evaluación con preguntas seleccionadas.
  - **Estudiante** → Identificado por RUT y nombre.
  - **RegistroNota** → Relación entre estudiante, evaluación y nota.

- `servicio/` → Lógica de negocio.
  - **GestorBancos** → Gestiona bancos de preguntas (usa `Map<String, BancoPreguntas>`).
    - Métodos para crear, buscar, agregar y listar preguntas.  
    - Sobrecarga de métodos para trabajar tanto con objetos (`Tema`) como con `String`.

- `ui/` → Interfaz de consola.
  - **ConsolaMenu**
    - `iniciar()` → Arranca la aplicación.
    - `seedMinimo()` → Precarga datos de ejemplo (1 tema, 1 pregunta, 1 estudiante).
    - Menú con opciones para **insertar** y **listar preguntas** de un banco.

---

## ⚙️ Requisitos

- **Java 11** o superior.
- IDE recomendado: **IntelliJ IDEA** o **Eclipse**.
- No se requiere ninguna librería externa (solo JDK estándar).

---

## ▶️ Ejecución del programa

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Agus22Dev/gestion-de-evaluaciones.git
   cd gestion-de-evaluaciones
## Compilar 
javac src/proyecto/evaluaciones/ui/Main.java
## Ejecutar 
java proyecto.evaluaciones.ui.Main

---

## Intereaccion con el menu 
===== Sistema de Evaluaciones =====
1. Crear banco de preguntas
2. Agregar pregunta a banco
3. Listar preguntas de banco
9. Salir
