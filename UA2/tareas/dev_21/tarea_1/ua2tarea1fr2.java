package tarea_1;

public class ua2tarea1fr2 {

    private static int contador = 0;

    // Método sincronizado
    public synchronized static void incrementar() {
        contador++;
    }

    public static void main(String[] args) {

        Thread[] hilos = new Thread[5];

        try {
            for (int i = 0; i < hilos.length; i++) {
                hilos[i] = new Thread(() -> {
                    for (int j = 0; j < 1000; j++) {
                        incrementar();
                    }
                });
                hilos[i].start();
            }

            for (Thread t : hilos) {
                t.join();
            }

        } catch (InterruptedException e) {
            System.out.println("Error en ejecución: " + e.getMessage());
        }

        System.out.println("Valor final esperado = 5000");
        System.out.println("Valor real obtenido = " + contador);
    }
}
