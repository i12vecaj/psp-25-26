package UA2.tareas.dev_0.tarea_1;

class Contador {
    private int valor = 0;

    public void incrementar() {
        valor++;
    }

    public int obtenerValor() {
        return valor;
    }
}

class HiloIncrementador extends Thread {
    private Contador contadorCompartido;

    public HiloIncrementador(String nombre, Contador contador) {
        super(nombre); // Asigna un nombre al hilo
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

public class ua2tarea1fr1 {
    public static void main(String[] args) {
        Contador contador = new Contador();
        HiloIncrementador[] listaDeHilos = new HiloIncrementador[5];

        System.out.println("Inicio de ejecución (sin sincronización)...");

        // Creación y lanzamiento de los 5 hilos
        for (int i = 0; i < listaDeHilos.length; i++) {
            listaDeHilos[i] = new HiloIncrementador("Hilo " + (i + 1), contador);
            listaDeHilos[i].start();
        }

        // Esperar a que todos los hilos terminen
        for (HiloIncrementador hiloActual : listaDeHilos) {
            try {
                hiloActual.join();
            } catch (InterruptedException e) {
                System.err.println("Error al esperar el hilo: " + hiloActual.getName());
            }
        }

        System.out.println("-------------------------------------------");
        System.out.println("Valor final del contador: " + contador.obtenerValor());
        System.out.println("Resultado esperado: 5000");
        System.out.println("Resultado real: probablemente menor (condiciones de carrera)");
    }
}
