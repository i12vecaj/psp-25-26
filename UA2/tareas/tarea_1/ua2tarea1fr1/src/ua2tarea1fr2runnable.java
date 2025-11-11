/*
@Author Bernardo Cubero
Fecha 09/11/2025
Version 1.0
 */
public class ua2tarea1fr2runnable {

    static int contador = 0;


    public static synchronized void cont() {
        contador++;
    }

    public static void main(String[] args) {

        Runnable tarea = new Hilos();

        Thread th1 = new Thread(tarea);
        Thread th2 = new Thread(tarea);
        Thread th3 = new Thread(tarea);
        Thread th4 = new Thread(tarea);
        Thread th5 = new Thread(tarea);

        th1.start();
        th2.start();
        th3.start();
        th4.start();
        th5.start();

        // esperar a que terminen antes de imprimir
        try {
            th1.join();
            th2.join();
            th3.join();
            th4.join();
            th5.join();
        } catch (InterruptedException e) {
            System.out.println( e.getMessage());
        }

        System.out.println("Valor  " + contador);
    }

    public static class Hilos implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    cont();  //Llamamos a la funcion contador para que vaya sumando
                }
            } catch (Exception e) {
                System.out.println("Error en tarea Runnable");
            }
            // En Runnable no existe getId(); usamos el hilo actual:
            long id = Thread.currentThread().getId();
            System.out.println("Hilo: " + id + " Contador parcial: " + contador);
        }
    }
}
