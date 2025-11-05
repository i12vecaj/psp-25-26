package UA2.tareas.dev_0.tarea_1;

class Contador3 {
    private int valor = 0;

    // Bloque sincronizado para evitar acceso concurrente
    public synchronized void incrementar() {
        valor++;
    }

    public int obtenerValor() {
        return valor;
    }
}

class HiloIncrementador3 implements Runnable {
    private Contador3 contadorCompartido;
    private String nombreHilo;

    public HiloIncrementador3(String nombre, Contador3 contador) {
        this.nombreHilo = nombre;
        this.contadorCompartido = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            contadorCompartido.incrementar();
        }
        System.out.println(nombreHilo + " ha terminado. Valor parcial: " + contadorCompartido.obtenerValor());
    }
}

public class ua2tarea1fr2runnable {
    public static void main(String[] args) {
        Contador3 contador = new Contador3();
        Thread[] listaDeHilos = new Thread[5];

        System.out.println("Inicio de ejecución (Runnable, sincronizado)...");

        // Crear y lanzar los hilos usando Runnable
        for (int i = 0; i < listaDeHilos.length; i++) {
            Runnable tarea = new HiloIncrementador3("Hilo " + (i + 1), contador);
            listaDeHilos[i] = new Thread(tarea);
            listaDeHilos[i].start();
        }

        // Esperar la finalización de todos los hilos
        for (Thread hiloActual : listaDeHilos) {
            try {
                hiloActual.join();
            } catch (InterruptedException e) {
                System.err.println("Error al esperar el hilo: " + hiloActual.getName());
            }
        }

        System.out.println("-------------------------------------------");
        System.out.println("Valor final del contador: " + contador.obtenerValor());
        System.out.println("Resultado esperado: 5000");
        System.out.println("Runnable ofrece la misma funcionalidad que Thread, pero con mayor flexibilidad.");
    }
}
