package StrangerThings;

public class Iniciador {

    private static final int TIEMPO_SIMULACION_MS = 5000; // 5 segundos de simulación


    public static void main(String[] args) {

        Eleven eleven = new Eleven("Eleven");

        HilosDemogorgon profesor = new HilosDemogorgon("Profesor", eleven);

        profesor.start();

        try {
            System.out.println("\nLa simulación correrá durante " + TIEMPO_SIMULACION_MS / 1000 + " segundos");
            Thread.sleep(TIEMPO_SIMULACION_MS);
            System.out.println("\nTiempo de simulación terminado");
            profesor.interrupt();
            profesor.join();

        } catch (InterruptedException e) {
            System.err.println("Error: El hilo principal fue interrumpido:" + e.getMessage());
            Thread.currentThread().interrupt();
        }


        System.out.println("=== FIN DE LA SIMULACIÓN DEL BAR ===");

    }
}