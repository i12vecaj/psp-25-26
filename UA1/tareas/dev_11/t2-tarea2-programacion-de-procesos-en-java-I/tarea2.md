<div align="center">

# T2 – Tarea 2 – Programación de procesos en Java (I)

</div>

#### _Criterios a), e), f), g), h)_

**PROCESOS**. Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

- FR1: lea una cadena de caracteres desde la entrada estándar hasta recibir un carácter de terminación, en concreto, un asterisco \* - 2 puntos
- FR2: una vez recibido el caracter de terminación, muestre por pantalla toda la información leída - 2 puntos
- FR3: Crea después otro programa que ejecute el anterior - 2 puntos
- Implementa el control de errores - 2 puntos
- Documenta el código - 2 puntos

## 1. Implementación de `LectorTexto.java`

En esta clase principal se realiza la lógica para leer desde consola caracter a caracter hasta detectar el asterisco `*`. Una vez recibido, el programa muestra el texto introducido.

He separado las responsabilidades en funciones distintas para mantener el código limpio y modular:

- `leerHastaElAsterisco()`. Lectura de entrada.
- `mostrarTextoDelUsuario()`. Muestra el resultado final.

<div align="center">

<img src="https://i.imgur.com/IUItJBL.png" alt="Captura del código de LectorTexto.java"  />

</div>

## 2. Implementación de `EjecutaLectorTexto.java`

Este programa ejecuta la clase anterior (`LectorTexto`) como un proceso independiente utilizando `ProcessBuilder`.

Para hacerlo correctamente, he usado:

- `-cp` para indicar el classpath actual.
- `inheritIO()` para que el proceso hijo **use el mismo teclado y pantalla** que el padre.

Esto es necesario para qpoder escribir texto desde consola al proceso hijo, algo que no me funcionaba al principio.

<div align="center">

<img src="https://i.imgur.com/Xqyt79d.png" alt="Captura del código de EjecutaLectorTexto.java"  />

</div>

## ⚠️ Problemas que he encontrado durante el desarrollo

Durante la implementación de esta tarea, aparecieron varios errores que se han resuelto con prueba y error, y búsqueda de información técnica:

### ❌ Error 1: `ClassNotFoundException: LectorTexto`

Al principio ejecuté el proceso así:

```sh
new ProcessBuilder("java", "LectorTexto.java");
```

Esto me dio error porque el comando esperaba el nombre de la clase compilada, no el archivo fuente. Lo corregí pasando el nombre correcto de la clase junto con el classpath:

```sh
new ProcessBuilder("java", "-cp", classpath, "LectorTexto");
```

### ❌ Error 2: El proceso no permitía escribir desde teclado

En un principio no podía interactuar con el programa `LectorTexto` desde consola. Esto se debía a que el proceso hijo no hereda la entrada estándar del proceso padre por defecto.

Para solucionarlo, añadí esta línea clave:

```sh
pb.inheritIO();
```

Esto permite que el proceso hijo (`LectorTexto`) use el mismo teclado y pantalla que `EjecutaLectorTexto`.

## Conclusión

Con esta tarea he aprendido a:

- Usar `ProcessBuilder` para lanzar procesos externos.
- Redirigir correctamente la entrada/salida del proceso.
- Controlar errores típicos como `ClassNotFoundException`.
- Separar la lógica en métodos reutilizables para un código más limpio.
