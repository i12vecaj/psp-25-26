public class ua2tarea1fr1 implements Runnable {
    int contador = 0;

    /* En el metodo run ponemos que el contador se incremnte 1000 por cada hilo que se lenze */
    public void run() {
        try {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        } catch (Exception e) {
            System.err.println("Error en la ejecución del hilo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        /* lanzamos los hilos */
        try {
            ua2tarea1fr1 tarea = new ua2tarea1fr1();

            Thread t1 = new Thread(tarea);
            Thread t2 = new Thread(tarea);
            Thread t3 = new Thread(tarea);
            Thread t4 = new Thread(tarea);
            Thread t5 = new Thread(tarea);

            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();

            // Esperar a que terminen los hilos
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

            System.out.println("Valor final del contador: " + tarea.contador);

        } catch (Exception e) {
            System.err.println("Error general en el programa: " + e.getMessage());
        }
    }
}

/* El resultado no es el que esperaba porque ya que lanzamos 5 hilos y tenemos que sumar 1000 por cada que se lanza un hilo 
y el reusltado final es aleatorio y puede dar 5000 o 0 o 1000 */
