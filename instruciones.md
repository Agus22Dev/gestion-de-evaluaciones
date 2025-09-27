# Objetivo

El presente proyecto tiene por objeto que los alumnos implementen una aplicación informática integrando los contenidos vistos en el curso y en los talleres de ayudantía. El proyecto debe cumplir con los requisitos definidos en este enunciado.

## Requisitos generales del sistema

- Los proyectos consideran el desarrollo de un producto de software que dé respuesta a una necesidad de la sociedad.
- Ambos softwares deberán ser implementados en su totalidad por el grupo de trabajo.
- Los problemas a abordar deben ser escogidos y modelados por los propios alumnos en grupos de 3 personas. Existirá la posibilidad de formar algunos grupos de 4 en caso de que el número de estudiantes no permita que todos los grupos sean de 3 personas.
- Toda entrega se realizará por el aula virtual.
- Los proyectos deberán ser desarrollados bajo el lenguaje **JAVA**, usando el **Oracle JDK 11**, y los editores **Netbeans 21** o **Eclipse**.

## Elección de temas

- Los alumnos deben cuidar de no seleccionar temas repetidos de otros semestres.
- El proyecto debe ser nuevo y desarrollado durante el semestre por el propio grupo. Por tanto no se puede reutilizar códigos de proyectos pasados o de terceros.
## Entregas de Proyecto

La signatura considera la entrega de 2 proyectos. Uno sobre un sistema de información (SIA) que gestiona datos y otro sobre un videojuego (GM) a partir del uso del motor LibGDX. Cada proyecto consta de 2 etapas y sus entregables: **Avance** y **Final**.

### Proyecto Sistema de Información (SIA) - Avance

**SIA1.1** Realizar un análisis de los datos a utilizar y principales funcionalidades a implementar que dan sentido a la realización del proyecto.

**SIA1.2** Diseño conceptual de clases del Dominio y su código en Java.

**SIA1.3** Todos los atributos de todas las clases deben ser privados y poseer sus respectivos métodos de lectura y escritura (getter y setter).

**SIA1.4** Se deben incluir datos iniciales dentro del código.

**SIA1.5** Diseño conceptual y codificación de 2 colecciones de objetos, con la 2ª colección anidada como muestra la figura. Las colecciones pueden ser implementadas mediante arreglos o clases del Java Collections Framework (JCF).

**SIA1.6** Diseño conceptual y codificación de 2 clases que utilicen sobrecarga de métodos (no de constructores).

**SIA1.7** Diseño conceptual y codificación de al menos 1 clase mapa del Java Collections Framework.

**SIA1.8** Se debe hacer un menú para el Sistema donde ofrezca las funcionalidades de: 
1. Inserción Manual / agregar elemento
2. Mostrar por pantalla listado de elementos

Esto para la 2ª colección de objetos (colección anidada) del SIA1.5.

**SIA1.9** Todas las funcionalidades deben ser implementadas mediante consola (Sin ventanas).

**SIA1.10** Utilización de GitHub (Realización de al menos 3 Commit).


### Proyecto Sistema de Información (SIA) - Final

La integración de todo lo entregado anteriormente (entrega de avance) más lo detallado a continuación:

**SIA2.1** Diseño de diagrama de clases UML.

**SIA2.2** Persistencia de datos utilizando archivo de texto, CSV, Excel, o conexión con DBMS local (ej. MySQL). Utiliza sistema batch (carga datos al iniciar la aplicación y graba al salir).

**SIA2.3** La implementación de todas las interfaces gráficas (ventanas) para interactuar con el usuario, considerando componentes SWING.

**SIA2.4** Se debe hacer un menú para el Sistema donde ofrezca las funcionalidades de:
1. Edición o modificación del elemento
2. Eliminación del elemento

Esto para la 2ª colección de objetos (colección anidada) del SIA1.5.

**SIA2.5** Se deben incluir al menos 1 funcionalidad propia que sean de utilidad para el negocio (distintas de la inserción, edición, eliminación y reportes). Específicamente:
- **Subconjunto filtrado por criterio**: considera la selección de un subconjunto de objetos basado en un criterio específico, involucrando 1 o más colecciones. Por ejemplo, selección de los alumnos con nota final entre 4,0 y 7,0 de entre todos los cursos; o seleccionar a todos los pasajeros que tengan asiento impar de entre todos los buses de la compañía.

**SIA2.6** El código fuente debe estar bien modularizado de acuerdo a lo descrito en el informe además de seguir las buenas prácticas de documentación interna y legibilidad.

**SIA2.7** Diseño y codificación de 2 (dos) clases que utilicen sobreescritura de métodos.

**SIA2.8** Implementar el manejo de excepciones capturando errores potenciales específicos mediante Try-catch.

**SIA2.9** Crear 2 clases que extiendan de una Excepción y que se utilicen en el programa.

**SIA2.10** Se debe generar un reporte en archivo txt que considere mostrar datos de la colección de objetos (ej: csv).

**SIA2.11** Continuidad en la utilización de GitHub (Realización de al menos 3 Commit adicionales a los ya hechos en el avance).

**SIA2.12** Implementar las funcionalidades de agregar, eliminar o modificar en elementos de la 1ra colección o nivel.

**SIA2.13** Implementar la funcionalidad de buscar elemento en 1 o más niveles.


### Funcionalidades Opcionales

**SIA3.1** *(opcional)* Se puede utilizar un componente gráfico estadístico (JFreeChart u otro) en ventana.

**SIA3.2** *(opcional)* Se puede generar un archivo de salida de planilla de cálculo (.xls o .xlsx).

**SIA3.3** *(opcional)* Generar documentación con Javadoc.

**SIA3.4** *(opcional)* Considerar la implementación del patrón Modelo-Vista-Controlador (MVC) en la arquitectura del sistema.

**SIA3.5** *(opcional)* Se puede utilizar JavaFX para las ventanas.