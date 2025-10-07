package tasks;

public class TaskLento {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("[TaskLento] Iniciando simulación de trabajo (sleep)...");
        long start = System.currentTimeMillis();
        Thread.sleep(2500);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[TaskLento] Finalizado en " + elapsed + " ms.");
    }
}