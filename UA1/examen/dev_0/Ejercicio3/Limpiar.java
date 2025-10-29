public class Limpiar implements Runnable{
    @Override
    public void run() {
        System.out.println("[Limpiar] Iniciando tarea.");
        try {
            Thread.sleep(3000);
            System.out.println("[Limpiar] Finalizando tarea.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
