class ContadorSync {
    private int c = 0;  // Atributo Contador
    ContadorSync(int c) {
        this.c = c;
    }

    public void incrementaSync() {
        c++;
    }

    public int valorSync() {
        return c;
    }
} // Fin Class Contador


class ExecutionTimerSync {
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
} // Fin Class ExecutionTimer


class HiloSumadorSync extends Thread {
    private ContadorSync contador;

    public HiloSumadorSync(String nombre, ContadorSync c) {
        setName(nombre);
        contador = c;
    }

    public void run() {
        synchronized(contador) {
            for(int j = 0; j < 3000; j++) {  // Igual que el profesor indica probar
                contador.incrementaSync();
            }
            System.out.println(getName() + " - contador vale " + contador.valorSync());
        }
    }
} // Fin Class HiloSumador


public class ua2tarea1fr2 {
    public static void main(String[] args) {
        System.out.println("----------------------------");
        System.out.println("Hilos: Bloques Sincronizados");
        System.out.println("----------------------------");
        ExecutionTimer timer = new ExecutionTimer();

        ContadorSync cont = new ContadorSync(0);

        HiloSumadorSync hilo1 = new HiloSumadorSync("Hilo 1", cont);
        HiloSumadorSync hilo2 = new HiloSumadorSync("Hilo 2", cont);
        HiloSumadorSync hilo3 = new HiloSumadorSync("Hilo 3", cont);
        HiloSumadorSync hilo4 = new HiloSumadorSync("Hilo 4", cont);
        HiloSumadorSync hilo5 = new HiloSumadorSync("Hilo 5", cont);

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
        }
        catch (InterruptedException e) {}

        System.out.println("--------------------------------------");
        System.out.println("... Finaliza la ejecución de los hilos");
        System.out.println("--------------------------------------");
        System.out.println("Valor Final del Contador: " + cont.valorSync());
        System.out.println("--------------------------------------");
        timer.stop();
        timer.printElapsedTime();
    }
}