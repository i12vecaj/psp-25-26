public class Worker3 {
    public static void main(String[] args) {
        System.out.println("Worker 3: Iniciando tarea...");
        try {
            Thread.sleep(1000); // 1 segundo
        } catch (InterruptedException e) {
            System.out.println("Worker 3: Error al dormir el hilo.");
        }
        System.out.println("Worker 3: Tarea finalizada.");
    }
}