# T3 - Tarea 3 - Programación de procesos en Java (II)

## Enunciado

### PROCESOS. Crea un programa en Java que admita argumentos desde main() y devuelva con System.exit() los siguientes valores:

- Si el número de argumentos es < 1 debe devolver 1
- Si el argumento es una cadena debe devolver 2
- Si el argumento es un número entero menor que 0 debe devolver 3
- En cualquier otro caso debe devolver 0
- Realiza un segundo programa Java que ejecute al anterior. Este programa deberá mostrar por pantalla lo que sucede según el valor devuelto al ejecutar el programa principal.

Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

FR1: admita argumentos desde main() - 1 punto
FR2: devuelva con System.exit() los siguientes valores:
Si el número de argumentos es < 1 debe devolver 1 - 1 punto
Si el argumento es una cadena debe devolver - 1 punto
Si el argumento es un número entero menor que 0 debe devolver 3 - 1 punto
En cualquier otro caso debe devolver 0 - 1 punto
FR3: Crea después otro programa que ejecute el anterior - 1 punto
Implementa el control de errores - 2 puntos
Documenta el código - 2 puntos


## Estructura del proyecto
src/
└── TareaTres/
├── MainProgram.java
└── RunPrograma.java

Ambos archivos pertenecen al package `TareaTres`

## 1. MainProgram.java

`MainProgram` valida el primer argumento recibido desde `main()` y devuelve un código de salida mediante `System.exit(codigo)`:

- **Código 1**: No se proporcionó ningún argumento (`args.length < 1`)
- **Código 2**: El argumento no es un número entero (`NumberFormatException`)
- **Código 3**: El número proporcionado es negativo (`num < 0`)
- **Código 0**: El argumento es correcto

### Lógica principal:

1. Se verifica si hay al menos un argumento.
2. Se intenta convertir el primer argumento a un número entero.
3. Se determina si es negativo o no.
4. Se devuelve el código correspondiente.

## 2. RunPrograma.java

Ejecuta `MainProgram` como proceso hijo usando `ProcessBuilder` y muestra un mensaje según el código de salida.


## Conclusión

- Ejecutar procesos en Java y pasar argumentos de forma controlada.
- Interpretar códigos de salida usando System.exit().
- Manejar errores comunes como ClassNotFoundException.
- Organizar el código de manera clara y reutilizable con ProcessBuilder.