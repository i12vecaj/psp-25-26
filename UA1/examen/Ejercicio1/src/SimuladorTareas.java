/*
 * Nombre: Elena Expósito Lara
 * Fecha: 29/10/2025
 * Descripción: Clase interna que representa una tarea concurrente, implementa Runnable para poder ejecutarse dentro de un hilo
 * FR implementados:
    * a) Crea una clase interna llamada Tarea que implemente la interfaz Runnable.
    * Cada tarea debe tener un nombre (por ejemplo, "Tarea 1", "Tarea 2", "Tarea 3") que se reciba por parámetro en el constructor.
    * (1 punto)
    * b) En el método run() de cada tarea, muestra por pantalla un mensaje indicando que la tarea ha comenzado, el identificador del hilo actual y el nombre de la tarea.
    * Por ejemplo:[Tarea 1] ejecutándose en el hilo 13
    * (1,5 puntos)
    * c) Haz que cada tarea simule un trabajo real durmiendo entre 500 y 1500 milisegundos (usa Thread.sleep()) antes de finalizar.
    * Al terminar, debe mostrar: [Tarea 1] finalizada.
    * (1,5 puntos)
    * d) Desde el método main, crea y lanza tres hilos que ejecuten las tres tareas anteriores.
    * (2 puntos)
    * e) Asegúrate de que el hilo principal muestre su propio identificador antes y después de la ejecución de las tareas, y que espere a que todas terminen antes de finalizar.
    * (2 puntos)
    * f) En un comentario al final del código, explica con tus propias palabras:
    * Qué diferencia hay entre crear un proceso y crear un hilo.
    * Qué ventajas e inconvenientes tiene la programación concurrente.
    * (2 puntos)
 */

public class SimuladorTareas {

    public static class Tarea implements Runnable {
        private final String nombre;

        public Tarea(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            // Muestro el inicio de la tarea
            long idHilo = Thread.currentThread().getId();
            System.out.println("[" + nombre + "] ejecutándose en el hilo " + idHilo);

            // Simulo trabajo real (entre 500 y 1500 ms)
            try {
                int tiempo = (int) (Math.random() * 1000) + 500;
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                System.out.println("[" + nombre + "] interrumpida.");
            }

            // Muestro el fin de la tarea
            System.out.println("[" + nombre + "] finalizada.");
        }
    }
}


/*
f) EXPLICACIÓN FINAL
DIFERENCIA ENTRE CREAR UN PROCESO Y CREAR UN HILO:
Un proceso es un programa en ejecución con su propia memoria y recursos del sistema,
mientras que un hilo es una unidad de ejecución dentro de un proceso que comparte
esa misma memoria con otros hilos. Los procesos son independientes entre sí, pero
los hilos pueden comunicarse fácilmente al compartir datos dentro del mismo espacio.

VENTAJAS DE LA PROGRAMACIÓN CONCURRENTE:
- Permite que varias tareas progresen a la vez, mejorando la capacidad de respuesta.
- Aprovecha mejor los recursos del procesador y los núcleos disponibles.
- Hace posible realizar operaciones simultáneas sin bloquear la aplicación.

INCONVENIENTES:
- Aumenta la complejidad del código y la dificultad para depurar errores.
- Puede provocar problemas de sincronización o condiciones de carrera si
  varios hilos acceden a los mismos datos sin control.
- Un mal diseño concurrente puede causar bloqueos o pérdida de rendimiento.
*/
