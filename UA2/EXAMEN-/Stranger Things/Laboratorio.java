public class Laboratorio {
    
    private final Buffer buffer;
    private final int eventosAConsumir;

    public Laboratorio(Buffer buffer, int eventosAConsumir) {
        this.buffer = buffer;
        this.eventosAConsumir = eventosAConsumir;
    }

    public void iniciarConsumo() {
        Thread consumidorThread = new Thread(() -> {
            for (int i = 0; i < eventosAConsumir; i++) {
                try {
                    buffer.consumir();
                    Thread.sleep(300); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumidorThread.start();
    }
}
