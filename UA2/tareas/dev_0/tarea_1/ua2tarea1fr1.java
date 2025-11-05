
public class ua2tarea1fr1 {

    private static int contador = 0;

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        contador++;
                        System.out.println("Contador: " + contador);
                    }
                } catch (Exception e) {
                    System.out.println("Error en hilo: " + e.getMessage());
                }
            });
            hilos[i].start();
        }

        try {
            for (int i = 0; i < hilos.length; i++) {
                hilos[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Final: " + contador);
    }
}
