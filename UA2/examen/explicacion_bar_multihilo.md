# Explicación del Programa Multihilo del Bar

## 1. ua2ex1.java --- Programa principal

El programa simula un bar donde varios clientes piden vasos de cerveza,
los beben y los devuelven. Todo ocurre concurrentemente usando
**threads**, y el camarero es un recurso compartido que debe
sincronizarse.

------------------------------------------------------------------------

## 2. Clase VasoCerveza

Representa un vaso que puede ser:

-   Media pinta (0)\
-   Pinta completa (1)

Cada vaso tiene:

-   Un identificador único.\
-   Un tipo de vaso.

Se usan en las transacciones entre clientes y camarero.

------------------------------------------------------------------------

## 3. Clase Camarero

El camarero gestiona los vasos disponibles:

### Funciones principales

1.  **Constructor**
    -   Crea 3 vasos aleatorios.
    -   Guarda todos en una lista compartida.
2.  **servirCerveza()**
    -   Si hay vasos, entrega uno aleatoriamente.
    -   Si no hay vasos, el cliente espera con `wait()`.
3.  **devolverCerveza()**
    -   Añade el vaso a la lista.
    -   Llama a `notifyAll()` para despertar clientes en espera.
4.  **contarVasos()**
    -   Muestra el estado actual de la lista.

------------------------------------------------------------------------

## 4. Clase HilosClientes (extends Thread)

Cada cliente:

1.  Pide un vaso al camarero.
2.  Bebe durante un tiempo simulado.
3.  Acumula los litros consumidos.
4.  Devuelve el vaso.
5.  Espera entre 250 y 1000 ms antes de repetir.

El método `run()` ejecuta todo este ciclo en bucle.

------------------------------------------------------------------------

## 5. Método main()

1.  Crea un camarero llamado *Mou*.
2.  Crea a los clientes:
    -   Homer\
    -   Barney\
    -   Carl\
    -   Lenny\
    -   Lurleen\
3.  Los arranca como hilos concurrentes.
4.  Simula durante un período de tiempo.
5.  Interrumpe y finaliza los hilos.

------------------------------------------------------------------------

## 6. Concurrencia y sincronización

-   La lista de vasos es un recurso compartido.
-   Se usa `synchronized`, `wait()` y `notifyAll()`.
-   Evita condiciones de carrera y asegura que no haya accesos
    simultáneos peligrosos.

------------------------------------------------------------------------

## 7. Comportamiento esperado

-   Los clientes piden y devuelven vasos sin bloquearse indefinidamente.
-   El camarero coordina la entrega de vasos.
-   Al finalizar, cada cliente muestra cuántos litros ha consumido.

------------------------------------------------------------------------

Daniel Ronda
