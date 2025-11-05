public class ua2tarea1fr1 {

    static int contador = 0;

    public static void main(String[] args) {

        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    contador++; 
                }
            });
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido: " + e.getMessage());
            }
        }

        System.out.println("Valor final (sin sincronización): " + contador);
        System.out.println("Debería ser 5000, pero puede cambiar");
    }
}
