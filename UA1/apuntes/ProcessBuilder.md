<div align="center">

# Class ProcessBuilder:

[Enlace a la documentación oficial](https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html)

</div>

```sh
java.lang.Object
	java.lang.ProcessBuilder

public final class ProcessBuilder
extends Object
```

Esta clase se utiliza para crear procesos del sistema operativo.

Cada instancia de `ProcessBuilder` gestiona una colección de atributos de proceso. El método [`start()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#start()>) crea una nueva instancia de [`Process`](https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html) con esos atributos. El método [`start()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#start()>) puede invocarse repetidamente desde la misma instancia para crear nuevos subprocesos con atributos idénticos o relacionados.

Cada _process builder_ gestiona los siguientes atributos del proceso:

- Un _comando_, una lista de strings que representa el archivo de programa externo que se va a invocar y sus argumentos, si los hay. Qué listas de cadenas representan un comando válido del sistema operativo depende del sistema. Por ejemplo, es común que cada argumento conceptual sea un elemento en esta lista, pero hay sistemas operativos donde se espera que los programas analicen ellos mismos la línea de comandos. En tales sistemas, una implementación de Java podría requerir que los comandos contengan exactamente dos elementos.

- Un _entorno_, que es una asignación dependiente del sistema de variables a valores. El valor inicial es una copia del entorno del proceso actual (ver [`System.getenv()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/System.html#getenv()>)).

- Un _directorio de trabajo_. El valor predeterminado es el directorio de trabajo actual del proceso, normalmente el directorio nombrado por la propiedad del sistema `user.dir`.

- Una fuente de _input_ (entrada) _estándar_. Por defecto, el subproceso lee la entrada desde una tubería (pipe). El código Java puede acceder a esta tubería mediante el flujo de salida retornado por [`Process.getOutputStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getOutputStream()>). Sin embargo, la entrada estándar puede redirigirse a otra fuente utilizando [`redirectInput`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#redirectInput(java.lang.ProcessBuilder.Redirect)>). En ese caso, Process.[`Process.getOutputStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getOutputStream()>) devolverá un _flujo de salida nulo_, en el cual:

  - los métodos [`write`](<https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html#write(int)>) siempre lanzan una `IOException`
  - el método [`close`](<https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html#close()>) no hace nada

- Un destino para la _salida estándar_ y los _errores estándar_. Por defecto, el subproceso escribe la salida estándar y los errores estándar en tuberías. El código Java puede acceder a estas tuberías mediante los flujos de entrada retornados por [`Process.getInputStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getInputStream()>) y [`Process.getErrorStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getErrorStream()>). Sin embargo, la salida estándar y los errores estándar pueden redirigirse a otros destinos usando [`redirectOutput`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#redirectOutput(java.lang.ProcessBuilder.Redirect)>) y [`redirectError`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#redirectError(java.lang.ProcessBuilder.Redirect)>). En este caso, [`Process.getInputStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getInputStream()>) y/o [`Process.getErrorStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getErrorStream()>) devolverán un _flujo de entrada nulo_, en el cual:

  - los métodos [`read`](<https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html#read()>) siempre devuelven -1
  - el método [`available`](<https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html#available()>) siempre devuelve 0
  - el método [`close`](<https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html#close()>) no hace nada

- Una propiedad `redirectErrorStream`. Inicialmente, esta propiedad es false, lo que significa que la salida estándar y los errores estándar de un subproceso se envían a dos flujos separados, que pueden ser accedidos usando los métodos [`Process.getInputStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getInputStream()>) y [`Process.getErrorStream()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getErrorStream()>).
  Si el valor se establece en true, entonces:

      - el error estándar se fusiona con la salida estándar y siempre se envía al mismo destino (esto facilita correlacionar los mensajes de error con su salida correspondiente)
      - el destino común de error estándar y salida estándar puede redirigirse usando [`redirectOutput`](https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#redirectOutput(java.lang.ProcessBuilder.Redirect))
      - cualquier redirección configurada con [`redirectError`](https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#redirectError(java.lang.ProcessBuilder.Redirect)) se ignora al crear el subproceso
      - el flujo retornado por [`Process.getErrorStream()`](https://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#getErrorStream()) siempre será un [`flujo de entrada nulo`](https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#redirect-output)

Modificar los atributos de un ProcessBuilder afectará a los procesos que se inicien posteriormente con el método [`start()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#start()>) de ese objeto, pero nunca afectará a los procesos iniciados previamente ni al propio proceso Java.

La mayor parte de la comprobación de errores se realiza en el método [`start()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#start()>). Es posible modificar el estado de un objeto de manera que [`start()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#start()>) falle. Por ejemplo, establecer el atributo command como una lista vacía no lanzará una excepción a menos que se invoque [`start()`](<https://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#start()>).

**Ten en cuenta que esta clase no es sincronizada**. Si varios hilos acceden a una instancia de `ProcessBuilder` simultáneamente, y al menos uno de ellos modifica estructuralmente alguno de los atributos, _se debe_ sincronizar externamente.

Iniciar un nuevo proceso con el directorio de trabajo y entorno predeterminados es fácil:

```sh
Process p = new ProcessBuilder("myCommand", "myArg").start();
```

Aquí tienes un ejemplo que inicia un proceso con un directorio de trabajo y entorno modificados, y redirige la salida estándar y los errores para que se añadan a un archivo de registro:

```sh
ProcessBuilder pb =
  new ProcessBuilder("myCommand", "myArg1", "myArg2");
Map<String, String> env = pb.environment();
env.put("VAR1", "myValue");
env.remove("OTHERVAR");
env.put("VAR2", env.get("VAR1") + "suffix");
pb.directory(new File("myDir"));
File log = new File("log");
pb.redirectErrorStream(true);
pb.redirectOutput(Redirect.appendTo(log));
Process p = pb.start();
assert pb.redirectInput() == Redirect.PIPE;
assert pb.redirectOutput().file() == log;
assert p.getInputStream().read() == -1;
```

Para iniciar un proceso con un conjunto explícito de variables de entorno, primero llama a [`Map.clear()`](<https://docs.oracle.com/javase/7/docs/api/java/util/Map.html#clear()>) antes de añadir las variables de entorno.

## Enlaces a los ejemplos (repo psp-25-26, [📁 procesos](https://github.com/i12vecaj/psp-25-26/tree/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos)):

| Ejemplo                                                                                                                                 | Descripción breve del funcionamiento                                                                             |
| --------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| [Ejemplo 1](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo1.java)             | Abre el explorador de archivos de Windows (EXPLORER.exe)                                                         |
| [Ejemplo 2](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo2.java)             | Ejecuta DIR en CMD y muestra la salida por consola, carácter a carácter                                          |
| [Ejemplo 2 Error](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo2Error.java)  | Ejecuta un comando erróneo (DIRR), muestra errores por pantalla y captura el código de salida                    |
| [Ejemplo 3](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo3.java)             | Ejecuta DATE en CMD, le envía una fecha por entrada y muestra la salida por consola                              |
| [Ejemplo 4](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo4.java)             | Ejecuta otro programa Java (EjemploLectura) en la carpeta bin, le envía texto por entrada y muestra la respuesta |
| [Ejemplo 5](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo5.java)             | Muestra variables de entorno, argumentos del proceso y ejecuta LeerNombre con argumento "Luis"                   |
| [Ejemplo 6](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo6.java)             | Ejecuta DIRR y redirige la salida estándar a salida.txt y los errores a error.txt                                |
| [Ejemplo 7](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo7.java)             | Ejecuta CMD leyendo la entrada desde un fichero .bat y redirige salida y errores a ficheros                      |
| [Ejemplo 8](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Ejemplo8.java)             | Ejecuta DIR y hereda directamente la salida del proceso padre (se muestra directamente en consola)               |
| [Ejemplo Lectura](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/EjemploLectura.java) | Lee una cadena por teclado y la muestra (usado por otros ejemplos como Ejemplo4)                                 |
| [Leer Nombre](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/LeerNombre.java)         | Muestra el nombre pasado como argumento y sale con código 1                                                      |
| [Un Saludo](https://github.com/i12vecaj/psp-25-26/blob/main/UA1/ejemplos/java/src/com/ceslopedevega/procesos/Unsaludo.java)             | Imprime un saludo recibido como argumento cinco veces                                                            |
