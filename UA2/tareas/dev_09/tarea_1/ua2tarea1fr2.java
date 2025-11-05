// FR2 - Versión sincronizada con clase Thread
// Acceso controlado mediante método synchronized

public class ua2tarea1fr2 {
    public static int contador = 0;

    // Método sincronizado para incrementar el contador
    public static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // Creamos e iniciamos los hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new HiloSync("Hilo-" + (i + 1));
            hilos[i].start();
        }

        // Esperamos que terminen
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar hilo: " + e.getMessage());
            }
        }

        System.out.println("\nValor final del contador (sincronizado): " + contador);
        System.out.println("Resultado esperado: 5000 (sin pérdida de datos)");
    }
}

// Clase hilo que usa método sincronizado
class HiloSync extends Thread {
    public HiloSync(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            ua2tarea1fr2.incrementar();
        }
        System.out.println(getName() + " ha terminado.");
    }
}
