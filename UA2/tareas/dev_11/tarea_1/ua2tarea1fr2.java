package UA2.tareas.dev_0.tarea_1;

class Contador2 {
    private int valor = 0;

    // Método sincronizado: solo un hilo puede ejecutar este método a la vez
    public synchronized void incrementar() {
        valor++;
    }

    public int obtenerValor() {
        return valor;
    }
}

class HiloIncrementador2 extends Thread {
    private Contador2 contadorCompartido;

    public HiloIncrementador2(String nombre, Contador2 contador) {
        super(nombre);
        this.contadorCompartido = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            contadorCompartido.incrementar();
        }
        System.out.println(getName() + " ha terminado. Valor parcial: " + contadorCompartido.obtenerValor());
    }
}

public class ua2tarea1fr2 {
    public static void main(String[] args) {
        Contador2 contador = new Contador2();
        HiloIncrementador2[] listaDeHilos = new HiloIncrementador2[5];

        System.out.println("Inicio de ejecución (con sincronización)...");

        // Crear y lanzar los 5 hilos
        for (int i = 0; i < listaDeHilos.length; i++) {
            listaDeHilos[i] = new HiloIncrementador2("Hilo " + (i + 1), contador);
            listaDeHilos[i].start();
        }

        // Esperar a que terminen todos los hilos
        for (HiloIncrementador2 hiloActual : listaDeHilos) {
            try {
                hiloActual.join();
            } catch (InterruptedException e) {
                System.err.println("Error al esperar el hilo: " + hiloActual.getName());
            }
        }

        System.out.println("-------------------------------------------");
        System.out.println("Valor final del contador: " + contador.obtenerValor());
        System.out.println("Resultado esperado: 5000");
        System.out.println("Gracias a 'synchronized', los hilos acceden de forma segura al contador.");
    }
}
