public class ua2tarea1fr2 {

    static int contador = 0;

    public static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {

        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    incrementar();
                }
            });
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error en hilo: " + e.getMessage());
            }
        }

        System.out.println("Valor final (con synchronized): " + contador);
        System.out.println("Resultado esperado: 5000");
    }
}
