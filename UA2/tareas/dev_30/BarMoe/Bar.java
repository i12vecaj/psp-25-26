import java.util.List;

import java.util.ArrayList;
import java.util.Random;

class VasoCerveza {

    public static final int MEDIA_PINTA = 0;
    public static final double VOLUMEN_MEDIA_PINTA = 0.5;
    public static final int PINTA = 1;
    public static final double VOLUMEN_PINTA = 1.0;

    private int id;
    private int tipo;

    public VasoCerveza(int id, int tipo) {
        System.out.println("[VasoCerveza] Creando vaso ID:" + id + ", Tipo:" + tipo);
        this.id = id;
        if (tipo != MEDIA_PINTA && tipo != PINTA) {
            this.tipo = MEDIA_PINTA;
        } else {
            this.tipo = tipo;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getVolumenLitros() {
        if (this.tipo == PINTA) {
            return VOLUMEN_PINTA;
        } else {
            return VOLUMEN_MEDIA_PINTA;
        }
    }

    @Override
    public String toString() {
        String tipoString;
        if (tipo == PINTA) {
            tipoString = "Pinta (1.0L)";
        } else {
            tipoString = "Media Pinta (0.5L)";
        }
        return "Vaso ID=" + id + ", Tipo=" + tipoString;
    }
}

class Camarero {
    private String nombre;
    private final List<VasoCerveza> listaVasos;
    private final Random rand = new Random();

    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        System.out.println("--- Camarero " + nombre + ": Inicializando vasos (x3).");

        for (int i = 0; i < 3; i++) {
            // El tipo es aleatorio (0 o 1)
            VasoCerveza vaso = new VasoCerveza(i, rand.nextInt(2));
            listaVasos.add(vaso);
        }
        System.out.println("--- Camarero " + nombre + ": Creacion completada.");
        contarVasos();
    }

    public synchronized VasoCerveza servirCerveza(String nombreCliente) throws InterruptedException {
        System.out.println("\n[PETICION] Cliente " + nombreCliente + " pide a " + nombre + ".");

        while (listaVasos.isEmpty()) {
            // Mensaje de salida mejorado
            System.out.println("!!! Camarero " + nombre + ": NO HAY VASOS. Cliente " + nombreCliente + " esperando...");
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Camarero " + nombre + ": Error en wait para servir cerveza.");
                throw e;
            }
        }

        int IDElegido = rand.nextInt(listaVasos.size());
        VasoCerveza vasoElegido = listaVasos.get(IDElegido);

        listaVasos.remove(IDElegido);

        // Mensaje de salida mejorado
        System.out.println("[SERVICIO] Camarero " + nombre + ": Sirve " + vasoElegido + " a " + nombreCliente);

        notifyAll();

        return vasoElegido;
    }

    public synchronized void devolverCerveza(VasoCerveza vaso, String nombreCliente) {
        // Mensaje de salida mejorado
        System.out.println("[DEVOLUCION] Cliente " + nombreCliente + " devuelve " + vaso );

        // El vaso devuelto pasa a ser de un tipo aleatorio (Pinta o Media Pinta)
        int nuevoTipo = rand.nextInt(2);
        vaso.setTipo(nuevoTipo);

        listaVasos.add(vaso);
        // Mensaje de salida mejorado
        System.out.println("[DEVOLUCION] Camarero " + nombre + ": Vaso reciclado (Tipo " + vaso.getTipo() + ").");
        notifyAll();
        contarVasos();
    }

    public void contarVasos() {
        // Mensaje de salida mejorado
        System.out.println(">>> ESTADO: Camarero " + nombre + " tiene (" + listaVasos.size() + ") vasos disponibles.");
    }
}

class HiloCliente extends Thread {

    private final Camarero camarero;
    private double totalCervezaBebida = 0.0;

    public HiloCliente(String nombre, Camarero camarero) {
        super(nombre);
        this.camarero = camarero;
        System.out.println("[Cliente] " + nombre + ": Creado.");
    }

    @Override
    public void run() {
        System.out.println("--- Cliente " + getName() + ": ejecutandose ---");

        while (true) {
            VasoCerveza vaso = null;
            try {
                // 1. Pedir un vaso de Cerveza
                vaso = camarero.servirCerveza(getName());

                // 2. Beber la cerveza
                System.out.println("[BEBIENDO] Cliente " + getName() + ": ¡Salud! " + vaso + "...");
                Thread.sleep(200);

                // 3. Contabilizar
                totalCervezaBebida += vaso.getVolumenLitros();
                System.out.printf("[INFO] Cliente %s: Total acumulado: %.1f L%n", getName(), totalCervezaBebida);

                // 4. Devolver el vaso
                camarero.devolverCerveza(vaso, getName());

                // 5. Esperar antes de pedir otra
                Random rand = new Random();
                int tiempoDormir = rand.nextInt(750) + 250;
                System.out.println("Cliente " + getName() + ": esperando " + tiempoDormir + " ms");
                Thread.sleep(tiempoDormir);

            } catch (InterruptedException e) {
                System.err.println("Cliente " + getName() + "Terminando.");
                if (vaso != null) {
                    camarero.devolverCerveza(vaso, getName());
                }
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

public class Bar {

    public static void main(String[] args) {
        System.out.println("Bar de Moes");

        Camarero mou = new Camarero("Mou");

        List<HiloCliente> clientes = new ArrayList<>();
        clientes.add(new HiloCliente("Homer", mou));
        clientes.add(new HiloCliente("Barney", mou));
        clientes.add(new HiloCliente("Carl", mou));
        clientes.add(new HiloCliente("Lenny", mou));
        clientes.add(new HiloCliente("Lurieen", mou));

        System.out.println("--- creando clientes clientes ---");

        for (HiloCliente cliente : clientes) {
            cliente.start();
        }

        try {
            System.out.println("--- Simulacion de 10 seg ---");
            Thread.sleep(10000);

            for (HiloCliente cliente : clientes) {
                cliente.interrupt();
            }

            for (HiloCliente cliente : clientes) {
                cliente.join();
            }

        } catch (InterruptedException e) {
            System.err.println("Error en hilo principal.");
            Thread.currentThread().interrupt();
        }
        System.out.println("Fin de Simulacion");
    }
}