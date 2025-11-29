public class CounterFR2_Thread {
    public static void main(String[] args) {
        int THREADS = 5;
        int INCREMENTS = 1000;

        Contador contador = new Contador(0);
        Thread[] hilos = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            hilos[i] = new IncrementadorSync("T" + i, contador, INCREMENTS);
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
Explicación simplificada:
- Cada incremento se hace dentro de synchronized(contador), por lo que sólo una hebra a la vez
  modifica el contador.
- Evita la condición de carrera y el resultado coincide con lo esperado.
*/
class Contador {
    private int c;
    Contador(int init) { c = init; }
    void incrementa() { c++; }
    int valor() { return c; }
}

class IncrementadorSync extends Thread {
    private final Contador contador;
    private final int reps;
    IncrementadorSync(String name, Contador c, int reps) { super(name); contador = c; this.reps = reps; }
    @Override
    public void run() {
        for (int i = 0; i < reps; i++) {
            synchronized (contador) {
                contador.incrementa();
            }
        }
    }
}