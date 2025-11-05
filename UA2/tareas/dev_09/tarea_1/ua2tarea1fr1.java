// FR1 - Programa con variable compartida sin sincronización
// Cada hilo incrementa el contador 1000 veces

public class ua2tarea1fr1 {
    public static int contador = 0; // variable compartida entre hilos

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // Creamos 5 hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new Hilo("Hilo-" + (i + 1));
            hilos[i].start();
        }

        // Esperamos a que terminen todos los hilos
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar hilo: " + e.getMessage());
            }
        }

        System.out.println("\nValor final del contador (sin sincronizar): " + contador);
        System.out.println("Resultado esperado: 5000");
        System.out.println("El valor final probablemente no sea el esperado debido a condiciones de carrera.");
    }
}

// Clase hilo que incrementa la variable compartida
class Hilo extends Thread {
    public Hilo(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            ua2tarea1fr1.contador++; // acceso no sincronizado
        }
        System.out.println(getName() + " ha terminado.");
    }
}
