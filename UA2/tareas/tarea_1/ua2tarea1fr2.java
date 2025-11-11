/*
@Author Bernardo Cubero
Fecha 08/11/2025
Version 1.0

FR2: Modifica el programa anterior para sincronizar el acceso a dicha varaible.
Lanza primero los hilos mediante la clase Thread
y después mediante la interfaz Runnable. Comprueba los resultados e indica las variaciones - 3 puntos
 */


public class ua2tarea1fr2 {


    static int contador = 0;

    public static synchronized void cont() {
        contador++;
    }

    public static void main(String[] args) {

        Thread th1 = new ua2tarea1fr1.hilo();
        Thread th2 = new ua2tarea1fr1.hilo();
        Thread th3 = new ua2tarea1fr1.hilo();
        Thread th4 = new ua2tarea1fr1.hilo();
        Thread th5 = new ua2tarea1fr1.hilo();

        //Hilos
        th1.start();
        th2.start();
        th3.start();
        th4.start();
        th5.start();


    }

    public static class hilo extends Thread {

        public void run() {
            // Incremento 1000 veces (una a una)

            try {
                for (int i = 0; i < 1000; i++) {
                    cont();
                }
            } catch (Exception e) {
                System.out.println("Error: " + getId());
            }

            System.out.println("Hilo: " + getId() + " Contador: " + contador);
        }

    }

}

