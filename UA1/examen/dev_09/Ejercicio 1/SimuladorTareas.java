/*
 * Nombre: [David Alberto Cruz Barranco]
 * Fecha: [29/10/2025]
 * Descripción: [Ejercicio 1: Programa con el cual creamos tareas ayudandonos de una clase interna para dar formato a esta,
 * lanzamos las mismas y simulamos trabajo mientras "duermen"]
 * FR implementados:
 */

/*
* He ido comentando los enunciados del ejercicio para un mayor orden, algunos ejercicios los he "duplicado" para distinguir las partes dentro del punto, como en el apartado c, d y e.
* */

public class SimuladorTareas {

    // a) Clase interna que implementa Runnable
    static class Tarea implements Runnable {
        private String nombre;

        public Tarea(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            // b) Mensaje indicando que la tarea ha comenzado
            System.out.println("[" + nombre + "] ejecutándose en el hilo " + Thread.currentThread().getId());

            // c) Simula trabajo durmiendo entre 500 y 1500 ms
            try {
                int tiempo = (int) (Math.random() * 1000) + 500;
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                System.err.println("Se ha producido un error mientras de simulaba el trabajo" + e.getMessage());
            }

            // c) Mensaje al finalizar
            System.out.println("[" + nombre + "] finalizada.");
        }
    }

    public static void main(String[] args) {
        // e) Mostrar identificador del hilo principal antes
        System.out.println("Hilo principal iniciando. ID: " + Thread.currentThread().getId());

        // d) Creamos tres hilos con sus tareas
        Thread t1 = new Thread(new Tarea("Tarea 1"));
        Thread t2 = new Thread(new Tarea("Tarea 2"));
        Thread t3 = new Thread(new Tarea("Tarea 3"));

        //d) Lanzamos los hilos
        t1.start();
        t2.start();
        t3.start();

        try {
            // e) Esperar a que todas las tareas terminen
            t1.join();
            t2.join();
            t3.join();

        } catch (InterruptedException e) {
            System.err.println("Se produjo un eerror mientras se finalizaban las tareas"+e.getMessage());
        }

        // e) Mostrar identificador del hilo principal después
        System.out.println("Hilo principal finalizando. ID: " + Thread.currentThread().getId());
    }
}

/*
f) Explicación:

- Diferencia entre proceso y hilo:
  Un proceso es una instancia independiente de un programa listo para ejecutar, con su propia memoria y recursos.
  Un hilo, seria "un administrador del proceso", seria la unidad de ejecución dentro de un proceso; varios hilos pueden compartir la misma memoria y recursos del proceso.

- Ventajas de la programación concurrente:
  Permite aprovechar mejor los procesadores multinúcleo, realizando varias tareas al mismo tiempo y mejorar el rendimiento de programas que hacen operaciones de espera.
  Mejorando la velocidad y eficacia del programa.

- Inconvenientes:
  Puede ser más difícil de depurar, mantener y predecir el comportamiento del programa debido a posibles condiciones de competencia, bloqueos o errores de sincronización.
  Tambien puede suponer la division de los recursos de manera ineficaz por lo que nos perjudicaria en los datos y resultados que podriamos obtener, tanto en tiempo como en veracidad.
*/
