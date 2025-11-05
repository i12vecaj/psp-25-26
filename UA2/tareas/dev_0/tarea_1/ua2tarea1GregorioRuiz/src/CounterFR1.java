public class CounterFR1 {
    public static void main(String[] args) {
        int THREADS = 5;
        int INCREMENTS = 1000;

        Contador contador = new Contador(0);
        Thread[] hilos = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            hilos[i] = new Incrementador("T" + i, contador, INCREMENTS);
            hilos[i].start();
        }

        for (Thread t : hilos) {
            try { t.join(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); System.err.println("Interrumpido"); return; }
        }

        System.out.println("Esperado: " + (THREADS * INCREMENTS));
        System.out.println("Resultado: " + contador.valor());
    }
}

/*
- Varias hebras ejecutan contador.incrementa() sin sincronizar.
- La operación c++ no es atómica: lectura, incremento y escritura pueden entrelazarse.
- Por eso el resultado final suele ser menor que el esperado.
*/
class Contador {
    private int c;
    Contador(int init) { c = init; }
    void incrementa() { c++; }
    int valor() { return c; }
}

class Incrementador extends Thread {
    private final Contador contador;
    private final int reps;
    Incrementador(String name, Contador c, int reps) { super(name); contador = c; this.reps = reps; }
    @Override
    public void run() {
        for (int i = 0; i < reps; i++) contador.incrementa();
    }
}