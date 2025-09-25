# 📚 Sistema de Evaluaciones Digitales - Versión SIA2

Este proyecto implementa un **Sistema de Evaluaciones Digitales** completo para un colegio.  
Su finalidad es **gestionar bancos de preguntas**, **crear evaluaciones** y **registrar notas de estudiantes** de manera estructurada, utilizando **Java 11**, **Swing GUI** y el **Java Collections Framework**.  

El sistema ahora cuenta con **interfaces gráficas completas**, **manejo avanzado de excepciones** y **funcionalidades de edición y eliminación**, cumpliendo con los requisitos SIA2.3, SIA2.4 y SIA2.8 de *Programación Avanzada (ICI2241)*.

---

## 🆕 Nuevas Funcionalidades (SIA2)

### ✅ **SIA2.3 - Interfaces Gráficas (SWING)**
- **Ventana Principal**: Menú principal con navegación a todas las funcionalidades
- **Gestión de Preguntas**: Interfaz completa para CRUD de preguntas y bancos
- **Gestión de Evaluaciones**: Creación y visualización de evaluaciones
- **Gestión de Notas**: Registro, búsqueda y exportación de notas
- **Componentes SWING**: JTable, JComboBox, JSpinner, JFileChooser, etc.

### ✅ **SIA2.4 - Edición y Eliminación**
- **Editar Preguntas**: Selección desde tabla y modificación de datos
- **Eliminar Preguntas**: Confirmación y eliminación segura
- **Validación de Formularios**: Controles de entrada robustos
- **Actualización en Tiempo Real**: Tablas que se actualizan automáticamente

### ✅ **SIA2.8 - Manejo de Excepciones**
- **Try-Catch Específicos**: Manejo granular de diferentes tipos de errores
- **Excepciones Personalizadas**: 
  - `ValidacionException`: Para errores de validación de datos
  - `PersistenciaException`: Para errores de archivo/persistencia
- **Mensajes de Usuario**: Errores comprensibles y acciones sugeridas
- **Recuperación de Errores**: Opciones de reintento y manejo graceful

---

## 🚀 Funcionalidades Principales

### 📋 **Gestión de Bancos de Preguntas**
- Crear bancos de preguntas por tema (ej: Matemáticas, Lenguaje)
- Agregar/Editar/Eliminar preguntas en un banco
- Listar preguntas registradas con interfaz de tabla
- Validación de formatos y datos de entrada

### 📝 **Creación de Evaluaciones**
- Instanciar evaluaciones a partir de bancos de preguntas
- Visualizar detalles completos de evaluaciones
- Estadísticas básicas de preguntas por evaluación

### 📊 **Registro de Notas**
- Registrar notas con validación RUT/nota
- Buscar notas por evaluación con estadísticas
- Exportar datos a CSV con selector de archivo
- Carga automática de datos al inicio

---

## 🛠️ Arquitectura Técnica

### **Paquetes del Sistema**
```
src/proyecto/evaluaciones/
├── Main.java                    # Punto de entrada con GUI
├── dominio/                     # Clases del modelo
│   ├── Tema.java
│   ├── Pregunta.java
│   ├── BancoPreguntas.java     # Con sobrecarga de métodos
│   ├── Evaluacion.java         # Con sobrecarga de métodos  
│   ├── Estudiante.java
│   └── RegistroNotas.java
├── servicio/                    # Lógica de negocio
│   ├── GestorBancos.java       # Map<Tema, BancoPreguntas>
│   ├── GestorEvaluaciones.java # Map<String, Evaluacion>
│   └── GestorNotas.java        # Con manejo de excepciones
├── ui/                         # Interfaces gráficas (SWING)
│   ├── VentanaPrincipal.java
│   ├── VentanaGestionPreguntas.java  # CRUD completo
│   ├── VentanaGestionEvaluaciones.java
│   ├── VentanaGestionNotas.java
│   └── ConsolaMenu.java        # Versión original por consola
└── excepciones/                # SIA2.9 - Excepciones personalizadas
    ├── ValidacionException.java
    └── PersistenciaException.java
```

### **Tecnologías Utilizadas**
- **Java 11** (Oracle JDK)
- **Swing** para interfaces gráficas
- **Java Collections Framework** (Map, List)
- **CSV** para persistencia de datos
- **Try-Catch** avanzado para manejo de errores

---

## 🎯 Cumplimiento de Requisitos SIA

| Requisito | ✅ Estado | Implementación |
|-----------|---------|----------------|
| **SIA1.1** | ✅ CUMPLE | Sistema de evaluaciones para colegio |
| **SIA1.2** | ✅ CUMPLE | 6 clases del dominio bien estructuradas |
| **SIA1.3** | ✅ CUMPLE | Atributos privados con getters/setters |
| **SIA1.4** | ✅ CUMPLE | Método seedMinimo() con datos iniciales |
| **SIA1.5** | ✅ CUMPLE | Map<Tema, BancoPreguntas> + List<Pregunta> |
| **SIA1.6** | ✅ CUMPLE | Sobrecarga en BancoPreguntas y Evaluacion |
| **SIA1.7** | ✅ CUMPLE | LinkedHashMap en gestores |
| **SIA1.8** | ✅ CUMPLE | Menú con inserción y listado |
| **SIA1.9** | ✅ CUMPLE | Sistema por consola (ConsolaMenu) |
| **SIA2.3** | ✅ **NUEVO** | **Interfaces gráficas SWING completas** |
| **SIA2.4** | ✅ **NUEVO** | **Edición y eliminación de preguntas** |
| **SIA2.8** | ✅ **NUEVO** | **Try-catch con excepciones personalizadas** |

---

## 🚀 Cómo Ejecutar

### **Compilación:**
```bash
javac -d out -sourcepath src src/proyecto/evaluaciones/*.java src/proyecto/evaluaciones/dominio/*.java src/proyecto/evaluaciones/servicio/*.java src/proyecto/evaluaciones/ui/*.java src/proyecto/evaluaciones/excepciones/*.java
```

### **Ejecución (Interfaz Gráfica):**
```bash
java -cp out proyecto.evaluaciones.Main
```

### **Ejecución (Consola - Versión Original):**
```bash
# Cambiar Main.java para usar ConsolaMenu en lugar de VentanaPrincipal
```

---

## 📁 Persistencia de Datos

- **Archivo:** `csvs/notas.csv`
- **Formato:** `evaluacionId,rutEstudiante,nota`
- **Carga:** Automática al iniciar la aplicación
- **Guardado:** Automático al salir + exportación manual
- **Manejo de Errores:** Excepciones específicas para archivos

---

## 🎨 Capturas de Interfaz

El sistema incluye:
- 🏠 **Ventana Principal** con acceso a todas las funcionalidades
- 📝 **Gestión de Preguntas** con tabla, formularios y CRUD completo
- 📋 **Gestión de Evaluaciones** con creación y visualización
- 📊 **Gestión de Notas** con registro, búsqueda y exportación
- ⚠️ **Manejo de Errores** con mensajes claros y opciones de recuperación

---

## 👥 Desarrollado por
**Grupo de Programación Avanzada**  
*Proyecto SIA - Sistema de Información de Evaluaciones*

---

*Este proyecto cumple con los requisitos técnicos de la asignatura **Programación Avanzada (ICI2241)** incluyendo las nuevas funcionalidades SIA2.3, SIA2.4 y SIA2.8.*
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
Para compilar **todo el proyecto** y generar los archivos `.class` en la carpeta `out`, 
abre una terminal en la carpeta raíz del proyecto y ejecuta (para PowerShell en Windows):

javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

Si el comando anterior no te funciona, puedes usar este alternativo:

Get-ChildItem -Recurse -Filter *.java | ForEach-Object { javac -d out $_.FullName }

src/proyecto/evaluaciones/Main.java

## Ejecutar 
java -cp out proyecto.evaluaciones.Main

---

## Intereaccion con el menu 
===== Sistema de Evaluaciones =====
1. Crear banco de preguntas
2. Agregar pregunta a banco
3. Listar preguntas de banco
9. Salir
