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

public class Tarea implements Runnable{
    private String nombreTarea;

    public Tarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    @Override
    public void run(){
        //Me he dado cuenta de que a la hora de ejecutar el programa se repite la frase de "La tarea ha comenzado"
        // se repite 3 veces y no me ha dado tiempo cambiarlo
        System.out.println("La tarea ha comenzado");
        for(int i = 0; i<3; i++){
            System.out.println("Tarea: "+nombreTarea+" ejecutandose en el hilo: "+i);

            try{
                int espera = 500+(int) (Math.random()*1500);
                Thread.sleep(espera);
            }catch (InterruptedException e){
                System.out.println("Error en la ejecucion de la tarea: ");
                e.printStackTrace();
            }

            System.out.println("Finalizado: "+nombreTarea);

        }
    }
}
