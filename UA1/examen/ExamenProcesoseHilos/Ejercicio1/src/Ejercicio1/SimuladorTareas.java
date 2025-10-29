package Ejercicio1;

public class SimuladorTareas {
    /*

 * Nombre: Gregorio Ruiz López

 * Fecha: 29/10/2025

 * Descripción: a) Crea una clase interna llamada Tarea que implemente la interfaz Runnable.

Cada tarea debe tener un nombre (por ejemplo, "Tarea 1", "Tarea 2", "Tarea 3") que se reciba por parámetro en el constructor.

(1 punto)

b) En el método run() de cada tarea, muestra por pantalla un mensaje indicando que la tarea ha comenzado, el identificador del hilo actual y el nombre de la tarea.

Por ejemplo:[Tarea 1] ejecutándose en el hilo 13

(1,5 puntos)

c) Haz que cada tarea simule un trabajo real durmiendo entre 500 y 1500 milisegundos (usa Thread.sleep()) antes de finalizar.

Al terminar, debe mostrar: [Tarea 1] finalizada.

(1,5 puntos)

d) Desde el método main, crea y lanza tres hilos que ejecuten las tres tareas anteriores.

(2 puntos)

e) Asegúrate de que el hilo principal muestre su propio identificador antes y después de la ejecución de las tareas, y que espere a que todas terminen antes de finalizar.

(2 puntos)

f) En un comentario al final del código, explica con tus propias palabras:

Qué diferencia hay entre crear un proceso y crear un hilo.

Qué ventajas e inconvenientes tiene la programación concurrente.

(2 puntos)

 * FR implementados: [FR1, FR2...]

 */


 public static void main(String[] args) {
    

    Tarea tarea1 = new Tarea("Tarea1");
    Tarea tarea2 = new Tarea("Tarea2");
    Tarea tarea3 = new Tarea("Tarea3");

    Thread hilo1 = new Thread(tarea1);
    Thread hilo2 = new Thread(tarea2);
    Thread hilo3 = new Thread(tarea3);

    hilo1.start();
    hilo2.start();
    hilo3.start();
    
/*Qué diferencia hay entre crear un proceso y crear un hilo.

 * Un proceso es la instancia de ejecución de un programa, por lo que tiene asociado a si mismo unos
 * recursos del sistema para sí mismo. Un hilo es la subunidad del proceso, que tiene como caracteristica que 
 * comparte los recursos únicamente del programa al que pertenece. La diferencia entre crear un hilo y un proceso
 * radica en como queramos aprovechar los recursos del sistema, un proceso es independiente de otro,
 * y un hilo puede ser concurrente con otros hilos de dentro de su mismo proceso
 * 
 * 
 * Qué ventajas e inconvenientes tiene la programación concurrente.
 * 
 * la desventaja crucial es que al momento de ejecucion del programa concurrente, si el proceso falla
 * todo el programa se dejará de ejecutar.
 * 
 * La ventaja que ofrece la programación concurrente es el aprovechamiento pleno de los recursos del sistema
 */
    
 }
}
