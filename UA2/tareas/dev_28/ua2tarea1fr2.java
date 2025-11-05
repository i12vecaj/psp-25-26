public class ua2tarea1fr2 {

    static int contador = 0;
    static final Object lock = new Object(); // objeto para la sincronizacion

    public static void main(String[] args) throws InterruptedException {
        Thread hilo1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (lock) {
                    contador++;
                }
            }
        });

        Thread hilo2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (lock) {
                    contador++;
                }
            }
        });

        Thread hilo3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (lock) {
                    contador++;
                }
            }
        });

        Thread hilo4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (lock) {
                    contador++;
                }
            }
        });

        Thread hilo5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (lock) {
                    contador++;
                }
            }
        });

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        // Esperar a que terminen
        hilo1.join();
        hilo2.join();
        hilo3.join();
        hilo4.join();
        hilo5.join();

        System.out.println("Valor final del contador : " + contador);
    }
}
