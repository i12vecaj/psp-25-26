import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class VasoCerveza {
    private int id;
    private int tipo;

    public VasoCerveza(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
        System.out.println("[VasoCerveza] Creado: " + this);
    }

    public int getId() {
        System.out.println("[VasoCerveza] getId() -> " + id);
        return id;
    }

    public void setId(int id) {
        System.out.println("[VasoCerveza] setId(" + id + ")");
        this.id = id;
    }

    public int getTipo() {
        System.out.println("[VasoCerveza] getTipo() -> " + tipo);
        return tipo;
    }

    public void setTipo(int tipo) {
        System.out.println("[VasoCerveza] setTipo(" + tipo + ")");
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        String sTipo = (tipo == 0) ? "media pinta" : "pinta";
        return "Vaso{id=" + id + ", tipo=" + sTipo + "}";
    }
}

class Camarero {
    private String nombre;
    private final List<VasoCerveza> listaVasos;
    private final Random rnd = new Random();

    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        System.out.println("[Camarero] Constructor: " + nombre + " - creando vasos iniciales...");
        for (int i = 0; i < 3; i++) {
            int tipo = rnd.nextInt(2);
            VasoCerveza v = new VasoCerveza(i, tipo);
            listaVasos.add(v);
        }
        contarVasos();
    }

    public synchronized VasoCerveza servirCerveza(String nombreCliente) {
        System.out.println("[Camarero:" + nombre + "] " + nombreCliente + " pide un vaso...");
        while (listaVasos.isEmpty()) {
            try {
                System.out.println("[Camarero:" + nombre + "] No hay vasos disponibles. " + nombreCliente + " espera...");
                wait();
            } catch (InterruptedException e) {
                System.err.println("[Camarero:" + nombre + "] servirCerveza interrumpido mientras " + nombreCliente + " esperaba.");
                Thread.currentThread().interrupt();
                return null;
            }
        }
        int idx = rnd.nextInt(listaVasos.size());
        VasoCerveza vaso = listaVasos.remove(idx);
        System.out.println("[Camarero:" + nombre + "] Sirviendo " + vaso + " a " + nombreCliente + ". Vasos restantes: " + listaVasos.size());
        contarVasos();
        return vaso;
    }

    public synchronized void devolverCerveza(VasoCerveza vaso, String nombreCliente) {
        if (vaso == null) {
            System.err.println("[Camarero:" + nombre + "] devolverCerveza recibió un vaso nulo desde " + nombreCliente);
            return;
        }
        listaVasos.add(vaso);
        System.out.println("[Camarero:" + nombre + "] " + nombreCliente + " ha devuelto " + vaso + ". Vasos ahora: " + listaVasos.size());
        contarVasos();
        notifyAll();
    }

    public synchronized void contarVasos() {
        System.out.println("[Camarero:" + nombre + "] Contando vasos. Total disponibles = " + listaVasos.size());
        StringBuilder sb = new StringBuilder();
        for (VasoCerveza v : listaVasos) {
            sb.append(v.toString()).append(" ");
        }
        System.out.println("[Camarero:" + nombre + "] Lista vasos -> " + sb.toString().trim());
    }

    public String getNombre() {
        return nombre;
    }
}

class HilosClientes extends Thread {
    private final Camarero camarero;
    private final Random rnd = new Random();
    private double litrosTotales = 0.0;
    private static final double LITROS_PINTA = 0.568;
    private static final double LITROS_MEDIA_PINTA = 0.284;

    public HilosClientes(String nombre, Camarero camarero) {
        super(nombre);
        this.camarero = camarero;
        System.out.println("[HilosClientes] Nuevo cliente creado: " + nombre + " asignado a camarero " + camarero.getNombre());
    }

    @Override
    public void run() {
        System.out.println("[Cliente:" + getName() + "] Hilo arrancado y en ejecución.");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("[Cliente:" + getName() + "] Pide un vaso.");
                VasoCerveza vaso = camarero.servirCerveza(getName());
                if (vaso == null) {
                    System.out.println("[Cliente:" + getName() + "] No recibió vaso. Terminando hilo.");
                    break;
                }
                beber(vaso);
                System.out.println("[Cliente:" + getName() + "] Devuelve el vaso: " + vaso);
                camarero.devolverCerveza(vaso, getName());
                int sleepMs = 250 + rnd.nextInt(751);
                System.out.println("[Cliente:" + getName() + "] Dormir " + sleepMs + " ms antes de la siguiente cerveza.");
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException e) {
                    System.out.println("[Cliente:" + getName() + "] Interrumpido durante sleep. Saliendo del bucle.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("[Cliente:" + getName() + "] Excepción en run: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("[Cliente:" + getName() + "] Hilo terminando. Total litros consumidos = " + String.format("%.3f", litrosTotales) + " L");
        }
    }

    private void beber(VasoCerveza vaso) {
        System.out.println("[Cliente:" + getName() + "] Empieza a beber de " + vaso);
        try {
            int beberMs = 100 + rnd.nextInt(401);
            Thread.sleep(beberMs);
            double litros = (vaso.getTipo() == 0) ? LITROS_MEDIA_PINTA : LITROS_PINTA;
            litrosTotales += litros;
            System.out.println("[Cliente:" + getName() + "] Ha bebido " + String.format("%.3f", litros) + " L en " + beberMs + " ms. Total acumulado: " + String.format("%.3f", litrosTotales) + " L");
        } catch (InterruptedException e) {
            System.out.println("[Cliente:" + getName() + "] Interrumpido mientras bebía.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("[Cliente:" + getName() + "] Error al beber: " + e.getMessage());
        }
    }
}

public class App {
    public static void main(String[] args) {
        System.out.println("=== INICIO Simulación Bar ===");
        Camarero mou = new Camarero("Mou");
        String[] nombres = {"Homer", "Barney", "Carl", "Lenny", "Lurleen"};
        List<HilosClientes> clientes = new ArrayList<>();
        for (String n : nombres) {
            HilosClientes c = new HilosClientes(n, mou);
            clientes.add(c);
        }
        for (HilosClientes c : clientes) {
            c.start();
        }
        int tiempoSimulacionSegundos = 20;
        System.out.println("[Main] Simulación corriendo durante " + tiempoSimulacionSegundos + " segundos para pruebas.");
        try {
            Thread.sleep(tiempoSimulacionSegundos * 1000L);
        } catch (InterruptedException e) {
            System.err.println("[Main] Interrumpido mientras esperaba el fin de la simulación.");
            Thread.currentThread().interrupt();
        }
        System.out.println("[Main] Fin del tiempo de simulación. Interrumpiendo clientes...");
        for (HilosClientes c : clientes) {
            c.interrupt();
        }
        for (HilosClientes c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
                System.err.println("[Main] Interrumpido al esperar join() de " + c.getName());
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("=== FIN Simulación Bar (ua2ex1) ===");
    }
}
