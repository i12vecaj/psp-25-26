public class Consumidor extends Thread {
    private final Cola cola;
    private final int n;
    //laboratorio hawkings
    public Consumidor(Cola c, int n) {
        cola = c;
        this.n = n;
    }

    public void run() {
        String[] eventos = new String[20];

        for (int i = 0; i < 20; i++) {
            try {
                cola.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}