/*
 * Nombre: Maria Luisa Ortega Lucena
 * Fecha: 29/10/2025
 * Descripción: [Breve descripción del ejercicio]
 * FR implementados: a) Crea una clase interna llamada Tarea que implemente la interfaz Runnable.
Cada tarea debe tener un nombre (por ejemplo, "Tarea 1", "Tarea 2", "Tarea 3") que se reciba por parámetro en el constructor.
(1 punto)
b) En el metodo run() de cada tarea, muestra por pantalla un mensaje indicando que la tarea ha comenzado, el identificador del hilo actual y el nombre de la tarea.
Por ejemplo:[Tarea 1] ejecutándose en el hilo 13
(1,5 puntos)
c) Haz que cada tarea simule un trabajo real durmiendo entre 500 y 1500 milisegundos (usa Thread.sleep()) antes de finalizar.
Al terminar, debe mostrar: [Tarea 1] finalizada.
(1,5 puntos)
d) Desde el metodo main, crea y lanza tres hilos que ejecuten las tres tareas anteriores.
(2 puntos)
e) Asegúrate de que el hilo principal muestre su propio identificador antes y después de la ejecución de las tareas, y que espere a que todas terminen antes de finalizar.
(2 puntos)
f) En un comentario al final del código, explica con tus propias palabras:
Qué diferencia hay entre crear un proceso y crear un hilo.
Qué ventajas e inconvenientes tiene la programación concurrente.
(2 puntos)
 */
public class Main {
    public static void main(String[] args) {

        Thread hilo1 = new Thread(new Tarea("Tarea 1"));
        Thread hilo2 = new Thread(new Tarea("Tarea 2"));
        Thread hilo3 = new Thread(new Tarea("Tarea 3"));

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try{
            hilo1.join();
            hilo2.join();
            hilo3.join();
        }catch (InterruptedException e){
            System.out.println("Error");
            e.printStackTrace();
        }

    }
}

//f) En un comentario al final del código, explica con tus propias palabras:
//Qué diferencia hay entre crear un proceso y crear un hilo.
//Qué ventajas e inconvenientes tiene la programación concurrente.
//(2 puntos)

/*
Un proceso es un programa en ejecución y un hilo un proceso ligero. Los hilos son controlados por un controlador
y es el mismo controlador el que va diciendo que hilo entra primero o segundo, después los procesos tienen varios estados
que son el estado preparado, parado, terminado.

Ventaja principal que se puede tener varios procesos trabajando concurrentemente, puedes manejar que hilo entra antes
que otro, otro priorizar que un proceso se ejecute antes, o también puedes controlar el tiempo que se tarda en que
otro proceso se ejecute y eso mediendo los hilos

Una de sus desventajas es que un hilo dependa de que otro se ejecute antes, y a la hora de que quieras poder
ejecutar el hilo que depende dde otro, no puedas y tengas que esperar solo exclusivamente a que el hilo padre por
asi decirlo se tenga que ejecutar, otro inconveniente es que es más dificil a la hora de depurar,
 */

