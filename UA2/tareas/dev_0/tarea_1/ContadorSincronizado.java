import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContadorSincronizado {

    private static int contador = 0;

    private static final Object lock = new Object();

    //sincronizacion de manera mucho más avanzada
    private static final Lock reentrantLock = new ReentrantLock();

    static class HiloContador extends Thread {
        private final String nombre;
        private final boolean usarLock;


        public HiloContador(String nombre, boolean usarLock) {
            this.nombre = nombre;
            this.usarLock = usarLock;
        }

        @Override
        public void run() {
            try {
                System.out.println(nombre + "iniciando...");

                for (int i = 0; i < 1000; i++) {
                    if (usarLock) {
                        incrementarConLock();
                    } else {
                        incrementarConSynchronized();
                    }
                }

                System.out.println(nombre + " (Thread) finalizado. Contador parcial: " + contador);

            } catch (Exception e) {
                System.err.println("Error en " + nombre + ": " + e.getMessage());
            }

        }
    }

    static class HiloContadorRunnable implements Runnable {
        private final String nombreHilo;
        private final boolean usarLock;

        public HiloContadorRunnable(String nombreHilo, boolean usarLock) {
            this.nombreHilo = nombreHilo;
            this.usarLock = usarLock;
        }

        @Override
        public void run() {
            try {
                System.out.println(nombreHilo + " (Runnable) iniciando...");

                for (int i = 0; i < 1000; i++) {
                    if (usarLock) {
                        incrementarConLock();
                    } else {
                        incrementarConSynchronized();
                    }
                }

                System.out.println(nombreHilo + " (Runnable) finalizado. Contador parcial: " + contador);

            } catch (Exception e) {
                System.err.println("Error en " + nombreHilo + ": " + e.getMessage());
            }
        }
    }

    public static void incrementarConSynchronized(){
        synchronized(lock){
            contador++;
        }
    }

    private static void incrementarConLock(){
        reentrantLock.lock();
        try{
            contador++;
        } finally {
            reentrantLock.unlock();
        }
    }

    private static void reiniciarContador(){
        contador = 0;
    }


    static void main() {
        System.out.println(" === PROGRAMA CON SINCRONIZACIÓN ===");

        System.out.println("\n--- Prueba 1: Usando Thread con synchronized ---");
        pruebaConThread(false);

        reiniciarContador();

        System.out.println("\n--- Prueba 2: Usando Thread con Lock ---");
        pruebaConThread(true);

        reiniciarContador();

        System.out.println("\n--- Prueba 3: Usando Runnable con synchronized ---");
        pruebaConRunnable(false);

        reiniciarContador();

        System.out.println("\n--- Prueba 4: Usando Runnable con Lock ---");
        pruebaConRunnable(true);
    }

    private static void pruebaConThread(boolean usarLock) {
        System.out.println("Valor inicial del contador: " + contador);

        Thread[] hilos = new Thread[5];
        String mecanismo = usarLock ? "Lock" : "synchronized";

        try {
            // Crear e iniciar hilos
            for (int i = 0; i < 5; i++) {
                hilos[i] = new HiloContador("Hilo-T-" + (i + 1) + "-" + mecanismo, usarLock);
                hilos[i].start();
            }

            // Esperar a que todos terminen
            for (Thread hilo : hilos) {
                hilo.join();
            }

        } catch (InterruptedException e) {
            System.err.println("Error de interrupción: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        System.out.println("Valor final del contador: " + contador);
        System.out.println("Valor esperado: 5000");
        System.out.println("¿Resultado correcto? " + (contador == 5000));
    }

    private static void pruebaConRunnable(boolean usarLock) {
        System.out.println("Valor inicial del contador: " + contador);

        Thread[] hilos = new Thread[5];
        String mecanismo = usarLock ? "Lock" : "synchronized";

        try {

            for (int i = 0; i < 5; i++) {
                Runnable runnable = new HiloContadorRunnable("Hilo-R-" + (i + 1) + "-" + mecanismo, usarLock);
                hilos[i] = new Thread(runnable);
                hilos[i].start();
            }


            for (Thread hilo : hilos) {
                hilo.join();
            }

        } catch (InterruptedException e) {
            System.err.println("Error de interrupción: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        System.out.println("Valor final del contador: " + contador);
        System.out.println("Valor esperado: 5000");
        System.out.println("¿Resultado correcto? " + (contador == 5000));
    }


}


