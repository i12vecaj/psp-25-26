# UA1 · Tarea 3 · Procesos en Java

Este ejercicio incluye dos programas:

- `ProgramaPrincipal`: evalúa el argumento recibido y finaliza con un código de salida según las reglas.
- `LanzadorPrograma`: ejecuta `ProgramaPrincipal` como proceso hijo y muestra el significado del código devuelto.

## Reglas de salida (ProgramaPrincipal)

- 1 → no se pasó ningún argumento
- 2 → el primer argumento no es un entero válido
- 3 → el primer argumento es un entero negativo
- 0 → cualquier otro caso (entero 0 o positivo)

Nota: solo se evalúa el primer argumento. Si ocurre un error no previsto, podría salir con `99`.

## Compilación

```bash
javac ProgramaPrincipal.java LanzadorPrograma.java
```

## Ejecución

```bash
# Programa principal
java ProgramaPrincipal
java ProgramaPrincipal hola
java ProgramaPrincipal -5
java ProgramaPrincipal 0

# Lanzador (reenvía argumentos y explica el resultado)
java LanzadorPrograma
java LanzadorPrograma hola
java LanzadorPrograma -10
java LanzadorPrograma 42
```
