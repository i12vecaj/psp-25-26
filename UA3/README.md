![lope_logo](https://www.ceslopedevega.com/wp-content/uploads/2020/03/pruebalogo.svg_.png)

# UA3 - Programacion de Comunicaciones en Red

- Temporalización: **1T y 2T**
- Duración: **12 horas**
- Ponderación respecto a la asignatura: **20%**

## Resultados de Aprendizaje

En esta unidad de aprendizaje se desarrollan los siguientes resultados de aprendizaje:

- **RA3:** Programa mecanismos de comunicación en red empleando sockets y analizando el escenario de ejecución

### Criterios de Evaluación

a) Se han identificado escenarios que precisan establecer comunicación en red entre varias aplicaciones.

b) Se han identificado los roles de cliente y de servidor y sus funciones asociadas.

c) Se han reconocido librerías y mecanismos del lenguaje de programación que permiten programar aplicaciones en red.

d) Se ha analizado el concepto de socket, sus tipos y características.

e) Se han utilizado sockets para programar una aplicación cliente que se comunique con un servidor.

f) Se ha desarrollado una aplicación servidor en red y verificado su funcionamiento.

g) Se han desarrollado aplicaciones que utilizan sockets para intercambiar información.

h) Se han utilizado hilos para implementar los procedimientos de las aplicaciones relativos a la comunicación en red.

## Contenidos

* Protocolos de comunicaciones (IP, TCP,UDP).
* Comunicación entre aplicaciones
* El modelo cliente/servidor
* El modelo p2p (peer-to-peer)
* Modelos híbridos.
* Sockets
* Concepto de socket
* Dominios y direcciones.
* Estilos de comunicación.
* Escenario cliente/servidor orientado a conexión.
* Escenario cliente/servidor sin conexión.
* Programación con sockets en Java.

## Evaluación

La evaluación de esta unidad didáctica estará basada en la entrega de las tareas, corregidas automáticamente por los tests necesarios a superar y examen teórico y práctico de la materia, acorde a la siguiente tabla

| CRITERIO de EVALUACIÓN | PONDERACIÓN | INSTRUMENTOS de EVALUACIÓN|
|------------------------|-------------|-------------|
| a)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
| b)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
| c)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
| d)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
| e)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
| f)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
| g)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
| h)                     |12,5 %       | T1 (15%), T2 (15%), T3 (15%), T4 (15%), T5 (15%) y EX3 (25%) |
|                        |**100 %**    |             |

Por favor, leed el documento [INSTRUCCIONES_ENTREGAS.md](..\INSTRUCCIONES_ENTREGAS.md)

### **Tarea 1 - Programación de Sockets 1**
##### **Criterios c), f), g), h)**

Crea un programa en Java que admita desde la línea de comandos una URL y visualice información sobre esta. Modifica el programa para que admita continuamente nuevas IP o URL y muestre la información hasta que el usuario inserte "localhost".

```
Fecha de Entrega: 08/01/2025
```

### **Tarea 2 - Programación de Sockets 2**
##### **Criterios a), f), g), h)**

Realiza un programa servidor TCP que acepte dos clientes.  Muestra por cada cliente conectado sus puertos local y remoto.

Crea también el programa cliente que se conecte a dicho servidor.  Muestra los puertos locales y remotos a los que está conectado su socket y la dirección IP de la máquina remota a la que se conecta.

```
Fecha de Entrega: 15/01/2025
```
### **Tarea 3 - Programación de Sockets 3**
##### **Criterios a), f), g), h)**

Crea un cliente que pida un mensaje al usuario por pantalla y lo mande al servidor. El servidor deberá recibir el mensaje, convertirlo todo a mayúsculas y devolvérselo al cliente, que lo imprimirá por pantalla.

```
Fecha de Entrega: 22/01/2025
```

### **Tarea 4 - Programación de Sockets 4**
##### **Criterios a), f), g), h)**

Crea un programa cliente usando sockets UDP que envíe el texto escrito desde la entrada estándar al servidor. El servidor devolverá la cadena en mayúsculas. El proceso de entrada de datos finalizará cuando el cliente introduzca un astrerisco. El servidor se encargará de procesar las cadenas de caracteres hasta recibir un asterisco.

Deberás establecer un tiempo de espera de varios segundos para que el método receive() del cliente se bloquee. Controlas las excepciones de IO e indica si los paquetes se han perdido. Haz varias pruebas de la aplicación sin ejecutar el servidor y ejecutando varios clientes a la vez.

```
Fecha de Entrega: 29/01/2025
```

### **Tarea 5 - Programación de Sockets 5**
##### **Criterios a), f), g), h)**

Crea una clase Java llamada Numeros que defina 3 atributos: uno entero, otros dos de tipo long. Crea un constructor con parámetros y otro vacío. Define los getters y setters y crea un programa cliente que introduzca por teclado el atributo entero.

Después, envía este objeto a un proceso servidor para que calcule el cuadrado y el cubo del número y envíe el objeto de vuelta al cliente con los cálculos realizados, almacenándolos en los atributos oportunos.

Deberás controlar todos los errores posibles (que el servidor no esté iniciado, que se produzcan excepciones o cortes abruptos del cliente o servidor, que el número introducido por pantalla por el clientea sea menor que 0, etcétera).

```
Fecha de Entrega: 29/01/2025
```

### **Actividad Evaluable UA3 - Programacion de Comunicaciones en Red**
##### **Criterios a), b), c), d), e) f), g) y h)**
Enunciado disponible en [actividad/README.md](actividad/README.md)

*¡¡ Preparad Presentación para el día 29 !!*

```
Fecha de Examen: 05/02/2025 - Fecha Defensa: 29/01/2025
```


### **Examen UA3 - Programacion de Comunicaciones en Red**
##### **Criterios a), b), c), d), e) f), g) y h)**
```
Fecha de Examen: 05/02/2025
```

## Recursos

- [Repositorio Asignatura](https://github.com/daniteleco/psp-22-23](https://github.com/i12vecaj/psp-24-25/)
- Apuntes del Profesorado //TODO
- Bibliografía Recomendada
  - **Programación de servicios y procesos - Técnico Superior en DAM.** *Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.*
- Moodle

## Autor

Creado por José David Vela Castro, basado en [Daniel Pérez Rodríguez](https://twitter.com/daniteleco) e inspirado en el trabajo de [José Luis González Sánchez](https://github.com/joseluisgs/ProgServiciosProcesos-00-2021-2022)

## Contacto
- Email: [jdvela@ceslopedevega.com](mailto:jdvela@ceslopedevega.com)
