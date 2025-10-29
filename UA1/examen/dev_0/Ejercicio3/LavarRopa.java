public class LavarRopa implements Runnable{

    @Override
    public void run() {
        System.out.println("[Lavar ropa] Iniciando tarea.");
        try {
            Thread.sleep(2000);
            System.out.println("[Lavar ropa] Finalizando tarea.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
