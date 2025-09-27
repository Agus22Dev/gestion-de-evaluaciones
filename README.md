# 📚 Sistema de Evaluaciones Digitales - Versión SIA2

Este proyecto implementa un **Sistema de Evaluaciones Digitales** completo para un colegio.  
Su finalidad es **gestionar bancos de preguntas**, **crear evaluaciones** y **registrar notas de estudiantes** de manera estructurada, utilizando **Java 11**, **Swing GUI** y el **Java Collections Framework**.  

El sistema ahora cuenta con **interfaces gráficas completas**, **manejo avanzado de excepciones** y **funcionalidades de edición y eliminación**, cumpliendo con los requisitos SIA2.3, SIA2.4 y SIA2.8 de *Programación Avanzada (ICI2241)*.

---

## 🆕 Nuevas Funcionalidades Implementadas (SIA2)

### ✅ **SIA2.2 - Persistencia CSV Completa**
- **Carga automática**: Al iniciar la aplicación desde archivos CSV
- **Guardado automático**: Al cerrar la aplicación y exportación manual
- **Archivos gestionados**: evaluaciones.csv, notas.csv, preguntas.csv, temas.csv
- **Manejo de errores**: Con excepciones personalizadas para archivos

### ✅ **SIA2.3 - Interfaces Gráficas (SWING)**
- **Ventana Principal**: Menú principal con navegación a todas las funcionalidades
- **Gestión de Preguntas**: Interfaz completa para CRUD de preguntas y bancos
- **Gestión de Evaluaciones**: Creación y visualización de evaluaciones
- **Gestión de Notas**: Registro, búsqueda y exportación de notas
- **Componentes SWING**: JTable, JComboBox, JSpinner, JFileChooser, etc.

### ✅ **SIA2.4 - Edición y Eliminación**
- **Editar Preguntas**: Selección desde tabla y modificación de datos
- **Eliminar Preguntas**: Confirmación y eliminación segura
- **Editar Evaluaciones**: Modificación de datos de evaluaciones existentes
- **Validación de Formularios**: Controles de entrada robustos
- **Actualización en Tiempo Real**: Tablas que se actualizan automáticamente

### ✅ **SIA2.5 - Funcionalidad Propia: Filtrado por Criterio**
- **Filtro de Notas**: Buscar notas por ID de evaluación específica
- **Estadísticas**: Promedio, nota máxima, mínima por evaluación
- **Filtros Avanzados**: Subconjuntos de datos basados en criterios específicos

### ✅ **SIA2.7 - Sobreescritura de Métodos**
- **JTable personalizada**: @Override del método isCellEditable() 
- **ActionListeners**: @Override en múltiples interfaces gráficas
- **Event Handlers**: Sobreescritura de métodos de eventos Swing

### ✅ **SIA2.8 - Manejo de Excepciones Avanzado**
- **Try-Catch Específicos**: Manejo granular de diferentes tipos de errores
- **Excepciones Personalizadas**: 
  - `ValidacionException`: Para errores de validación de datos
  - `PersistenciaException`: Para errores de archivo/persistencia
- **Mensajes de Usuario**: Errores comprensibles y acciones sugeridas
- **Recuperación de Errores**: Opciones de reintento y manejo graceful

### ✅ **SIA2.10 - Reportes en Archivo TXT**
- **Generación de Reportes**: Sistema completo de reportes estadísticos
- **Contenido detallado**: Temas, bancos, evaluaciones, notas y estadísticas
- **Formato estructurado**: Encabezados, secciones organizadas y datos formateados
- **Exportación**: Guardado automático en directorio 'reportes/'

### ✅ **SIA2.12 - CRUD en Primera Colección**
- **Gestión completa de Bancos**: Agregar, eliminar, modificar bancos de preguntas
- **Operaciones en Evaluaciones**: CRUD completo en evaluaciones
- **Gestión de Temas**: Creación y modificación de temas

### ✅ **SIA2.13 - Búsqueda en Múltiples Niveles**
- **Búsqueda por Evaluación**: Localizar notas específicas por ID de evaluación
- **Búsqueda por Estudiante**: Filtrar por RUT de estudiante
- **Búsqueda Multinivel**: Búsquedas que abarcan múltiples colecciones relacionadas

---

## 🚀 Funcionalidades Principales

### 📋 **Gestión de Bancos de Preguntas** (SIA2.12)
- Crear bancos de preguntas por tema (ej: Matemáticas, Lenguaje)
- **Agregar/Editar/Eliminar preguntas** en un banco (CRUD completo)
- **Modificar bancos existentes** con validaciones
- Listar preguntas registradas con interfaz de tabla
- Validación de formatos y datos de entrada

### 📝 **Creación y Gestión de Evaluaciones** (SIA2.4, SIA2.12)
- Instanciar evaluaciones a partir de bancos de preguntas
- **Editar evaluaciones existentes** (título, fecha, ponderación)
- **Eliminar evaluaciones** con confirmación
- Visualizar detalles completos de evaluaciones
- Estadísticas básicas de preguntas por evaluación

### 📊 **Registro y Análisis de Notas** (SIA2.5, SIA2.13)
- Registrar notas con validación RUT/nota (Try-catch avanzado)
- **Buscar notas por evaluación** con estadísticas (SIA2.13)
- **Filtros por criterio específico** - Subconjunto filtrado (SIA2.5)
- Exportar datos a CSV con selector de archivo
- **Reportes TXT detallados** con estadísticas completas (SIA2.10)
- Carga automática de datos al inicio

### 🔍 **Funcionalidades de Búsqueda Avanzada** (SIA2.13)
- **Búsqueda multinivel**: En bancos, evaluaciones y notas
- **Filtros por evaluación**: Notas específicas por ID de evaluación
- **Búsqueda por estudiante**: Filtrar registros por RUT
- **Estadísticas en tiempo real**: Promedio, máxima, mínima por grupo

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

### **📋 SIA1 - Avance (COMPLETADO)**
| Requisito | ✅ Estado | Implementación |
|-----------|---------|----------------|
| **SIA1.1** | ✅ CUMPLE | Sistema de evaluaciones para colegio |
| **SIA1.2** | ✅ CUMPLE | 6 clases del dominio bien estructuradas |
| **SIA1.3** | ✅ CUMPLE | Atributos privados con getters/setters |
| **SIA1.4** | ✅ CUMPLE | Datos iniciales en código |
| **SIA1.5** | ✅ CUMPLE | Map<Tema, BancoPreguntas> + List<Pregunta> |
| **SIA1.6** | ✅ CUMPLE | Sobrecarga en BancoPreguntas y Evaluacion |
| **SIA1.7** | ✅ CUMPLE | LinkedHashMap en gestores |
| **SIA1.8** | ✅ CUMPLE | Menú con inserción y listado |
| **SIA1.9** | ✅ CUMPLE | Sistema por consola (ConsolaMenu) |
| **SIA1.10** | ✅ CUMPLE | GitHub con 3+ commits |

### **🚀 SIA2 - Final (COMPLETADO)**
| Requisito | ✅ Estado | Implementación |
|-----------|---------|----------------|
| **SIA2.1** | ⚠️ PENDIENTE | Diagrama UML (será incluido en informe) |
| **SIA2.2** | ✅ **IMPLEMENTADO** | **Persistencia CSV completa** |
| **SIA2.3** | ✅ **IMPLEMENTADO** | **Interfaces gráficas SWING completas** |
| **SIA2.4** | ✅ **IMPLEMENTADO** | **Edición y eliminación de elementos** |
| **SIA2.5** | ✅ **IMPLEMENTADO** | **Filtrado por criterio (notas por evaluación)** |
| **SIA2.6** | ✅ **IMPLEMENTADO** | **Código bien modularizado** |
| **SIA2.7** | ✅ **IMPLEMENTADO** | **Sobreescritura de métodos (isCellEditable)** |
| **SIA2.8** | ✅ **IMPLEMENTADO** | **Try-catch con excepciones personalizadas** |
| **SIA2.9** | ✅ **IMPLEMENTADO** | **ValidacionException y PersistenciaException** |
| **SIA2.10** | ✅ **IMPLEMENTADO** | **Reporte TXT con estadísticas completas** |
| **SIA2.11** | ✅ **IMPLEMENTADO** | **GitHub con commits adicionales** |
| **SIA2.12** | ✅ **IMPLEMENTADO** | **CRUD en primera colección (bancos)** |
| **SIA2.13** | ✅ **IMPLEMENTADO** | **Búsqueda en múltiples niveles** |

**🎯 ESTADO FINAL: 12/13 requisitos SIA2 implementados (92% completado)**

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

## 📁 Persistencia de Datos (SIA2.2)

### **Archivos CSV Gestionados:**
- **notas.csv**: `evaluacionId,rutEstudiante,nota`
- **evaluaciones.csv**: `id,titulo,fecha,temaId,ponderacion,preguntasIds`
- **preguntas.csv**: Preguntas con opciones y respuestas
- **temas.csv**: Temas y sus descripciones

### **Características de Persistencia:**
- **Carga**: Automática al iniciar la aplicación (SIA2.2)
- **Guardado**: Automático al salir + exportación manual
- **Manejo de Errores**: PersistenciaException para archivos (SIA2.9)
- **Formato Robusto**: Validación de datos en carga/guardado
- **Reportes TXT**: Generación de reportes estadísticos (SIA2.10)

---

## 🎨 Características del Sistema

### **Interfaces Gráficas (SIA2.3):**
- 🏠 **Ventana Principal** con acceso a todas las funcionalidades
- 📝 **Gestión de Preguntas** con tabla, formularios y CRUD completo
- 📋 **Gestión de Evaluaciones** con creación, edición y eliminación
- 📊 **Gestión de Notas** con registro, búsqueda y exportación
- ⚠️ **Manejo de Errores** con mensajes claros y opciones de recuperación

### **Manejo de Excepciones (SIA2.8, SIA2.9):**
- **ValidacionException**: Errores de validación de datos con detalles
- **PersistenciaException**: Errores de archivo con contexto específico
- **Try-Catch granular**: Manejo específico para cada tipo de operación
- **Mensajes usuario-friendly**: Explicaciones claras y acciones sugeridas

### **Funcionalidades Avanzadas:**
- **Sobreescritura de métodos** (SIA2.7): JTable personalizada
- **Búsqueda multinivel** (SIA2.13): En múltiples colecciones
- **Filtrado por criterio** (SIA2.5): Subconjuntos específicos
- **Reportes estadísticos** (SIA2.10): TXT con análisis completo

---

## 👥 Desarrollado por
**Grupo de Programación Avanzada**  
*Proyecto SIA - Sistema de Información de Evaluaciones*

---

*Este proyecto cumple con **12 de 13 requisitos** de la asignatura **Programación Avanzada (ICI2241)** implementando todas las funcionalidades SIA2 requeridas. El único elemento pendiente es el diagrama UML (SIA2.1) que será incluido en el informe final.*

**🎯 Estado: 92% completado - Proyecto SIA2 prácticamente finalizado**
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
