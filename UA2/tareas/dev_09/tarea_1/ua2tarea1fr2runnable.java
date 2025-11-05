// FR2 - Versión sincronizada usando Runnable
// Similar a la anterior, pero implementando la interfaz Runnable

public class ua2tarea1fr2runnable {
    public static int contador = 0;

    public static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // Creamos Runnable y lo pasamos a cada Thread
        for (int i = 0; i < 5; i++) {
            Runnable r = new HiloRunnable("Hilo-" + (i + 1));
            hilos[i] = new Thread(r);
            hilos[i].start();
        }

        // Esperamos la finalización de todos
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar hilo: " + e.getMessage());
            }
        }

        System.out.println("\nValor final del contador (Runnable sincronizado): " + contador);
        System.out.println("Resultado esperado: 5000 (correcto y estable)");
        System.out.println("Diferencia: misma lógica, pero Runnable separa la tarea del hilo que la ejecuta.");
    }
}

// Clase que implementa Runnable
class HiloRunnable implements Runnable {
    private String nombre;

    public HiloRunnable(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            ua2tarea1fr2runnable.incrementar();
        }
        System.out.println(nombre + " ha terminado.");
    }
}
