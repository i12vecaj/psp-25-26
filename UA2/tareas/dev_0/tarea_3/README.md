# Explicación

## 1. App.java -- Programa principal

El programa compara el tiempo que tarda en *contar los caracteres* de
uno o varios ficheros usando:

1.  *Ejecución secuencial*
2.  *Ejecución concurrente* (un hilo por fichero)

El objetivo es observar si existe mejora en tiempos al usar múltiples
hilos.

------------------------------------------------------------------------

## 2. Método contarCaracteresSecuencial()

Este método recibe un array de nombres de fichero y los procesa **uno
detrás de otro**:

### Funcionamiento

1.  Mide el tiempo de inicio.
2.  Recorre cada fichero:
    -   Comprueba si existe y es accesible.
    -   Usa un FileReader para leer carácter por carácter.
    -   Cuenta los caracteres hasta llegar a -1.
    -   Muestra el resultado por consola.
3.  Devuelve el tiempo total empleado.

### Características

-   No usa concurrencia.
-   Cada fichero bloquea la ejecución hasta que se termina de procesar.
-   Es sencillo, pero no aprovecha múltiples núcleos.

------------------------------------------------------------------------

## 3. Clase interna HiloContador -- Extiende Thread

Cada objeto HiloContador representa una tarea independiente que cuenta
los caracteres de un fichero.

### En su método run():

1.  Comprueba si el fichero es accesible.

2.  Lee carácter por carácter igual que el método secuencial.

3.  Imprime:

        Concurrente - <nombre> : <nº caracteres>


------------------------------------------------------------------------

## 4. Método contarCaracteresConcurrente()

Este método crea y ejecuta un hilo por cada fichero del array.

### Pasos

1.  Mide el tiempo de inicio.
2.  Crea una lista de hilos.
3.  Por cada fichero:
    -   Construye un HiloContador.
    -   Lo añade a la lista.
    -   Llama a start() para iniciarlo.
4.  Tras arrancar todos los hilos, hace un *join()*:

 java
for (Thread h : hilos) {
    h.join();
}


Esto garantiza que el método no termina hasta que *todos* los hilos
han finalizado.

### Ventajas

-   Permite procesar varios archivos en paralelo.
-   Aprovecha mejor el hardware cuando hay varios núcleos.

------------------------------------------------------------------------

## 5. Método main()

1.  Define un fichero (el_quijote.txt).
2.  Llama a la versión secuencial y muestra su tiempo.
3.  Llama a la versión concurrente y muestra su tiempo.
4.  Compara la diferencia entre ambos.

Este flujo permite observar las ventajas (o no) de la ejecución
concurrente.

------------------------------------------------------------------------

# Comparación entre secuencial y concurrente

## Ejecución secuencial

-   Procesa cada fichero de uno en uno.
-   Tiempo total = suma de todos los tiempos individuales.
-   Sencillo pero no óptimo para múltiples ficheros grandes.

## Ejecución concurrente

-   Cada fichero se procesa en un hilo independiente.
-   El tiempo total suele ser el del fichero más grande.
-   Puede ser más rápido, pero depende de:
    -   Tamaño del archivo
    -   Número de núcleos del sistema
    -   Sobrecoste de crear hilos

------------------------------------------------------------------------

Daniel Ronda