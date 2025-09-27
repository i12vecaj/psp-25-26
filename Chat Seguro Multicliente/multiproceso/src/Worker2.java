public class Worker2 {
    public static void main(String[] args) {
        System.out.println("Worker 2: Iniciando tarea...");
        try {
            Thread.sleep(2000); // 2 segundos
        } catch (InterruptedException e) {
            System.out.println("Worker 2: Error al dormir el hilo.");
        }
        System.out.println("Worker 2: Tarea finalizada.");
    }
}