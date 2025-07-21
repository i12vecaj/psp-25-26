![lope_logo](https://www.ceslopedevega.com/wp-content/uploads/2020/03/pruebalogo.svg_.png)

# UA2 - Programación Multihilo

- Temporalización: **1T**
- Duración: **12 horas - 4 sesiones**
- Ponderación respecto a la asignatura: **20%**

## Resultados de Aprendizaje

En esta unidad de aprendizaje se desarrollan los siguientes resultados de aprendizaje:

- **RA2:** *Desarrolla aplicaciones compuestas por varios hilos de ejecución analizando y aplicando librerías específicas del lenguaje de programación.*

### Criterios de Evaluación

a) Se han identificado situaciones en las que resulte útil la utilización de varios hilos en un programa.

b) Se han reconocido los mecanismos para crear, iniciar y finalizar hilos.

c) Se han programado aplicaciones que implementen varios hilos.

d) Se han identificado los posibles estados de ejecución de un hilo y programado aplicaciones que los gestionen.

e) Se han utilizado mecanismos para compartir información entre varios hilos de un mismo proceso.

f) Se han desarrollado programas formados por varios hilos sincronizados mediante técnicas específicas.

g) Se ha establecido y controlado la prioridad de cada uno de los hilos de ejecución.

h) Se han depurado y documentado los programas desarrollados.


## Contenidos

* Concepto de hilo.
* Hilo vs. proceso.
* Estados de un hilo.
* Hilos de usuario e hilos de sistema.
* Usos comunes.
* Gestión de hilos en Java.
* Creación y ejecución de hilos.
* Atributos de un hilo. La prioridad.
* Interrumpiendo la ejecución de un hilo.
* Durmiendo a un hilo.
* Esperando la finalización de otro hilo.
* Hilos demonio.
* Procesamiento de excepciones en un hilo.
* Grupos de hilos.
* Pools de hilos.
* Realización de tareas periódicas.



## Evaluación

La evaluación de esta unidad didáctica estará basada en la entrega de las tareas, corregidas automáticamente por los tests necesarios a superar y examen teórico y práctico de la materia, acorde a la siguiente tabla

| CRITERIO de EVALUACIÓN | PONDERACIÓN | INSTRUMENTOS de EVALUACIÓN|
|------------------------|-------------|-------------|
| a)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
| b)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
| c)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
| d)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
| e)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
| f)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
| g)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
| h)                     |12,5 %       | T1 (30%), T2 (30%), T3 (30%) y EX2 (10%) |
|                        |**100 %**    |             |

Por favor, leed el documento [INSTRUCCIONES_ENTREGAS.md](..\INSTRUCCIONES_ENTREGAS.md)

### **T1 - Tarea 1 - Programación y sincronización de hilos en Java 1**
##### **Criterios a), b), c), d), e), f), g) y h)**
```
Fecha de Entrega: 06/11/2024 - 20:00:00
```
* FR1: Crea un programa en Java que lance 5 hilos. Cada hilo incrementará una variable contador de tipo entero en 1000 unidades. Esta variable estará compartida por todos los hilos. Comprueba el resultado final de la variable y reflexiona sobre el resultado. ¿Se obtiene el resultado esperado? - 3 puntos
* FR2: Modifica el programa anterior para sincronizar el acceso a dicha varaible. Lanza primero los hilos mediante la clase Thread y después mediante el interfaz Runnable. Comprueba los resultados e indica las variaciones - 3 puntos
* Implementa el control de errores básico - 2 puntos
* Documenta el código indicando el funcionamiento del programa y las diferencias que has observado entre el primer y el segundo apartado. - 2 puntos
* Entregables:
  * tareas\dev_X\tarea_1\ua2tarea1fr1.java
  * tareas\dev_X\tarea_1\ua2tarea1fr2.java
  * tareas\dev_X\tarea_1\ua2tarea1fr2runnable.java



### **T2 - Tarea 2 - Programación y sincronización de hilos en Java 2**
##### **Criterios a), b), c), d), e), f), g) y h)**
```
Fecha de Entrega: 13/11/2024 - 21:00:00
```
* FR1: Crea una clase CuentaCorriente, con un atributo que indique el saldo de la cuenta, el constructor que le da un valor inicial al saldo y después los métodos setter y getter. En estos métodos deberás añadir un sleep() aleatorio, que duerma al hilo durante un número de milisegundos que oscile entre 250 y 2000 (0,25s y 2s respectivamente). Añade también otro método que reciba una cantidad y la añada al saldo, indicando por pantalla el estado previo del saldo, el estado final y quién realiza el ingreso. Deberás definir los parámetros que reciba este método y deberás definirlo como synchronized - 2 puntos.
* FR2: Crea una clase que extienda de Thread, y desde el método run() usará el método de la clase CuentaCorriente que permite añadir saldo a la cuenta - 2 puntos.
* FR3: Crea en el método main un objeto de tipo CuentaCorriente asignando un valor inicial y visualiza el saldo inicial. Ahora crea varios hilos que compartan dicho objeto. Cada hilo recibirá un nombre y tendrá una cantidad de dinero. Lanza los hilos y espera a que finalicen para visualizar el saldo final de la cuenta - 2 puntos.
* FR4: Comprueba ahora los resultados quitando el modificador synchronized del método de la clase CuentaCorriente y del método que permite añadir saldo. ¿Cuál es la diferencia? ¿Por qué el resultado obtenido difiere respecto al apartado 3?  - 2 puntos.
* Implementa el control de errores básico - 1 punto
* Documenta el código indicando el funcionamiento del programa y las diferencias que has observado entre el tercer y el cuarto apartado. - 1 punto
* Entregables:
  * tareas\dev_X\tarea_2\\*


### **T3 - Tarea 3 - Programación y sincronización de hilos en Java 3**
##### **Criterios a), b), c), d), e), f), g) y h)**
```
Fecha de Entrega: 26/11/2024 - 23:59:00
```
* FR1: Crea un programa que reciba a través de sus argumentos una lista de ficheros de texto (procura que sean de un cierto tamaño, por ejemplo [El Quijote.txt](https://gist.githubusercontent.com/jsdario/6d6c69398cb0c73111e49f1218960f79/raw/8d4fc4548d437e2a7203a5aeeace5477f598827d/el_quijote.txt)) y cuente el número de caracteres que hay en cada fichero (ejecución secuencial).  Ayuda en este [enlace](https://javiergarciaescobedo.es/programacion-en-java/15-ficheros/42-leer-un-fichero-caracter-a-caracter) - 2 puntos.
* FR2: Modifica el programa para que cree un hilo para cada fichero (ejecución concurrente)  - 2 puntos.
* FR3: Compara el tiempo de ejecución entre los dos apartados anteriores - 2 puntos.
* Implementa el control de errores básico - 2 puntos
* Documenta el código indicando el funcionamiento del programa y las diferencias que has observado entre el primer y el segundo apartado - 2 puntos
* Entregables:
  * tareas\dev_X\tarea_3\\*

Para el FR3 puedes usar el siguiente código, o alguna variación del mismo:

```
long t_comienzo, t_fin;
t_comienzo = System.currentTimeMillis();
Hilo(); // Se ejecuta el hilo o el proceso (en el caso secuencial)
t_fin = System.currentTimeMillis();
long t_total = t_fin - t_comienzo;
```

### **EX2 - Examen UA2 - Programación Multihilo**
##### **Criterios a), b), c), d), e), f), g) y h)**
```
Fecha de Examen: xx/12/2024 - xx:xx:00
```

## Recursos

- [Canal SLACK](https://psp-2024-2025.slack.com/)
- [Repositorio Asignatura](https://github.com/i12vecaj/psp-24-25/)
- Apuntes del Profesorado //TODO
- Bibliografía Recomendada
  - **Programación de servicios y procesos - Técnico Superior en DAM.** *Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.*

## Autor

Creado por José David Vela e inspirado en el trabajo de [Daniel Pérez Rodríguez](https://twitter.com/daniteleco) y [José Luis González Sánchez](https://github.com/joseluisgs/ProgServiciosProcesos-00-2021-2022)

## Contacto
- Email: [jdvela@ceslopedevega.com](mailto:jdvela@ceslopedevega.com)
