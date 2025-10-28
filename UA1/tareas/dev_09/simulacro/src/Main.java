public class Main {
    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN DE MONITOREO DE HUERTO ===\n");

        // Creamos diferentes sensores con diferentes lecturas
        sensores s1 = new sensores("Sensor 1", "temperatura");
        sensores s2 = new sensores("Sensor 2", "humedad");
        sensores s3 = new sensores("Sensor 3", "estado");
        sensores s4 = new sensores("Sensor 4", "temperatura", "humedad");
        sensores s5 = new sensores("Sensor 5", "estado", "temperatura");
        sensores s6 = new sensores("Sensor 6", "estado", "humedad");
        sensores s7 = new sensores("Sensor 7", "temperatura", "humedad", "estado");

        // Creamos hilos
        Thread[] hilos = {
                new Thread(s1), new Thread(s2), new Thread(s3), new Thread(s4), new Thread(s5), new Thread(s6), new Thread(s7)
        };

        // Iniciamos los hilos
        for (Thread hilo : hilos) hilo.start();

        // Esperamos a que finalicen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("El proceso principal fue interrumpido.");
            }
        }
        //Finalizamos
        System.out.println("\n=== SIMULACIÓN FINALIZADA ===");
    }
}
