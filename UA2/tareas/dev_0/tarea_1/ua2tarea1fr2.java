package UA2.tareas.dev_0.tarea_1;

public class ua2tarea1fr2 {
    private int contador = 0;

    // Método sincronizado para evitar condiciones de carrera
    public synchronized void incrementar() {
        contador++;
    }

    // Clase interna que hereda de Thread
    static class HiloThread extends Thread {
        private ua2tarea1fr2 tarea;

        public HiloThread(ua2tarea1fr2 tarea) {
            this.tarea = tarea;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    tarea.incrementar();
                }
            } catch (Exception e) {
                System.err.println("Error en hilo (Thread): " + e.getMessage());
            }
        }
    }

    // Clase interna que implementa Runnable
    static class HiloRunnable implements Runnable {
        private ua2tarea1fr2 tarea;

        public HiloRunnable(ua2tarea1fr2 tarea) {
            this.tarea = tarea;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    tarea.incrementar();
                }
            } catch (Exception e) {
                System.err.println("Error en hilo (Runnable): " + e.getMessage());
            }
        }
    }

    // Método principal
    public static void main(String[] args) {
        try {
            ua2tarea1fr2 tareaCompartida = new ua2tarea1fr2();

            // Primera mitad de hilos usando Thread
            HiloThread t1 = new HiloThread(tareaCompartida);
            HiloThread t2 = new HiloThread(tareaCompartida);
            HiloThread t3 = new HiloThread(tareaCompartida);

            // Segunda mitad de hilos usando Runnable
            Thread t4 = new Thread(new HiloRunnable(tareaCompartida));
            Thread t5 = new Thread(new HiloRunnable(tareaCompartida));

            // Iniciar todos los hilos
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();

            // Esperar a que terminen
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
                t5.join();
            } catch (InterruptedException e) {
                System.err.println("Un hilo fue interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            // Mostrar resultado final
            System.out.println("Valor final del contador: " + tareaCompartida.contador);

        } catch (Exception e) {
            System.err.println("Error general en el programa: " + e.getMessage());
        }
    }
}

/* El resultado que esperaba ahora si es el correcto ya que da simepre 5000 */
