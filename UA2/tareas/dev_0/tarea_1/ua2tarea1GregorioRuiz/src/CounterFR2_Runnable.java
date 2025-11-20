public class CounterFR2_Runnable {
    public static void main(String[] args) {
        int THREADS = 5;
        int INCREMENTS = 1000;

        Contador contador = new Contador(0);
        Thread[] hilos = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            Runnable task = createIncrementRunnable(contador, INCREMENTS);
            hilos[i] = new Thread(task, "R" + i);
            hilos[i].start();
        }

        for (Thread t : hilos) {
            try { t.join(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); System.err.println("Interrumpido"); return; }
        }

        System.out.println("Esperado: " + (THREADS * INCREMENTS));
        System.out.println("Resultado: " + contador.valor());
    }

    static Runnable createIncrementRunnable(final Contador contador, final int reps) {
        return new Runnable() {
            public void run() {
                for (int i = 0; i < reps; i++) {
                    synchronized (contador) {
                        contador.incrementa();
                    }
                }
            }
        };
    }
}

/*
Explicación simplificada:
- La tarea es un Runnable; cada hilo ejecuta la misma tarea.
- La sección crítica está sincronizada sobre el objeto contador, por eso el resultado esperado se alcanza.
- Usar Runnable separa la tarea del hilo (bueno para diseño), pero la sincronización funciona igual.
*/
class Contador {
    private int c;
    Contador(int init) { c = init; }
    void incrementa() { c++; }
    int valor() { return c; }
}