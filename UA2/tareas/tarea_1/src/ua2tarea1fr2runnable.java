class ContadorSyncRunnable {
    private int c = 0;
    ContadorSyncRunnable(int c) {
        this.c = c;
    }

    public void incrementaSync() {
        c++;
    }

    public int valorSync() {
        return c;
    }
}

class ExecutionTimerSyncRunnable {
    private long startTime = 0;
    private long endTime = 0;
    private long timeElapsed = 0;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public void elapsedTime() {
        timeElapsed = endTime - startTime;
    }

    public void printElapsedTime() {
        elapsedTime();
        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
    }
}

class HiloSumadorRunnable implements Runnable {
    private ContadorSyncRunnable contador;
    private String nombre;

    public HiloSumadorRunnable(String nombre, ContadorSyncRunnable c) {
        this.nombre = nombre;
        this.contador = c;
    }

    public void run() {
        synchronized(contador) {
            for(int j = 0; j < 3000; j++) {
                contador.incrementaSync();
            }
            System.out.println(nombre + " - contador vale " + contador.valorSync());
        }
    }
}

public class ua2tarea1fr2runnable {
    public static void main(String[] args) {
        System.out.println("----------------------------");
        System.out.println("Hilos: Bloques Sincronizados (Runnable)");
        System.out.println("----------------------------");

        ExecutionTimerSyncRunnable timer = new ExecutionTimerSyncRunnable();
        ContadorSyncRunnable cont = new ContadorSyncRunnable(0);

        Thread hilo1 = new Thread(new HiloSumadorRunnable("Hilo 1", cont));
        Thread hilo2 = new Thread(new HiloSumadorRunnable("Hilo 2", cont));
        Thread hilo3 = new Thread(new HiloSumadorRunnable("Hilo 3", cont));
        Thread hilo4 = new Thread(new HiloSumadorRunnable("Hilo 4", cont));
        Thread hilo5 = new Thread(new HiloSumadorRunnable("Hilo 5", cont));

        timer.start();
        System.out.println("Comienza la ejecución de los hilos ...");
        System.out.println("--------------------------------------");

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo5.join();
        } catch (InterruptedException e) {}

        System.out.println("--------------------------------------");
        System.out.println("... Finaliza la ejecución de los hilos");
        System.out.println("--------------------------------------");
        System.out.println("Valor Final del Contador: " + cont.valorSync());
        System.out.println("--------------------------------------");
        timer.stop();
        timer.printElapsedTime();
    }
}
