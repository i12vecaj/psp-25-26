public class ua2tarea1fr2runnable {

    private static int contador = 0;

    public synchronized static void incrementar() {
        contador++;
        System.out.println("Contador: " + contador);
    }

    public static void main(String[] args) {
        Runnable tarea = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int j = 0; j < 1000; j++) {
                        incrementar();
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };

        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(tarea);
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