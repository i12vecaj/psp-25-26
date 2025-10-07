# Tarea 2 - Programación de procesos en Java (I)

Este directorio contiene la solución a la Tarea 2 (criterios a), e), f), g), h)).

## Programas

- **ReaderProgram.java**: (FR1, FR2) Lee desde la entrada estándar hasta encontrar `*` y luego muestra el texto leído y estadísticas.
- **LauncherProgram.java**: (FR3) Ejecuta `ReaderProgram` como proceso hijo usando `ProcessBuilder`.

## Compilación
```bash
javac ReaderProgram.java LauncherProgram.java
```

## Ejecución directa del lector
```bash
java ReaderProgram
```
Escribe tu texto y finaliza con `*`.

## Ejecución mediante el lanzador
```bash
java LauncherProgram
```
Interacciona igual; la E/S se hereda.

## Códigos de salida relevantes
- 0: Éxito
- 1: Error de E/S
- 2: Fin de entrada sin encontrar `*`
- 3: Restricción de seguridad
- 5/6/7: Errores relacionados con el lanzamiento / espera del proceso hijo
- 10: Error inesperado

## Mejoras posibles
- Parametrizar el carácter terminador.
- Añadir pruebas automatizadas (JUnit).
- Empaquetar en un JAR.
