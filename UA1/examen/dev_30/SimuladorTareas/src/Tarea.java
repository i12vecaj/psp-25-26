 /*
  * Nombre: [Miguel Ángel varo rabadán]
  * Fecha: [29/10/2025]
  * Descripción: [Programa que crea y ejecuta tres hilos que simulan tareas concurrentes]
  */

 public class Tarea {
    public static void main(String[] args) {
        Thread tareaSimulada1 = new Thread(new Tarea1());
        tareaSimulada1.start();

        Thread tareaSimulada2 = new Thread(new Tarea2());
        tareaSimulada2.start();

        Thread tareaSimulada3 = new Thread(new Tarea3());
        tareaSimulada3.start();

    }
}

/*
f) En un comentario al final del código, explica con tus propias palabras:
Qué diferencia hay entre crear un proceso y crear un hilo:
    Un proceso es una instancia de un programa en ejecución que tiene su propio espacio de memoria utilizando los recursos del sistema,
    un hilo es una programa en ejecución dentro de un proceso que comparte el mismo espacio de memoria y recursos con otros hilos del mismo proceso.
    Los hilos se ejecutan dentro del mismo proceso independiéntes de entre unos y otros y son más eficientes a la hora de coger los recursos del sistema

Qué ventajas e inconvenientes tiene la programación concurrente:
    ventajas:
    - Mejora el rendimiento y la eficiencia del sistema al permitir la ejecución simultánea de múltiples tareas(hilos).
    - Permite una mejor utilización de los recursos del sistema.
    - Facilita la gestión de tareas que pueden ejecutarse de manera independiente.
    Inconvénientes:
    - Es compleja y difícil de ejecutar debido a la gestion, la sincronización y comunicación entre hilos.
    - Puede dar lugar a condiciones de competencia si no se manejan adecuadamente los recursos compartidos y llevar a los hilos que iban a coger un mismo recurso a bloquearse y no poder utilizar el recurso ninguno de los dos.
    - Utilizar demasiados hilos puede provocar una sobrecarga del sistema.
*/