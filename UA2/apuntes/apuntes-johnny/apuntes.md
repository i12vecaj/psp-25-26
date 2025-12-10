# Guía de Estudio: Procesos, Hilos y Servicios Web en Java

## Cuestionario de Repaso

**Responda a las siguientes preguntas en 2-3 frases, basándose exclusivamente en el material proporcionado.**

1. ¿Cuál es el problema fundamental que resuelven los servicios web y cómo lo abordan?
2. Describa las tres características principales que hacen de Spring Boot una herramienta potente para crear APIs.
3. ¿Qué es una "condición de carrera" (race condition) y qué trío de herramientas esenciales proporciona Java para evitarla?
4. Explique el propósito de la clase `ProcessBuilder` en Java y mencione tres de los atributos de proceso que gestiona.
5. ¿Cuál es la diferencia fundamental entre un proceso y un hilo en el contexto de la programación en Java?
6. Dentro de la arquitectura de una aplicación Spring Boot, ¿cuáles son las responsabilidades de las anotaciones `@RestController`, `@Service` y `@Repository`?
7. ¿En qué consiste el patrón productor-consumidor y qué analogía se utiliza para explicar su funcionamiento?
8. Describa los estados _New_, _Runnable_, _Blocked_ y _Dead_ en el ciclo de vida de un hilo en Java.
9. ¿Cuál es la diferencia entre los métodos `wait()` y `notifyAll()` y por qué se recomienda usar `notifyAll()` en lugar de `notify()`?
10. ¿Qué son las pruebas unitarias y las pruebas de integración en el contexto del desarrollo de una API y por qué son ambas necesarias?

---

## Clave de Respuestas

1. **¿Cuál es el problema fundamental que resuelven los servicios web y cómo lo abordan?**  
   El problema fundamental es cómo permitir que aplicaciones totalmente distintas, escritas en lenguajes diferentes, puedan comunicarse a través de internet de forma ordenada y fiable. La solución es un servicio web, que funciona como un "camarero universal", ofreciendo una API (la carta) para que otros componentes de software puedan solicitar y recibir datos sin necesidad de conocer la lógica interna.

2. **Describa las tres características principales que hacen de Spring Boot una herramienta potente para crear APIs.**  
   Spring Boot simplifica el desarrollo gracias a tres características clave. Primero, los **starters**, que son dependencias preconfiguradas que preparan todo lo necesario para empezar. Segundo, la **autoconfiguración**, que configura la aplicación de forma inteligente basándose en las librerías presentes. Por último, incluye un **servidor web integrado**, lo que permite ejecutar la API directamente sin necesidad de configurar un servidor externo.

3. **¿Qué es una "condición de carrera" (race condition) y qué trío de herramientas esenciales proporciona Java para evitarla?**  
   Una condición de carrera es el caos que se produce cuando varios hilos intentan leer y escribir en el mismo recurso compartido sin orden, lo que puede corromper los datos y generar errores impredecibles. Para evitarlo, Java ofrece un trío de herramientas: la palabra clave `synchronized` para crear secciones críticas, el método `wait()` para que un hilo espere cuando no puede avanzar, y el método `notifyAll()` para despertar a los hilos en espera cuando las condiciones cambian.

4. **Explique el propósito de la clase `ProcessBuilder` en Java y mencione tres de los atributos de proceso que gestiona.**  
   La clase `ProcessBuilder` se utiliza para crear y gestionar procesos del sistema operativo desde Java. Cada instancia de `ProcessBuilder` gestiona una colección de atributos del proceso que se creará. Tres de estos atributos son: un **comando** (la lista de strings que representa el programa a invocar y sus argumentos), un **entorno** (las variables de entorno) y un **directorio de trabajo**.

5. **¿Cuál es la diferencia fundamental entre un proceso y un hilo en el contexto de la programación en Java?**  
   Un proceso es un programa en ejecución con su propio espacio de memoria y recursos, gestionado por el sistema operativo. En cambio, un hilo es una unidad de ejecución más ligera que funciona dentro del contexto de un proceso. Varios hilos pueden existir dentro de un mismo proceso, compartiendo su contexto de ejecución, lo que facilita la comunicación entre ellos pero requiere una sincronización cuidadosa.

6. **Dentro de la arquitectura de una aplicación Spring Boot, ¿cuáles son las responsabilidades de las anotaciones `@RestController`, `@Service` y `@Repository`?**  
   En una aplicación Spring Boot, el `@RestController` actúa como el "portero", recibiendo las peticiones HTTP entrantes. Este se las pasa al `@Service`, que es el "cerebro" y contiene la lógica de negocio principal. Si el servicio necesita acceder o guardar datos, se comunica con el `@Repository`, que es el único componente con acceso directo a la base de datos.

7. **¿En qué consiste el patrón productor-consumidor y qué analogía se utiliza para explicar su funcionamiento?**  
   El patrón productor-consumidor es un modelo de comunicación para la programación concurrente donde uno o más hilos (productores) generan datos y otros hilos (consumidores) los utilizan. Se comunican a través de una zona común llamada búfer o monitor, que actúa como árbitro. La analogía utilizada es la de una cinta transportadora en una fábrica: un operario (productor) pone piezas en la cinta (búfer) y otro operario (consumidor) las recoge, asegurándose de que el primero pare si la cinta está llena y el segundo espere si está vacía.

8. **Describa los estados _New_, _Runnable_, _Blocked_ y _Dead_ en el ciclo de vida de un hilo en Java.**  
   Un hilo comienza en el estado **New** tras ser creado con el constructor. Pasa al estado **Runnable** cuando se invoca su método `start()`, quedando listo para ser ejecutado por el planificador. El estado **Blocked** ocurre cuando el hilo suspende su ejecución temporalmente (por ejemplo, al invocar `sleep()` o `wait()`). Finalmente, el hilo llega al estado **Dead** cuando su método `run()` finaliza o se invoca `stop()`.

9. **¿Cuál es la diferencia entre los métodos `wait()` y `notifyAll()` y por qué se recomienda usar `notifyAll()` en lugar de `notify()`?**  
   El método `wait()` hace que un hilo se detenga y libere el monitor en el que está esperando, quedando "dormido" hasta que otro hilo lo despierte. El método `notifyAll()` despierta a todos los hilos que están esperando en ese mismo monitor. Se recomienda usar `notifyAll()` sobre `notify()` (que solo despierta a un hilo arbitrario) porque es más seguro y previene situaciones en las que la señal se pierde o el hilo incorrecto es despertado.

10. **¿Qué son las pruebas unitarias y las pruebas de integración en el contexto del desarrollo de una API y por qué son ambas necesarias?**  
    Las pruebas unitarias son pruebas rápidas y precisas que comprueban que cada componente individual de la aplicación (como una pieza de Lego) funciona correctamente de forma aislada. Las pruebas de integración verifican que varios componentes (como el motor y el chasis de Lego) funcionan bien juntos. Ambas son necesarias para asegurar que no solo las piezas individuales son perfectas, sino que el sistema completo es fiable y robusto.

---

## Preguntas de Ensayo

**Desarrolle sus respuestas de forma extensa, sintetizando información de las distintas fuentes proporcionadas.**

1. **Compare y contraste la programación multiproceso (utilizando `ProcessBuilder` en Java) con la programación multihilo.** Discuta las diferencias en términos de creación, comunicación entre unidades de ejecución, gestión de memoria y los mecanismos de sincronización necesarios en cada paradigma.

2. **Explique en profundidad el flujo completo de una petición en una API web moderna, desde que una aplicación cliente la envía hasta que recibe la respuesta.** Incorpore los conceptos de protocolo HTTP, arquitectura REST, verbos HTTP, códigos de estado y la organización interna de una aplicación Spring Boot (`@RestController`, `@Service`, `@Repository`).

3. **Analice el problema de la concurrencia en Java.** Detalle qué son las condiciones de carrera y por qué son peligrosas. A continuación, explique cómo el trío de herramientas `synchronized`, `wait()` y `notifyAll()` se combinan de forma elegante en el patrón productor-consumidor para crear una solución robusta y coordinada.

4. **Describa el proceso de despliegue de una aplicación Java moderna utilizando Docker.** Explique qué es un archivo JAR, un Dockerfile y un contenedor, y argumente por qué este método soluciona el clásico problema de "en mi máquina funciona".

5. **Basándose en las Condiciones de Bernstein, explique por qué la concurrencia no es lo mismo que el paralelismo.** Describa cómo estas condiciones determinan si dos conjuntos de instrucciones pueden ejecutarse concurrentemente y relacione esto con la necesidad de mecanismos de sincronización como `synchronized` cuando los hilos compiten por recursos compartidos.

---

## Glosario de Términos Clave

| Término                                             | Definición                                                                                                                                                                                                                                                   |
| :-------------------------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **API (Interfaz de Programación de Aplicaciones)**  | Funcionalidad ofrecida por un servicio web para que otros componentes de software accedan a recursos en red. Actúa como la "carta" de un restaurante, definiendo qué se puede pedir.                                                                         |
| **Arquitectura REST**                               | El plano o estilo de arquitectura más utilizado para diseñar servicios web. Se caracteriza por su simplicidad y por usar las reglas existentes de la web, como el protocolo HTTP y sus verbos (GET, POST, etc.).                                             |
| **Anotaciones (en Spring)**                         | Palabras que empiezan con `@` (como `@RestController`) que actúan como "pegatinas" para decirle a Spring qué función cumple una pieza de código, permitiendo la autoconfiguración y organización.                                                            |
| **Buffer (o Monitor)**                              | En el patrón productor-consumidor, es la zona común o almacén intermedio a través del cual los hilos productor y consumidor se comunican. Actúa como árbitro para que no se pisen entre ellos.                                                               |
| **Clase `ProcessBuilder`**                          | Una clase en Java utilizada para crear y gestionar procesos del sistema operativo. Permite configurar atributos como el comando a ejecutar, el directorio de trabajo y las variables de entorno.                                                             |
| **Condición de Carrera (Race Condition)**           | El caos que se produce cuando varios hilos acceden (leen y escriben) a un recurso compartido sin un orden establecido, lo que puede llevar a la corrupción de datos y a resultados impredecibles.                                                            |
| **Concurrencia**                                    | La capacidad de un sistema de ejecutar varias tareas o procesos de forma que sus instrucciones se intercalan en el tiempo. Permite que múltiples sucesos ocurran en el mismo período, aunque no necesariamente en el mismo instante (eso sería paralelismo). |
| **Contenedor (Docker)**                             | Una "caja" a medida que contiene una aplicación y todas sus dependencias. Se construye a partir de un Dockerfile y puede ejecutarse en cualquier sistema que tenga Docker, garantizando un funcionamiento consistente.                                       |
| **Dockerfile**                                      | Un archivo de instrucciones o "receta" que define los pasos para construir una imagen de contenedor Docker para una aplicación.                                                                                                                              |
| **Estados de un Hilo**                              | Las diferentes fases por las que pasa un hilo durante su ciclo de vida: _New_ (creado), _Runnable_ (listo para ejecutar), _Blocked_ (suspendido) y _Dead_ (finalizado).                                                                                      |
| **Hilo (Thread)**                                   | Una secuencia de código que se ejecuta dentro de un proceso. Es la unidad básica de ejecución en Java y permite a un programa realizar varias tareas de forma concurrente, compartiendo el contexto del proceso padre.                                       |
| **HTTP (Protocolo de Transferencia de Hipertexto)** | El protocolo fundamental que rige la web. Funciona de manera independiente en cada petición, es decir, no guarda memoria de peticiones anteriores, lo que permite la escalabilidad de internet.                                                              |
| **Método `join()`**                                 | Un método de la clase `Thread` que hace que el hilo actual espere a que otro hilo específico termine su ejecución antes de continuar. Se usa para coordinar tareas dependientes.                                                                             |
| **Método `wait()`**                                 | Un método que, dentro de un bloque sincronizado, suspende la ejecución del hilo actual y libera el monitor (el "pestillo"), permitiendo que otros hilos entren. El hilo permanece en espera hasta ser despertado por `notify()` o `notifyAll()`.             |
| **Método `notifyAll()`**                            | Un método que despierta a todos los hilos que estaban esperando en el mismo objeto monitor. Es la señal que indica que las condiciones han cambiado y pueden volver a intentar su ejecución.                                                                 |
| **Proceso**                                         | Un programa en ejecución. Posee su propio espacio de memoria, datos, pila y un descriptor de proceso (PCB). Varios hilos pueden ejecutarse dentro de un mismo proceso.                                                                                       |
| **Pruebas de Integración**                          | Pruebas que verifican que diferentes componentes de una aplicación funcionan correctamente juntos.                                                                                                                                                           |
| **Pruebas Unitarias**                               | Pruebas que verifican que una única pieza o componente de código funciona perfectamente de forma aislada. Son rápidas y van al grano.                                                                                                                        |
| **Spring Boot**                                     | Una de las herramientas más potentes del ecosistema Java para construir servicios web y APIs de forma rápida y sólida, gracias a sus starters, autoconfiguración y servidor web embebido.                                                                    |
| **`synchronized`**                                  | Palabra clave en Java que marca un método o bloque de código como una "sección crítica". Solo un hilo puede ejecutar un bloque synchronized sobre el mismo objeto a la vez, mientras los demás esperan en cola.                                              |
| **Tubería (pipe)**                                  | Un mecanismo de comunicación entre procesos que actúa como un fichero compartido. Permite el intercambio de información de forma unidireccional.                                                                                                             |
| **Verbos HTTP**                                     | Las acciones estándar definidas en el protocolo HTTP para manipular recursos. Los más comunes son GET (leer), POST (crear), PUT (actualizar) y DELETE (borrar).                                                                                              |
