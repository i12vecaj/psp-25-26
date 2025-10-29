/*
 * Nombre: [David Alberto Cruz Barranco]
 * Fecha: [29/10/2025]
 * Descripción: Simulación de una casa inteligente donde se realizan tres tareas (lavar ropa, cocinar y limpiar)
 *               de forma concurrente utilizando hilos en Java.
 * FR implementados:
 *   FR1: Creación de tres clases que implementan Runnable (LavarRopa, Cocinar, Limpiar).
 *   FR2: Mostrar mensajes al inicio y fin de cada tarea.
 *   FR3: Simulación de diferentes duraciones con Thread.sleep().
 *   FR4: Creación y ejecución concurrente de los tres hilos desde el main, con mensajes de inicio y fin.
 *   FR5: Comprobación de orden variable en la ejecución debido a la concurrencia.
 */

public class CasaInteligente {

    // FR1: Clase para lavar la ropa
    static class LavarRopa implements Runnable {
        @Override
        public void run() {
            System.out.println("[Lavar ropa] Comenzando tarea...");
            try {
                // FR3: Simular duración de 2 segundo para que pueda haber varianza
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Se ha prodcido un error al dormir por 2segs"+e.getMessage());
            }
            System.out.println("[Lavar ropa] Tarea finalizada.");
        }
    }

    // FR1: Clase para cocinar
    static class Cocinar implements Runnable {
        @Override
        public void run() {
            System.out.println("[Cocinar] Comenzando tarea...");
            try {
                // FR3: Simular duración de 2 segundos
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Se ha prodcido un error al dormir por 2segs"+e.getMessage());
            }
            System.out.println("[Cocinar] Tarea finalizada.");
        }
    }

    // FR1: Clase para limpiar
    static class Limpiar implements Runnable {
        @Override
        public void run() {
            System.out.println("[Limpiar] Comenzando tarea...");
            try {
                // FR3: Simular duración de 3 segundos
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Se ha prodcido un error al dormir por 3segs"+e.getMessage());
            }
            System.out.println("[Limpiar] Tarea finalizada.");
        }
    }

    public static void main(String[] args) {
        // FR4: Mensaje al inicio del programa
        System.out.println("=== Inicio del programa: Casa Inteligente ===");

        // Crear los hilos
        Thread hiloLavar = new Thread(new LavarRopa());
        Thread hiloCocinar = new Thread(new Cocinar());
        Thread hiloLimpiar = new Thread(new Limpiar());

        // Iniciar las tareas concurrentemente
        hiloLavar.start();
        hiloCocinar.start();
        hiloLimpiar.start();

        try {
            // Esperar a que todas las tareas terminen
            hiloLavar.join();
            hiloCocinar.join();
            hiloLimpiar.join();
        } catch (InterruptedException e) {
            System.err.println("Se ha producido un error esperando a las tareas"+e.getMessage());
        }

        // FR4: Mensaje al final del programa
        System.out.println("=== Fin del programa: Todas las tareas han terminado ===");

        // FR5: Debido a la ejecución concurrente, el orden de los mensajes puede variar en cada ejecución.
        /*
=== Inicio del programa: Casa Inteligente ===
[Lavar ropa] Comenzando tarea...
[Cocinar] Comenzando tarea...
[Limpiar] Comenzando tarea...
[Lavar ropa] Tarea finalizada.
[Cocinar] Tarea finalizada.
[Limpiar] Tarea finalizada.
=== Fin del programa: Todas las tareas han terminado ===


=== Inicio del programa: Casa Inteligente ===
[Cocinar] Comenzando tarea...
[Limpiar] Comenzando tarea...
[Lavar ropa] Comenzando tarea...
[Lavar ropa] Tarea finalizada.
[Cocinar] Tarea finalizada.
[Limpiar] Tarea finalizada.
=== Fin del programa: Todas las tareas han terminado ===

Si ponemos todas las tareas en escala 1s 2s y 3s no habria varianza a la hora de ver los datos, pero si alguno tiene el mismo valor vuelve a tener importancia la concurrencia.
*/
    }
}
