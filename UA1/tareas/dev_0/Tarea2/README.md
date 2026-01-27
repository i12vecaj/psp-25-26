# Tarea 2 - Ejecutor de Programas en Java

## Descripción

Esta tarea consiste en crear un programa en Java (`Main.java`) que lea caracteres desde la entrada estándar hasta recibir un asterisco (`*`), y otro programa (`Ejecutor.java`) que ejecute `Main` como un proceso externo, mostrando su salida y permitiendo la interacción desde la consola.

---

## Archivos incluidos

- **Main.java**  
  Programa principal que:
    - Lee caracteres desde la entrada estándar.
    - Se detiene cuando se introduce un asterisco (`*`).
    - Controla errores como entradas vacías o nulas.
    - Imprime la cadena resultante al finalizar.

- **Ejecutor.java**  
  Programa que:
    - Lanza `Main` como un proceso externo usando `ProcessBuilder`.
    - Redirige la entrada y salida estándar del proceso para interactuar directamente con `Main`.
    - Muestra el código de salida de `Main` y controla posibles excepciones.
    - Compatible con IntelliJ y rutas absolutas al directorio de clases compiladas.

---

## Funcionamiento

1. **Compilar `Main.java` y `Ejecutor.java`**
    - En IntelliJ: Build → Build Project.
    - Asegurarse de que los `.class` estén en el directorio de salida (normalmente `out/production/Tarea2`).

2. **Ejecutar `Ejecutor`**
    - `Ejecutor` inicia `Main` como un proceso externo.
    - La consola mostrará las instrucciones de `Main`.
    - Se pueden introducir caracteres y finalizar con `*`.

3. **Salida esperada**
    - Mientras se introducen caracteres, `Main` no termina hasta que se escriba `*`.
    - Al finalizar, se imprime la cadena completa ingresada.
    - `Ejecutor` muestra un mensaje indicando si `Main` terminó correctamente o con error.

---

## Control de errores

- `Main.java`:
    - Maneja entradas vacías o nulas.
    - Captura excepciones inesperadas durante la lectura de la entrada.

- `Ejecutor.java`:
    - Captura excepciones de I/O y de interrupción de proceso.
    - Informa si `Main` terminó con código distinto de cero.

---

## Notas

- Es importante usar **el nombre de la clase sin `.java`** al ejecutar con `java`.
- Él `classpath` debe apuntar al directorio donde están los `.class` compilados.
- `inheritIO()` permite que la entrada y salida de `Main` se muestren directamente en la consola donde se ejecuta `Ejecutor`.

---

## Autor

Alejandro Córdoba

