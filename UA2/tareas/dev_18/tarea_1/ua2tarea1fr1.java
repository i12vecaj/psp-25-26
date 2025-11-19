package tarea_1;
public class ua2tarea1fr1 {

    // Variable compartida
    private static int contador = 0;

    public static void main(String[] args) {

        Thread[] hilos = new Thread[5];

        try {
            // Lanzamos 5 hilos
            for (int i = 0; i < hilos.length; i++) {
                hilos[i] = new Thread(() -> {
                    for (int j = 0; j < 1000; j++) {
                        contador++;      // Sincronización inexistente
                    }
                });
                hilos[i].start();
            }

            // Esperar a que todos terminen con join()
            for (Thread t : hilos) {
                t.join();
            }

        } catch (InterruptedException e) {
            System.out.println("Error de ejecución: " + e.getMessage());
        }

        // Resultado final
        System.out.println("Valor final esperado = 5000");
        System.out.println("Valor real obtenido = " + contador);
    }
}
