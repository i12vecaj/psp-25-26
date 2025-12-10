package ejemplosproductorc;

class Consumidor extends Thread {
    private Buffer buffer;

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while(true) {
            int valor = buffer.consumir();
            System.out.println("Consumido: " + valor);
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}
