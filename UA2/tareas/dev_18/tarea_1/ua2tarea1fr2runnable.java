package tarea_1;

public class ua2tarea1fr2runnable {

    private static int contador = 0;

    // Método sincronizado para evitar condiciones de carrera
    public synchronized static void incrementar() {
        contador++;
    }

    public static void main(String[] args) {

        Thread[] hilos = new Thread[5];

        Runnable tarea = () -> {
            for (int i = 0; i < 1000; i++) {
                incrementar();
            }
        };

        try {
            // Lanzamos los 5 hilos usando Runnable
            for (int i = 0; i < hilos.length; i++) {
                hilos[i] = new Thread(tarea);
                hilos[i].start();
            }

            for (Thread t : hilos) {
                t.join();
            }

        } catch (InterruptedException e) {
            System.out.println("Error de ejecución: " + e.getMessage());
        }

        System.out.println("Valor final esperado = 5000");
        System.out.println("Valor real obtenido = " + contador);
    }
}

