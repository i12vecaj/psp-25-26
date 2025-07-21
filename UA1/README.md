![lope_logo](https://www.ceslopedevega.com/wp-content/uploads/2020/03/pruebalogo.svg_.png)

# UA1 - Programación Multiproceso

- Temporalización: **1T**
- Duración: **12 horas - 4 sesiones**
- Ponderación respecto a la asignatura: **20%**

## Resultados de Aprendizaje

En esta unidad de aprendizaje se desarrollan los siguientes resultados de aprendizaje:

- **RA1:** *Desarrolla aplicaciones compuestas por varios procesos reconociendo y aplicando principios de programación paralela.*

### Criterios de Evaluación

a) Se han analizado las características de los procesos y de su ejecución por el sistema operativo.

b) Se han caracterizado los hilos de ejecución y descrito su relación con los procesos.

c) Se han reconocido las características de la programación concurrente y sus ámbitos de aplicación.

d) Se han identificado las diferencias entre programación paralela y programación distribuida, sus ventajas e inconvenientes.

e) Se han utilizado clases para programar aplicaciones que crean subprocesos.

f) Se han utilizado mecanismos para sincronizar y obtener el valor devuelto por los subprocesos iniciados.

g) Se han desarrollado aplicaciones que gestionen y utilicen procesos para la ejecución de varias tareas en paralelo.

h) Se han depurado y documentado las aplicaciones desarrolladas.

## Contenidos

* Ejecutables. Procesos. Servicios. Problemas asociados a recursos compartidos.
* Estados de un proceso. Planificación de procesos por el sistema operativo.
* Hilos.
* Programación concurrente.
* Programación paralela y distribuida.
* Creación de procesos.
* Comunicación entre procesos.
* Gestión de procesos.
* Sincronización entre procesos.
* Programación de aplicaciones multiproceso.
* Depuración y documentación.



## Evaluación

La evaluación de esta unidad didáctica estará basada en la entrega de las tareas, corregidas automáticamente por los tests necesarios a superar y examen teórico y práctico de la materia, acorde a la siguiente tabla

| CRITERIO de EVALUACIÓN | PONDERACIÓN | INSTRUMENTOS de EVALUACIÓN|
|------------------------|-------------|-------------|
| a)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX1 (10%) |
| b)                     |12,5 %       | EX1 (100%)  |
| c)                     |12,5 %       | EX1 (100%)  |
| d)                     |12,5 %       | EX1 (100%)  |
| e)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX1 (10%) |
| f)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX1 (10%) |
| g)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX1 (10%) |
| h)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX1 (10%) |
|                        |**100 %**    |             |



### **T1 - Tarea 1 - Programación de Procesos en C**
##### **Criterios a), e), f), g), h)**
```
Fecha de Entrega: 01/10/2024 - 14:00:00
```
PROCESOS. Crea un único programa en C que implemente las siguientes Funcionalidades Requeridas (FRs):
* FR1: cree un proceso (existirán en realidad dos procesos, uno padre y el otro hijo) - 1 punto
* FR2: pedirá al usuario una variable - 1 punto
* FR3: el proceso padre restará 5 a dicha variable - 1 punto
* FR4: el proceso hijo le sumará 4 - 1 punto
* FR5: mostrará todos los valores por pantalla - 1 punto
* Implementa el control de errores - 2 puntos
* Documenta el código - 2 puntos

Y ahora ... ¿Entiendes lo que está pasando? ¿De verdad?


### **T2 - Tarea 2 - Programación de procesos en Java (I)**
##### **Criterios a), e), f), g), h)**
```
Fecha de Entrega: 09/10/2024 - 14:00:00
```
PROCESOS. Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

* FR1: lea una cadena de caracteres desde la entrada estándar hasta recibir un carácter de terminación, en concreto, un asterisco * - 2 puntos
* FR2: una vez recibido el caracter de terminación, muestre por pantalla toda la información leída - 2 puntos
* FR3: Crea después otro programa que ejecute el anterior - 2 puntos
* Implementa el control de errores - 2 puntos
* Documenta el código - 2 puntos


### **T3 - Tarea 3 - Programación de procesos en Java (II)**
##### **Criterios a), e), f), g), h)**
```
Fecha de Entrega: 16/10/2024 - 14:00:00
```
PROCESOS. Crea un programa en Java que admita argumentos desde main() y devuelva con System.exit() los siguientes valores:

- Si el número de argumentos es < 1 debe devolver 1
- Si el argumento es una cadena debe devolver 2
- Si el argumento es un número entero menor que 0 debe devolver 3
- En cualquier otro caso debe devolver 0

Realiza un segundo programa Java que ejecute al anterior. Este programa deberá mostrar por pantalla lo que sucede según el valor devuelto al ejecutar el programa principal.

Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

* FR1: admita argumentos desde main() - 1 punto
* FR2: devuelva con System.exit() los siguientes valores:
  - Si el número de argumentos es < 1 debe devolver 1 - 1 punto
  - Si el argumento es una cadena debe devolver - 1 punto
  - Si el argumento es un número entero menor que 0 debe devolver 3 - 1 punto
  - En cualquier otro caso debe devolver 0  - 1 punto
* FR3: Crea después otro programa que ejecute el anterior - 1 punto
* Implementa el control de errores - 2 puntos
* Documenta el código - 2 puntos


### **EX1 - Examen UA1 - Programación Multiproceso**
##### **Criterios a), b), c), d), e) f), g) y h)**
```
Fecha de Examen: 16/10/2024 - 16:45:00
```
El examen constará de dos partes:
* Una primera parte de teoría, centrada sobre todo en los criterios de evaluación b), c) y d).  Serán unas preguntas cortas, donde deberéis responder utilizando vuestras propias palabras.  Para esta parte de teoría daremos un tiempo fijo y no permitiré el acceso a internet salvo para subir el resultado al repositorio.
* La segunda parte será resolver un problema de sincronización de procesos en lenguaje de programación en C.
Os recuerdo que los "problemas técnicos" para compilar y comprobar el funcionamiento del programa es vuestra responsabilidad.  Podéis usar compiladores online, máquinas virtuales, máquinas físicas con linux, ...

## Recursos

- [Repositorio Asignatura](https://github.com/i12vecaj/psp-24-25)
- Apuntes del Profesorado //TODO
- Bibliografía Recomendada
  - **Programación de servicios y procesos - Técnico Superior en DAM.** *Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.*
- Moodle

## Autor

Creado por José David Vela Castro e inspirado en el trabajo de Dani Pérez y [José Luis González Sánchez](https://github.com/joseluisgs/ProgServiciosProcesos-00-2021-2022)

## Contacto
- Email: [jdvelaceslopedevega.com](mailto:jdvela@ceslopedevega.com)
