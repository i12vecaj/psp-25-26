 /*
  * Nombre: Jonathan Martínez Pullana
  * Fecha: 29/10/2025
  * Descripción: Casa inteligente
  * FR implementados: [FR1, FR2, FR3, FR4, FR5]
 */

public class CasaInteligente {

    public static class LavarRopa implements Runnable {

        private int tiempo;

        public LavarRopa(int tiempo) {
            this.tiempo = tiempo;
        }

        @Override
        public void run() {
            System.out.println("[LavarRopa] Comenzando...");
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[LavarRopa] terminado.");
        }
    }

    public static class Cocinar implements Runnable {

        private int tiempo;

        public Cocinar(int tiempo) {
            this.tiempo = tiempo;
        }

        @Override
        public void run() {
            System.out.println("[Cocinar] Comenzando...");
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[Cocinar] terminado.");
        }
    }

    public static class Limpiar implements Runnable {

        private int tiempo;

        public Limpiar(int tiempo) {
            this.tiempo = tiempo;
        }

        @Override
        public void run() {
            System.out.println("[Limpiar] Comenzando...");
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[Limpiar] terminado.");
        }
    }

    public static void main(String[] args) {
        System.out.println("====CasaInteligente3000====");
        System.out.println("Ejecutando tareas/hilos");
        System.out.println("---------------------------------------------------------");
        Thread Tarea1 = new Thread(new LavarRopa(2500));
        Thread Tarea2 = new Thread(new Cocinar(1000));
        Thread Tarea3 = new Thread(new Limpiar(4000));

        Tarea1.start();
        Tarea2.start();
        Tarea3.start();

        // Nos aseguramos de que las tareas terminen antes de terminar el programa.
        try {
            Tarea1.join();
            Tarea2.join();
            Tarea3.join();
        } catch (InterruptedException e) {
            System.err.println("Hilo principal interrumpido: " + e.getMessage());
        }

        System.out.println("---------------------------------------------------------");
        System.out.println("Todas las tareas han finalizado.");
        System.out.println("====CasaInteligente3000====");
    }
}

/* FR5
Efectivamente los mensajes varían.

1ª Ejecución:
====CasaInteligente3000====
Ejecutando tareas/hilos
---------------------------------------------------------
[LavarRopa] Comenzando...
[Cocinar] Comenzando...
[Limpiar] Comenzando...
[Cocinar] terminado.
[LavarRopa] terminado.
[Limpiar] terminado.
---------------------------------------------------------
Todas las tareas han finalizado.
====CasaInteligente3000====


-----------------------------------------------------------------------------------


2ª Ejecución:
====CasaInteligente3000====
Ejecutando tareas/hilos
---------------------------------------------------------
[Cocinar] Comenzando...
[LavarRopa] Comenzando...
[Limpiar] Comenzando...
[Cocinar] terminado.
[LavarRopa] terminado.
[Limpiar] terminado.
---------------------------------------------------------
Todas las tareas han finalizado.
====CasaInteligente3000====
 */