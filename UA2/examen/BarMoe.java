import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bar {
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("Bienvenido al Bar de Moe");
        System.out.println("======================================================================");

        // Se crea el camarero que gestionará los vasos.
        Camarero mou = new Camarero("Mou");

        // Hilos de los clientes
        HilosClientes homer = new HilosClientes("Homer", mou);
        HilosClientes barney = new HilosClientes("Barney", mou);
        HilosClientes carl = new HilosClientes("Carl", mou);
        HilosClientes lenny = new HilosClientes("Lenny", mou);
        HilosClientes lurleen = new HilosClientes("Lurleen", mou);

        // Se inician todos los hilos de los clientes.
        // Se ejecutan de forma concurrente.
        homer.start();
        barney.start();
        carl.start();
        lenny.start();
        lurleen.start();
    }
}

/**
 * FR1 - Vaso de cerveza
 */
class VasoCerveza {
    private final int id;
    private static int nextId = 0; // Otro contador para generar IDs únicos para cada vaso.
    private int tipo; // 0 para media pinta, 1 para pinta.

    public VasoCerveza(int tipo) {
        this.id = nextId++;
        this.tipo = tipo;
        System.out.println("Creado " + this);
    }

    public int getID() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Vaso: " + id + " - " + (tipo == 0 ? "Media Pinta" : "Pinta Entera");
    }
}

/**
 * FR2 - Simula al camarero que sirve y recibe los vasos.
 */
class Camarero {
    private final List<VasoCerveza> listaVasos; // Lista que contiene los vasos disponibles.
    private final String nombre;

    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        System.out.println("Camarero " + nombre + " contratado.");
        // El camarero empieza con 3 vasos de tipo aleatorio.
        for (int i = 0; i < 3; i++) {
            listaVasos.add(new VasoCerveza(new Random().nextInt(2))); // nextInt(2) para generar un 0 o un 1
        }
        contarVasos();
    }

    /**
     * Este método está sincronizado para evitar que varios
     * clientes cojan un vaso a la vez, causando condiciones de carrera y errores.
     */
    public synchronized VasoCerveza servirCerveza() {
        // Si no hay vasos, el hilo (cliente) que llama a este método se pone en espera.
        // Se usa un 'while' en lugar de 'if' para prevenir "despertares".
        while (listaVasos.isEmpty()) {
            try {
                System.out.println("AVISO: No hay vasos limpios. " + Thread.currentThread().getName() + " debe esperar.");
                wait();
            } catch (InterruptedException e) {
                System.err.println("Error: "+ e.getMessage());
                e.printStackTrace();
            }
        }
        // Cuando hay vasos, se saca uno aleatoriamente de la lista.
        VasoCerveza vaso = listaVasos.remove(new Random().nextInt(listaVasos.size()));
        System.out.println("======================================================================");
        System.out.println(nombre + " sirve " + vaso + " a " + Thread.currentThread().getName());
        System.out.println("======================================================================\n");
        contarVasos();
        notifyAll(); // Notifica a todos los hilos en espera (wait) que el estado ha cambiado.
        return vaso;
    }

    /**
     * Sincronizado para mantener la consistencia de la lista de vasos.
     */
    public synchronized void devolverCerveza(VasoCerveza vaso) {
        listaVasos.add(vaso);
        System.out.println(Thread.currentThread().getName() + " ha devuelto el " + vaso);
        System.out.println("======================================================================\n");
        contarVasos();
        // Notifica a los hilos que puedan estar esperando por un vaso.
        notifyAll();
    }

    public void contarVasos() {
        System.out.println("======================================================================");
        System.out.println("Vasos disponibles en la barra: " + listaVasos.size());
        System.out.println("======================================================================\n");
    }
}

/**
 * FR3 - Hilo que representa a los clientes (consumidores)
 */
class HilosClientes extends Thread {
    private final Camarero camarero; // Objeto compartido.
    private double cervezaBebida = 0; // en litros.

    public HilosClientes(String nombre, Camarero camarero) {
        super(nombre); // Asigna el nombre al hilo.
        this.camarero = camarero;
    }

    @Override
    public void run() {
        System.out.println("======================================================================");
        System.out.println("Cliente " + getName() + " ha entrado en el bar.\n");
        // FR3 - Bucle infinito para simular que beben constantemente
        while (true) {
            try {
                // 1. Pedir un vaso de Cerveza (operación bloqueante si no hay vasos)
                VasoCerveza vaso = camarero.servirCerveza();

                // 2. Beber la cerveza (simulado con una pausa)
                System.out.println(getName() + " está bebiendo de " + vaso);

                // 3. Contabilizar la cerveza bebida
                cervezaBebida += (vaso.getTipo() == 0) ? 0.284 : 0.568; // Media pinta son 0.284L y una pinta son 0.568L.
                System.out.println(getName() + " ha bebido un total de " + String.format("%.2f", cervezaBebida) + " litros.");

                // 4. Devolver el vaso.
                camarero.devolverCerveza(vaso);

                // 5. Esperar antes de pedir otra cerveza.
                System.out.println(getName() + " descansa antes de la siguiente ronda.");
                System.out.println("======================================================================\n");
                Thread.sleep(new Random().nextInt(1000 - 250 + 1) + 250); // Pausa entre 250ms y 1000ms.
                //Thread.sleep(new Random().nextInt(3001) + 2000); // Pausa entre 2000ms (2s) y 5000ms (5s). (Para ver la ejecución más lentamente)
            } catch (InterruptedException e) {
                System.err.println("Error: "+ e.getMessage());
                e.printStackTrace();
            }
        }
    }
}