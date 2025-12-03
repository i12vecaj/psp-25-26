package BardeMou;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppMain {

    public static final double LITROS_MEDIA_PINTA = 0.568 / 2.0; // 0.284
    public static final double LITROS_PINTA = 0.568; // 0.568   
    public static void main(String[] args) {
        System.out.println("[MAIN] Iniciando aplicación MoesTabern");

        Camarero mou = new Camarero("Mou");

        // Creamos clientes y les pasamos el camarero compartido
        HilosClientes homer = new HilosClientes("Homer", mou);
        HilosClientes barney = new HilosClientes("Barney", mou);
        HilosClientes carl = new HilosClientes("Carl", mou);
        HilosClientes lenny = new HilosClientes("Lenny", mou);
        HilosClientes lurleen = new HilosClientes("Lurleen", mou);

        // Iniciamos los hilos clientes
        homer.start();
        barney.start();
        carl.start();
        lenny.start();
        lurleen.start();

        System.out.println("[MAIN] Clientes arrancados. El sistema funciona en bucle infinito (usa Ctrl+C para parar).");
    }
}

class VasoCerveza{

    private int id;
    private int tipo;

    public VasoCerveza(int id, int tipo){
        if(id < 0) throw new IllegalArgumentException("el id del vaso no puede ser negativo");
        if(tipo != 0 && tipo != 1) throw new IllegalArgumentException("");
        this.id = id;
        this.tipo = tipo;
        System.out.println("[VasoCerveza] Creado: " + this);
       
    }

    public synchronized int getId() {
        System.out.println("[VasoCerveza] getId() -> " + id);
        return id;
    }

    public synchronized int getTipo() {
        System.out.println("[VasoCerveza] getTipo() -> " + tipo);
        return tipo;
    }

    public synchronized void setId(int id) {
        System.out.println("[VasoCerveza] setId("+ id +")");
        if(id < 0) throw new IllegalArgumentException("el id no puede ser negativo");
        this.id = id;
    }

    public synchronized void setTipo(int tipo) {
        System.out.println("[VasoCerveza] setTipo("+ tipo +")");
        if(tipo != 0 && tipo != 1) throw new IllegalArgumentException("tipo debe ser 0 o 1");
        this.tipo = tipo;
    }

    @Override
    public synchronized String toString() {
        String t = (tipo == 0) ? "MediaPinta" : "Pinta";
        return "VasoCerveza [id=" + id + ", tipo=" + tipo + "("+ t +")" +"]";
    }
}

class Camarero{
    private String nombre;
    private final List<VasoCerveza> ListaVasos;
    private Random rnd = new Random();

    public Camarero(String nombre){
        if(nombre == null){
            nombre = "Sin nombre";
        }
        this.nombre = nombre;
        this.ListaVasos = new ArrayList<>();
        System.out.println("[Camarero] Creando camarero: " + nombre);
        for(int i = 0; i < 3; i++){
            int tipo = rnd.nextInt(2);
            VasoCerveza v =new VasoCerveza(i, tipo);
            ListaVasos.add(v);
            System.out.println("[Camarero] Añadido vaso inicial: " + v);
        }
        contarVasos();
    }

 public synchronized VasoCerveza servirCerveza(String nombreCliente) {
        System.out.println("[Camarero:" + nombre + "] " + nombreCliente + " pide un vaso.");
        try {
            while (ListaVasos.isEmpty()) {
                System.out.println("[Camarero:" + nombre + "] No hay vasos disponibles. " + nombreCliente + " espera...");
                wait(); // espera hasta que devolvemos un vaso
            }
            // elegir un vaso aleatoriamente de la lista
            int idx = rnd.nextInt(ListaVasos.size());
            VasoCerveza vaso = ListaVasos.remove(idx);
            System.out.println("[Camarero:" + nombre + "] Sirviendo " + vaso + " a " + nombreCliente + ". Vasos restantes: " + ListaVasos.size());
            return vaso;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[Camarero:" + nombre + "] servirCerveza interrumpido para " + nombreCliente);
            return null;
        } catch (Exception e) {
            System.err.println("[Camarero:" + nombre + "] Error en servirCerveza: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // devolverCerveza: devuelve el vaso a la lista y notifica
    public synchronized void devolverCerveza(VasoCerveza vaso, String nombreCliente) {
        if (vaso == null) {
            System.err.println("[Camarero:" + nombre + "] " + nombreCliente + " intentó devolver un vaso nulo. Ignorado.");
            return;
        }
        ListaVasos.add(vaso);
        System.out.println("[Camarero:" + nombre + "] " + nombreCliente + " devuelve " + vaso + ". Vasos ahora: " + ListaVasos.size());
        notifyAll();
    }

    // contarVasos: imprime vasos disponibles
    public synchronized void contarVasos() {
        System.out.println("[Camarero:" + nombre + "] Contando vasos. Total: " + ListaVasos.size());
        for (VasoCerveza v : ListaVasos) {
            System.out.println("  - " + v);
        }
    }

    public String getNombre() {
        return nombre;
    }
}

/* -------------------- HilosClientes -------------------- */
class HilosClientes extends Thread {
    private final Camarero camarero;
    private final Random rnd = new Random();
    private double litrosTotales = 0.0;

    public HilosClientes(String nombre, Camarero camarero) {
        if (nombre == null) nombre = "ClienteSinNombre";
        super(nombre); // asigna el nombre al hilo
        if (camarero == null) throw new IllegalArgumentException("camarero compartido no puede ser nulo");
        this.camarero = camarero;
        System.out.println("[HilosClientes] Creado hilo cliente: " + nombre + " (asignado a camarero " + camarero.getNombre() + ")");
    }

    @Override
    public void run() {
        System.out.println("[Cliente:" + getName() + "] Hilo arrancado y ejecutándose.");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Pedir un vaso
                System.out.println("[Cliente:" + getName() + "] Pide un vaso al camarero.");
                VasoCerveza vaso = camarero.servirCerveza(getName());
                if (vaso == null) {
                    System.out.println("[Cliente:" + getName() + "] No recibió vaso (null). Reintentando.");
                    // pequeño sleep para evitar bucle caliente si algo va mal
                    try { Thread.sleep(200); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
                    continue;
                }

                // Beber la cerveza
                System.out.println("[Cliente:" + getName() + "] Empieza a beber " + vaso + ".");
                // Simulamos el tiempo de beber: entre 100ms y 600ms
                int beberMs = 100 + rnd.nextInt(500);
                try { Thread.sleep(beberMs); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }

                // Contabilizar cantidad en litros según tipo
                double litros = (vaso.getTipo() == 0) ? AppMain.LITROS_MEDIA_PINTA : AppMain.LITROS_PINTA;
                litrosTotales += litros;
                System.out.printf("[Cliente:%s] Ha bebido %.3f L en esta ronda (tipo=%d). Total acumulado: %.3f L%n",
                                  getName(), litros, vaso.getTipo(), litrosTotales);

                // Devolver el vaso
                System.out.println("[Cliente:" + getName() + "] Devuelve " + vaso + " al camarero.");
                camarero.devolverCerveza(vaso, getName());

                // Esperar antes de pedir otra: dormir entre 250 ms y 1000 ms
                int espera = 250 + rnd.nextInt(751); // 250..1000
                System.out.println("[Cliente:" + getName() + "] Espera " + espera + " ms antes de pedir otra cerveza.");
                try { Thread.sleep(espera); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
            }
        } catch (Exception e) {
            System.err.println("[Cliente:" + getName() + "] Excepción en run(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("[Cliente:" + getName() + "] Hilo terminando. Total consumido: " + litrosTotales + " L");
        }
    }

    // getter del total (por si se quiere consultar desde fuera)
    public synchronized double getLitrosTotales() {
        System.out.println("[Cliente:" + getName() + "] getLitrosTotales() -> " + litrosTotales);
        return litrosTotales;
    }
}
