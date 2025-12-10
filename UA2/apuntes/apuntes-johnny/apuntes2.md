# Documento Informativo: Síntesis de Desarrollo de Software y Programación Concurrente

## Resumen Ejecutivo

Este documento sintetiza los conceptos clave extraídos de diversas fuentes sobre el desarrollo de software moderno, abarcando desde la creación de servicios web hasta la gestión avanzada de procesos y la programación concurrente. Los puntos más críticos a destacar son:

- **Creación de APIs Web Modernas**: El desarrollo de servicios web se basa en la arquitectura REST, que utiliza el protocolo HTTP de manera simple y universal. Spring Boot emerge como la herramienta de elección en el ecosistema Java, simplificando drásticamente el proceso mediante starters, autoconfiguración y un servidor web integrado. El ciclo de vida completo incluye el diseño, la implementación con un flujo claro (`@RestController` → `@Service` → `@Repository`), pruebas rigurosas (unitarias y de integración), securización con herramientas como Spring Security y despliegue en la nube mediante Docker.
- **Fundamentos de la Programación Concurrente en Java**: La concurrencia, gestionada a través de hilos, es fundamental para el rendimiento de las aplicaciones, pero introduce desafíos como las condiciones de carrera. Java proporciona un robusto mecanismo de sincronización basado en el trío `synchronized`, `wait()` y `notifyAll()`. Estos elementos son la base para implementar patrones complejos y seguros.
- **El Patrón Productor-Consumidor**: Este es un patrón de diseño concurrente esencial que resuelve el problema de la comunicación segura entre hilos que generan datos (productores) y los que los utilizan (consumidores). Se implementa utilizando un búfer compartido y los mecanismos de sincronización de Java para evitar la corrupción de datos y los bloqueos, demostrando ser una solución elegante y omnipresente en el software del mundo real.
- **Gestión de Procesos en Java y C/C++**: La creación y gestión de procesos del sistema operativo es una capacidad clave. Mientras que en C/C++ en entornos Linux se utilizan funciones como `fork()` y `pipe()` para crear procesos y comunicarlos, Java abstrae esta funcionalidad a través de la clase `ProcessBuilder`. Esta clase permite configurar y lanzar subprocesos, gestionar su entorno, y redirigir sus flujos de entrada, salida y error de manera controlada y portable.

---

## 1. Desarrollo de Servicios Web con Arquitectura REST y Spring Boot

El proceso de creación de una API web moderna se puede desglosar en un ciclo de vida de cinco etapas, desde la conceptualización hasta el despliegue.

### 1.1. Los Cimientos: El Servicio Web

El problema fundamental que resuelven los servicios web es permitir la comunicación ordenada y fiable entre aplicaciones heterogéneas (diferentes lenguajes, plataformas, ubicaciones) a través de internet. La solución es un servicio web, que actúa como un "camarero universal". Este servicio ofrece una carta (la API) que define qué se puede pedir, sin que el cliente necesite saber cómo se "cocina" la petición internamente.

El protocolo subyacente que gobierna esta comunicación es **HTTP**, cuyo funcionamiento se asemeja al envío de correos electrónicos: cada petición es independiente y no guarda memoria de la anterior, una característica clave que permite la escalabilidad masiva de internet.

### 1.2. El Plano: Arquitectura REST

La arquitectura REST (Representational State Transfer) es el diseño más popular para construir servicios web debido a su simplicidad y su adhesión a los principios ya existentes en la web. A diferencia de alternativas como SOAP, REST no reinventa la rueda, sino que aprovecha directamente el protocolo HTTP.

- **Recursos**: Son los "sustantivos" de la API, identificados por URIs como `/productos` o `/usuarios`.
- **Acciones**: Son los "verbos" de la API, representados por los métodos HTTP estándar, que ofrecen un lenguaje universal para la manipulación de datos.

| Verbo HTTP | Acción                             |
| :--------- | :--------------------------------- |
| **GET**    | Leer información de un recurso.    |
| **POST**   | Crear un nuevo recurso desde cero. |
| **PUT**    | Actualizar un recurso existente.   |
| **DELETE** | Eliminar un recurso.               |

- **Respuestas**: El servidor comunica el resultado de una petición mediante códigos de estado HTTP, que estandarizan la comunicación. Por ejemplo, un `200 OK` indica éxito ("Marchando aquí tienes tu pedido"), mientras que un `404 Not Found` indica que el recurso no existe ("Uf lo siento de eso ya no nos queda").

### 1.3. La Caja de Herramientas: Spring Boot

Para la implementación en Java, **Spring Boot** es una de las herramientas más potentes. Su popularidad se debe a tres características que simplifican enormemente el desarrollo:

1. **Starters**: Dependencias preconfiguradas que integran todo lo necesario para una funcionalidad específica (ej. web, datos, seguridad) con un solo clic.
2. **Autoconfiguración**: Spring Boot analiza las librerías presentes en el proyecto y se configura automáticamente, ahorrando una gran cantidad de código repetitivo y de configuración manual.
3. **Servidor Web Integrado**: Incluye un servidor web (como Tomcat) de serie, permitiendo que la aplicación se ejecute directamente sin necesidad de desplegar un archivo WAR en un servidor externo.

### 1.4. Anatomía de una Petición en Spring Boot

El código se organiza de forma modular mediante anotaciones (`@`), que actúan como "pegatinas" para indicar la función de cada componente. El flujo de una petición sigue un patrón claro:

1. **`@RestController` (El Portero)**: Es el punto de entrada. Recibe las peticiones HTTP y las dirige al componente adecuado.
2. **`@Service` (El Cerebro)**: Contiene la lógica de negocio. Orquesta las operaciones necesarias para cumplir con la petición.
3. **`@Repository` (El Guardián de los Datos)**: Es el único componente con acceso directo a la base de datos. Se encarga de las operaciones de persistencia (guardar, buscar, actualizar, etc.).

### 1.5. Control de Calidad: Pruebas y Seguridad

Una API no está completa sin un riguroso control de calidad.

- **Pruebas**: Se realizan en dos niveles obligatorios:
  - **Pruebas Unitarias**: Verifican cada componente de forma aislada, como si se comprobara cada pieza de un Lego. Son rápidas y precisas.
  - **Pruebas de Integración**: Verifican que los distintos componentes funcionan correctamente juntos, como comprobar que el motor de un Lego encaja con el chasis.
- **Seguridad**: No es un añadido, sino una parte integral del diseño desde el principio. Herramientas como Spring Security proporcionan funcionalidades robustas ("la puerta blindada, la alarma") para proteger la API.

### 1.6. Despliegue en la Nube con Docker

La forma moderna y estándar de lanzar una aplicación al mundo es mediante **Docker**. El proceso es el siguiente:

1. **Empaquetar la Aplicación**: Se compila la aplicación en un archivo `.jar` auto-contenido.
2. **Crear un Dockerfile**: Se escribe una "receta" con las instrucciones para construir una imagen de contenedor que contenga la aplicación y sus dependencias.
3. **Ejecutar el Contenedor**: La imagen creada se puede ejecutar como un contenedor en cualquier máquina que tenga Docker, eliminando el clásico problema de "en mi máquina funciona".

> El debate actual en el mundo de las APIs se centra en si REST mantendrá su dominio o si nuevas tecnologías como GraphQL cambiarán las reglas del juego.

---

## 2. Programación Concurrente en Java

La programación concurrente permite a un programa realizar múltiples tareas simultáneamente, pero requiere una coordinación precisa para evitar el caos.

### 2.1. Hilos: La Base de la Concurrencia

- **Definición**: Un hilo es la unidad básica de ejecución en Java, una secuencia de código que se ejecuta dentro de un proceso más grande. Un solo proceso puede tener múltiples hilos, permitiendo la concurrencia.
- **Creación en Java**: Existen dos formas principales:
  1. **Extender la clase `Thread`**: Se hereda directamente de la clase `Thread` y se sobrescribe el método `run()`.
  2. **Implementar la interfaz `Runnable`**: Se implementa esta interfaz y su método `run()`. Esta es la forma recomendada por ser más flexible, ya que permite que la clase herede de otra.
- **El Problema Central (Condición de Carrera)**: El caos, conocido como _race condition_, ocurre cuando múltiples hilos intentan leer y escribir en datos compartidos sin ningún orden. Esto conduce a datos corruptos, resultados impredecibles y errores extremadamente difíciles de depurar.

### 2.2. Mecanismos de Sincronización

Java ofrece un trío de herramientas para imponer orden y coordinar hilos:

1. **`synchronized`**: Es la herramienta principal para la exclusión mutua. Se puede aplicar a métodos o a bloques de código. Funciona como el "pestillo de la puerta de un baño": solo un hilo puede entrar a la vez en una sección crítica de un objeto determinado. El resto debe esperar en cola.
2. **`wait()`**: Permite a un hilo, una vez dentro de una sección `synchronized`, pausar su ejecución voluntariamente si no puede continuar (p. ej., un consumidor encuentra un búfer vacío). Crucialmente, al llamar a `wait()`, el hilo libera el cerrojo (lock), permitiendo que otros hilos entren. Es como decir: "No puedo seguir, me echo una siesta y dejo la puerta abierta para que otro lo intente".
3. **`notifyAll()`**: Despierta a todos los hilos que estaban esperando (en estado de `wait`) en el mismo objeto. Se utiliza cuando un hilo ha cambiado el estado del objeto de una manera que podría permitir a los hilos en espera continuar (p. ej., un productor añade un elemento a un búfer vacío). Se prefiere `notifyAll()` sobre `notify()` porque es más seguro y previene la pérdida de señales, asegurando que el hilo correcto se despierte.

> Estos tres métodos (`wait`, `notify`, `notifyAll`) solo pueden ser invocados desde un bloque o método sincronizado.

### 2.3. El Patrón Productor-Consumidor

Este es un patrón clásico que utiliza el trío de sincronización para resolver un problema de comunicación concurrente muy común.

- **Componentes**:
  - **Productor**: Uno o más hilos que crean datos y los añaden a un espacio compartido.
  - **Consumidor**: Uno o más hilos que extraen datos de ese espacio para utilizarlos.
  - **Búfer/Monitor**: Una zona común (como una cola o lista) que actúa como intermediario y árbitro, asegurando que la comunicación sea ordenada.
- **Analogía**: Una cinta transportadora en una fábrica. El productor pone piezas en la cinta, y el consumidor las recoge. El sistema debe garantizar que:
  - El productor se detenga si la cinta está llena (`wait()`).
  - El consumidor espere si la cinta está vacía (`wait()`).
- **Implementación en Código**:
  - Los métodos para añadir (producir) y quitar (consumir) elementos del búfer están marcados como `synchronized`.
  - Dentro del consumidor, se usa un bucle `while (buffer.isEmpty()) { wait(); }`. El `while` es crucial: cuando el hilo se despierta, debe volver a comprobar la condición, ya que podría haber sido despertado por error o el estado podría haber cambiado de nuevo.
  - El productor tiene una lógica simétrica: `while (buffer.isFull()) { wait(); }`.
  - Después de que un productor añade un dato o un consumidor lo retira, se llama a `notifyAll()` para despertar a los hilos que puedan estar esperando por el cambio de estado.

Este patrón es fundamental y se encuentra en sistemas de colas, servidores web, bases de datos y muchas otras aplicaciones del mundo real.

### 2.4. Gestión y Ciclo de Vida de los Hilos

La clase `Thread` y el sistema Java gestionan el ciclo de vida de un hilo a través de varios estados y métodos.

- **Estados de un Hilo**:

  - **New (Nuevo)**: El hilo ha sido creado (`new Thread()`) pero aún no ha sido iniciado.
  - **Runnable (Ejecutable)**: El hilo ha sido iniciado (`start()`) y está listo para que el planificador del sistema le asigne tiempo de CPU.
  - **Blocked (Bloqueado)**: El hilo está vivo pero temporalmente inactivo, esperando que ocurra algo (ej. una operación de E/S, la adquisición de un lock con `synchronized`, o una llamada a `sleep()`, `wait()` o `join()`).
  - **Dead (Muerto)**: El hilo ha completado su ejecución (el método `run()` ha finalizado) o ha terminado por una excepción no capturada.

- **Métodos de Control Clave**:

| Método                   | Descripción                                                                                                                                            |
| :----------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`start()`**            | Inicia la ejecución del hilo, haciendo que pase al estado _Runnable_.                                                                                  |
| **`run()`**              | Contiene el código que se ejecutará en el hilo. Es llamado internamente por `start()`.                                                                 |
| **`sleep(long millis)`** | Pausa la ejecución del hilo actual por un número específico de milisegundos.                                                                           |
| **`join()`**             | Hace que el hilo actual espere a que el hilo sobre el que se llama termine su ejecución. Esencial para coordinar tareas dependientes.                  |
| **`interrupt()`**        | Envía una señal de interrupción a un hilo. Si el hilo está bloqueado (en `sleep`, `wait`, `join`), lanzará una `InterruptedException`.                 |
| **`isAlive()`**          | Devuelve `true` si el hilo ha sido iniciado y todavía no ha muerto.                                                                                    |
| **`yield()`**            | Sugiere al planificador que el hilo actual está dispuesto a ceder su uso de la CPU a otros hilos. Es una sugerencia, no una garantía.                  |
| **`setPriority(int p)`** | Establece la prioridad del hilo (de 1 a 10). Los hilos de mayor prioridad pueden recibir más tiempo de CPU, pero no está garantizado y depende del SO. |
| **`getId()`**            | Devuelve el identificador único del hilo.                                                                                                              |
| **`toString()`**         | Devuelve una representación en cadena del hilo, incluyendo su nombre, prioridad y grupo.                                                               |

> **Métodos Obsoletos**: `stop()`, `suspend()` y `resume()` están obsoletos (_deprecated_) porque son inseguros y pueden causar interbloqueos (_deadlocks_). La forma correcta de detener un hilo es mediante una condición de parada controlada o interrupciones.

---

## 3. Gestión de Procesos

Un proceso es un programa en ejecución. Los sistemas operativos modernos son multiproceso, repartiendo los recursos (CPU, RAM) entre ellos para crear la ilusión de ejecución simultánea.

### 3.1. Creación de Procesos en C/C++ (Linux)

En entornos tipo Unix, la gestión de procesos se realiza a través de llamadas al sistema.

- **Creación**: La función `fork()` es la principal para crear procesos. Crea un proceso hijo que es una copia exacta del proceso padre, con su propio PID, espacio de memoria y PCB (Process Control Block).
  - `fork()` devuelve `0` en el proceso hijo.
  - `fork()` devuelve el PID del hijo en el proceso padre.
  - `fork()` devuelve `-1` si ocurre un error.
- **Comunicación (Pipes)**: Las tuberías (`pipe`) son un mecanismo sencillo para la comunicación unidireccional entre procesos. Actúan como un fichero compartido donde un proceso escribe y otro lee. Si un proceso intenta leer de una tubería vacía, se bloquea.
- **Sincronización (Señales)**: Las señales (`signal`, `kill`, `pause`) se utilizan para coordinar la ejecución de los procesos, permitiendo que uno notifique a otro sobre eventos específicos.

### 3.2. Creación de Procesos en Java con ProcessBuilder

Java abstrae la creación de procesos del sistema operativo a través de la clase `ProcessBuilder`, proporcionando un enfoque multiplataforma.

- **Funcionalidad**: `ProcessBuilder` se utiliza para configurar y crear procesos del SO. Cada instancia gestiona una colección de atributos del proceso a lanzar. El método `start()` es el que finalmente crea el subproceso.
- **Atributos Configurables**:
  - **Comando**: Una lista de cadenas que representan el programa a ejecutar y sus argumentos.
  - **Directorio de Trabajo**: El directorio desde el cual se ejecutará el comando. Por defecto, es el directorio de trabajo del proceso Java actual.
  - **Entorno**: Un mapa de variables de entorno para el subproceso. Por defecto, hereda el entorno del proceso padre.
  - **Redirección de E/S Estándar**: Permite controlar la entrada estándar (stdin), salida estándar (stdout) y salida de error estándar (stderr) del subproceso. Se pueden redirigir a/desde ficheros, tuberías (por defecto), o heredar los flujos del proceso padre.
- **Nota sobre Sincronización**: La clase `ProcessBuilder` no es sincronizada. Si múltiples hilos acceden y modifican una misma instancia, se debe implementar sincronización externa.

**Ejemplos de uso de ProcessBuilder:**

| Ejemplo             | Descripción breve del funcionamiento                                                     |
| :------------------ | :--------------------------------------------------------------------------------------- |
| **Ejemplo 1**       | Abre el explorador de archivos de Windows (`EXPLORER.exe`).                              |
| **Ejemplo 2**       | Ejecuta `DIR` en CMD y muestra la salida por consola, carácter a carácter.               |
| **Ejemplo 2 Error** | Ejecuta un comando erróneo (`DIRR`), muestra errores y captura el código de salida.      |
| **Ejemplo 3**       | Ejecuta `DATE` en CMD, le envía una fecha por entrada y muestra la salida.               |
| **Ejemplo 4**       | Ejecuta otro programa Java (`EjemploLectura`), le envía texto y muestra la respuesta.    |
| **Ejemplo 5**       | Muestra variables de entorno, argumentos y ejecuta `LeerNombre` con un argumento.        |
| **Ejemplo 6**       | Ejecuta `DIRR` y redirige la salida estándar y los errores a ficheros separados.         |
| **Ejemplo 7**       | Ejecuta CMD leyendo la entrada desde un fichero `.bat` y redirige la salida.             |
| **Ejemplo 8**       | Ejecuta `DIR` y hereda directamente la salida del proceso padre (se muestra en consola). |
| **Ejemplo Lectura** | Lee una cadena por teclado y la muestra (usado por otros ejemplos).                      |
| **Leer Nombre**     | Muestra un nombre pasado como argumento y sale con código 1.                             |
| **Un Saludo**       | Imprime un saludo recibido como argumento cinco veces.                                   |

---

## 4. Distinción entre Programación Concurrente, Paralela y Distribuida

Aunque relacionados, estos tres paradigmas describen diferentes modelos de ejecución.

- **Programación Concurrente**: Se refiere a la capacidad de un sistema para gestionar múltiples sucesos o tareas que progresan al mismo tiempo. Sus instrucciones se pueden intercalar en el tiempo, incluso en un sistema con un solo procesador (monoprocesador). La concurrencia es una propiedad del programa.
- **Programación Paralela**: Implica que varias tareas se ejecutan simultáneamente de verdad, lo que requiere un sistema con múltiples unidades de procesamiento (multiprocesador o multinúcleo). El objetivo principal es disminuir el tiempo de ejecución dividiendo un problema en partes que se resuelven a la vez.
- **Programación Distribuida**: Los componentes de software o hardware que componen el sistema están ubicados en diferentes máquinas, interconectadas por una red. Se caracteriza por la concurrencia, la inexistencia de un reloj global y la posibilidad de fallos independientes entre componentes. El cloud computing es el ejemplo más claro.
