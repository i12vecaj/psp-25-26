class ContadorSeguro {
    private int valor = 0;

    public synchronized void incrementar() {
        valor++;
    }

    public int getValor() {
        return valor;
    }
}

class HiloIncrementador implements Runnable {
    private ContadorSeguro contador;

    public HiloIncrementador(ContadorSeguro contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            contador.incrementar();
        }
    }
}

public class ua2tarea1fr2runnable {

    public static void main(String[] args) {

        ContadorSeguro contador = new ContadorSeguro();
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(new HiloIncrementador(contador));
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error en hilo: " + e.getMessage());
            }
        }

        System.out.println("Valor final del contador (Runnable + synchronized): " + contador.getValor());
        System.out.println("Resultado esperado: 5000");
    }
}