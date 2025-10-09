<div align="center">

# T3 – Tarea 3 – Programación de procesos en Java (II)

</div>

#### _Criterios a), e), f), g), h)_

**PROCESOS.** Crea un programa en Java que admita argumentos desde main() y devuelva con System.exit() los siguientes valores:

- Si el número de argumentos es < 1 debe devolver 1
- Si el argumento es una cadena debe devolver 2
- Si el argumento es un número entero menor que 0 debe devolver 3
- En cualquier otro caso debe devolver 0

Realiza un segundo programa Java que ejecute al anterior. Este programa deberá mostrar por pantalla lo que sucede según el valor devuelto al ejecutar el programa principal.

Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

- FR1: admita argumentos desde main() - 1 punto
- FR2: devuelva con System.exit() los siguientes valores:
  - Si el número de argumentos es < 1 debe devolver 1 - 1 punto
  - Si el argumento es una cadena debe devolver 2- 1 punto
  - Si el argumento es un número entero menor que 0 debe devolver 3 - 1 punto
  - En cualquier otro caso debe devolver 0 - 1 punto
- FR3: Crea después otro programa que ejecute el anterior - 1 punto
- Implementa el control de errores - 2 puntos
- Documenta el código - 2 puntos

## 1. Implementación de `ValidadorArgumentos.java`

Este programa se encarga de validar un argumento recibido por línea de comandos. Se devuelven distintos códigos de salida en función del tipo de error:

- Código `1`: No se ha proporcionado ningún argumento.
- Código `2`: El argumento no es un número entero.
- Código `3`: El número proporcionado es negativo.
- Código `0`: El argumento es correcto.

Se utiliza `System.exit(codigo)` para finalizar el proceso con el código correspondiente.

<div align="center">

<img src="https://i.imgur.com/ddUyDBb.png" alt="Captura del código de ValidadorArgumentos.java"  />

</div>

## 2. Implementación de `Lanzador.java`

Este programa ejecuta `ValidadorArgumentos` como proceso hijo mediante `ProcessBuilder`.

Para que el proceso funcione correctamente, es importante indicar el classpath actual al comando `java`, así como heredar la entrada/salida para poder mostrar los mensajes del proceso hijo.

He utilizado:

```java
String classpath = System.getProperty("java.class.path");

new ProcessBuilder("java", "-cp", classpath, "ValidadorArgumentos", argumento);
```

También se hereda la entrada/salida con:

```java
pb.inheritIO();
```

Esto permite que se vea la sallida del proceso hijo en consola directamente.

<img src="https://i.imgur.com/kgwKxCY.png" alt="Captura 1 del código de Lanzador.java"  />

<img src="https://i.imgur.com/mQ4D1kF.png" alt="Captura 2 del código de Lanzador.java"  />

## ⚠️ Problemas que he encontrado durante el desarrollo

### ❌ Error 1: `ClassNotFoundException: ValidadorArgumentos`

Inicialmente, el proceso hijo no encontraba la clase `ValidadorArgumentos`, lo que provocaba este error:

```ssh
Error: Could not find or load main class ValidadorArgumentos
Caused by: java.lang.ClassNotFoundException: ValidadorArgumentos
```

Lo solucioné añadiendo correctamente el classpath actual al crear el proceso:

```java
String classpath = System.getProperty("java.class.path");
```

### 💡 Detalle: Uso de `inheritIO()`

Otro detalle importante para el ejercicio fue heredar la entrada/salida estándar del proceso padre. Si no se hace esto, los mensajes del proceso hijo no se ven en consola.

Lo solucioné añadiendo:

```java
pb.inheritIO();
```

## Conclusión

Con esta tarea he aprendido a:

- Ejecutar procesos en Java de forma controlada.
- Pasar argumentos correctamente desde un proceso padre a uno hijo.
- Devolver e interpretar códigos de salida.
- Manejar errores frecuentes como `ClassNotFoundExceptionp`.
- Organizar el código en métodos reutilizables y claros.
