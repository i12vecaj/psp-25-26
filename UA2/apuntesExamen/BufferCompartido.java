package ejemplosproductorc;

class Buffer {
    private int dato;
    private boolean disponible = false;

    public synchronized void producir(int valor) {
        while(disponible) {
            try { wait(); } catch (InterruptedException e) {}
        }
        dato = valor;
        disponible = true;
        notify();
    }

    public synchronized int consumir() {
        while(!disponible) {
            try { wait(); } catch (InterruptedException e) {}
        }
        disponible = false;
        notify();
        return dato;
    }
}

