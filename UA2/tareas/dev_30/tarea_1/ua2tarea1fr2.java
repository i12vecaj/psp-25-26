<<<<<<< HEAD
public class ua2tarea1fr2 {
    static int contador = 0;

    public static synchronized void incrementar() {// usando Synchronized se evita que los hilos se solapen
        contador++;
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // Crear y lanzar los hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new MiHilo("Hilo-" + (i + 1));
            hilos[i].start();
        }

        // con join esperamos a que terminen todos los hilos
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        // Resultado final
        System.out.println("Valor final del contador: " + contador);
        System.out.println("Valor esperado: 5000");
    }

    static class MiHilo extends Thread {
        public MiHilo(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                ua2tarea1fr2.incrementar(); // llamada sincronizada
            }
        }
    }
}
=======
public class ua2tarea1fr2 {
    static int contador = 0;

    public static synchronized void incrementar() {// usando Synchronized se evita que los hilos se solapen
        contador++;
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // Crear y lanzar los hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new MiHilo("Hilo-" + (i + 1));
            hilos[i].start();
        }

        // con join esperamos a que terminen todos los hilos
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        // Resultado final
        System.out.println("Valor final del contador: " + contador);
        System.out.println("Valor esperado: 5000");
    }

    static class MiHilo extends Thread {
        public MiHilo(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                ua2tarea1fr2.incrementar(); // llamada sincronizada
            }
        }
    }
}
>>>>>>> 9bb36804bd953f7be089a1850adc30824a6e43a1
