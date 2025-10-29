public class Cocinar implements Runnable{
    @Override
    public void run() {
        System.out.println("[Cocinar] Iniciando tarea.");
        try {
            Thread.sleep(1000);
            System.out.println("[Cocinar] Finalizando tarea.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
