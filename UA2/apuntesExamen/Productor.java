package ejemplosproductorc;

class Productor extends Thread {
    private Buffer buffer;

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        int i = 0;
        while(true) {
            buffer.producir(i);
            System.out.println("Producido: " + i);
            i++;
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}

