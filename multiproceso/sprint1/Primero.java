package sprint1;

public class Primero {
    public static void main(String[] args) {
        // Simulamos una tarea que tarda 3 segundos
        System.out.println("Worker 1: Iniciando tarea...");

        try {
            Thread.sleep(3000); // Esperamos 3 segundos (3000 milisegundos)
        } catch (InterruptedException e) {
            System.out.println("Worker 1: Error al dormir el hilo.");
        }

        System.out.println("Worker 1: Tarea finalizada.");
    }
}
