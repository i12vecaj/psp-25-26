import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

// FR1:
class VasoCerveza {
    private final int id;
    private final int tipo;
    private static final double LITROS_MEDIA_PINTA = 0.25;
    private static final double LITROS_PINTA = 0.50;

    
    public VasoCerveza(int id, int tipo) {
        System.out.println("Creando vaso ID: " + id + ", Tipo: " + (tipo == 0 ? "Media Pinta" : "Pinta"));
        this.id = id;
        
        if (tipo != 0 && tipo != 1) {
            System.err.println("Error: Tipo de vaso no válido (" + tipo + "). Asignando 1 Pinta");
            this.tipo = 1;
        } else {
            this.tipo = tipo;
        }
    }

    public int getId() {
        return id;
    }
    
    public double getLitros() {
        return tipo == 0 ? LITROS_MEDIA_PINTA : LITROS_PINTA;
    }

    @Override
    public String toString() {
        return "VasoCerveza [ID=" + id + ", Tipo=" + (tipo == 0 ? "Media Pinta (0.25L)" : "Pinta (0.50L)") + "]";
    }
}

// FR2
class Camarero {
    private final String nombre;
    private final List<VasoCerveza> listaVasos;
    private final Random random;
    private final AtomicInteger nextVasoId = new AtomicInteger(0);

    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        this.random = new Random();

        System.out.println("\nCamarero: " + this.nombre + " preparando vasos");
        for (int i = 0; i < 3; i++) {
            int tipoAleatorio = random.nextInt(2);
            VasoCerveza nuevoVaso = new VasoCerveza(nextVasoId.getAndIncrement(), tipoAleatorio);
            listaVasos.add(nuevoVaso);
        }
        contarVasos();
        System.out.println("------------------------------------------\n");
    }

    // FR4:
    public synchronized VasoCerveza servirCerveza() {
        String clienteNombre = Thread.currentThread().getName();
        System.out.println(clienteNombre + " pide bebida al Camarero " + this.nombre);

        while (listaVasos.isEmpty()) {
            System.out.println(clienteNombre + " .El Camarero " + this.nombre + " no tiene vasos listos");
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Error: Hilo cliente interrumpido mientras esperaba vaso.");
                Thread.currentThread().interrupt();
                return null;
            }
        }

        int indiceAleatorio = random.nextInt(listaVasos.size());
        VasoCerveza vasoServido = listaVasos.remove(indiceAleatorio);

        System.out.println(clienteNombre + " recibe " + vasoServido.toString() + " | Vasos restantes: " + listaVasos.size());
        return vasoServido;
    }

    // FR4:
    public synchronized void devolverCerveza(VasoCerveza vaso) {
        String clienteNombre = Thread.currentThread().getName();
        listaVasos.add(vaso);
        System.out.println(clienteNombre + " devuelve " + vaso.toString() + " | Vasos disponibles: " + listaVasos.size());
        notifyAll();
    }

    public void contarVasos() {
        System.out.println("  Camarero: " + this.nombre + " .Vasos disponibles: " + listaVasos.size());
        for (VasoCerveza vaso : listaVasos) {
            System.out.println("    - " + vaso);
        }
    }
}

// FR3:
class HilosClientes extends Thread {
    private final Camarero camarero;
    private double totalCervezaBebida = 0.0;

    public HilosClientes(String nombre, Camarero camarero) {
        super(nombre);
        this.camarero = camarero;
    }

    @Override
    public void run() {
        System.out.println("Cliente " + getName() + " ha llegado al bar.");
        Random random = new Random();

        while (!Thread.currentThread().isInterrupted()) {
            VasoCerveza vaso = null;
            try {
                vaso = camarero.servirCerveza();
                if (vaso == null) continue;

                double litrosBebidos = vaso.getLitros();
                totalCervezaBebida += litrosBebidos;
                System.out.println(getName() + " se está bebiendo " + litrosBebidos + " L de " + vaso.toString());

                camarero.devolverCerveza(vaso);

                System.out.printf("Bebió un total de %.2f L.", totalCervezaBebida);

                int tiempoEspera = random.nextInt(751) + 250;
                System.out.println( getName() + " está esperando " + tiempoEspera + " ms antes de hacer otra ronda");
                Thread.sleep(tiempoEspera);

            } catch (InterruptedException e) {
                System.out.println("Cliente " + getName() + " terminó y se va del bar.");
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println("Error en el cliente " + getName() + ": " + e.getMessage());
                if (vaso != null) {
                    camarero.devolverCerveza(vaso);
                }
            }
        }
    }

    public double getTotalCervezaBebida() {
        return totalCervezaBebida;
    }
}

// FR4:
public class Bar {
    private static final int TIEMPO_SIMULACION_MS = 5000;

    public static void main(String[] args) {
        System.out.println("=== INICIO DE LA SIMULACIÓN DEL BAR ===");

        Camarero moe = new Camarero("Moe");

        List<HilosClientes> clientes = new ArrayList<>();
        String[] nombresClientes = {"Homero", "Lisa", "Bart", "Cerezo", "Cordoba"};

        for (String nombre : nombresClientes) {
            clientes.add(new HilosClientes(nombre, moe));
        }

        for (HilosClientes cliente : clientes) {
            cliente.start();
        }

        // FR4:
        try {
            System.out.println("\nLa simulación correrá durante " + TIEMPO_SIMULACION_MS / 1000 + " segundos");
            Thread.sleep(TIEMPO_SIMULACION_MS);

            System.out.println("\nTiempo de simulación terminado");
            double totalLitrosGeneral = 0.0;
            for (HilosClientes cliente : clientes) {
                cliente.interrupt();
            }

            for (HilosClientes cliente : clientes) {
                cliente.join();
                totalLitrosGeneral += cliente.getTotalCervezaBebida();
                System.out.printf("Bebió un total de %.2f L. ", cliente.getName(), cliente.getTotalCervezaBebida());
            }

            System.out.printf("\nTotal de bebida consumida: %.2f L", totalLitrosGeneral);
            moe.contarVasos();

        } catch (InterruptedException e) {
            System.err.println("Error: El hilo principal fue interrumpido:" + e.getMessage());
            Thread.currentThread().interrupt();
        }

        System.out.println("=== FIN DE LA SIMULACIÓN DEL BAR ===");
    }
}