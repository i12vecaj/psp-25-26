// ua2ex1.java
// Único fichero Java que implementa FR1..FR5
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ua2ex1 {
    public static void main(String[] args) {
        System.out.println("INICIO de la aplicación (FR4).");

        // Creamos el camarero con nombre Mou (FR4)
        Camarero mou = new Camarero("Mou");

        // Creamos los clientes solicitados (FR4)
        HilosClientes homer   = new HilosClientes("Homer", mou, 0);   // 0 indica que repite indefinidamente hasta interrupción
        HilosClientes barney  = new HilosClientes("Barney", mou, 0);
        HilosClientes carl    = new HilosClientes("Carl", mou, 0);
        HilosClientes lenny   = new HilosClientes("Lenny", mou, 0);
        HilosClientes lurleen = new HilosClientes("Lurleen", mou, 0);

        // Arrancamos los hilos (FR4)
        homer.start();
        barney.start();
        carl.start();
        lenny.start();
        lurleen.start();

        // Realizamos varias pruebas: dejamos correr durante un tiempo controlado
        // (en un examen querrás observar el comportamiento; aquí lo automatizamos)
        try {
            Thread.sleep(20000); // 20 segundos de prueba
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido durante la espera de prueba: " + e.getMessage());
        }

        System.out.println("\n--- Fin del periodo de prueba: solicitando parada controlada de clientes ---");

        // Paramos los clientes (en el enunciado el bucle es infinito; para pruebas los interrumpimos)
        homer.interrupt();
        barney.interrupt();
        carl.interrupt();
        lenny.interrupt();
        lurleen.interrupt();

        // Esperamos a que terminen y recogemos resultados
        try {
            homer.join();
            barney.join();
            carl.join();
            lenny.join();
            lurleen.join();
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido mientras esperaba joins: " + e.getMessage());
        }

        // Mostramos estado final y totales (FR2 + FR5)
        mou.contarVasos();
        mou.mostrarTotalesConsumidos();

    }
}

// =====================
// FR1: Clase VasoCerveza
// =====================
class VasoCerveza {
    private static int contador = 0;
    private int id;
    private int tipo;
    private double litros;
    private static final Random rand = new Random();

    // Constructor: asigna id y tipo aleatorio 0/1
    public VasoCerveza() {
        this.id = ++contador;
        this.tipo = rand.nextInt(2); // 0 o 1
        this.litros = (this.tipo == 1) ? 0.5 : 0.25; // pinta=0.5L, media=0.25L
        System.out.println("[VasoCerveza] creado: " + this);
    }

    // Getters y setters (FR1)
    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        if (tipo == 0 || tipo == 1) {
            this.tipo = tipo;
            this.litros = (this.tipo == 1) ? 0.5 : 0.25;
            System.out.println("[VasoCerveza] setTipo: id=" + id + " tipo=" + tipo + " litros=" + litros);
        } else {
            System.out.println("[VasoCerveza] setTipo: valor inválido -> " + tipo);
        }
    }

    public double getLitros() {
        return litros;
    }

    @Override
    public String toString() {
        String nombreTipo = (tipo == 1) ? "Pinta" : "Media Pinta";
        return "Vaso Cerveza id: " + id + ", tipo: " + tipo + " (" + nombreTipo + "), " + litros + "L";
    }
}
// ======================
// Fin VasoCerveza
// ======================

// ======================
// Clase Camarero
// ======================
class Camarero {
    private final List<VasoCerveza> listaVasos;
    private final String nombre;
    private final int CAPACIDAD = 10; //
    private int totalConsumidos = 0;  //

    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        System.out.println("Camarero inicializando camarero: " + nombre);


        for (int i = 0; i < 3; i++) {
            VasoCerveza v = new VasoCerveza();
            listaVasos.add(v);
        }
        System.out.println("Camarero lista inicial poblada con 3 vasos.");
        contarVasos();
    }


    public synchronized VasoCerveza servirCerveza(String nombreCliente) {
        try {
            System.out.println("Camarero " + this.nombre + " atenderá petición de " + nombreCliente);
            while (listaVasos.isEmpty()) {
                System.out.println("Camarero no hay vasos disponibles, " + nombreCliente + " debe esperar...");
                wait();
            }

            int idx = (int)(Math.random() * listaVasos.size());
            VasoCerveza vaso = listaVasos.remove(idx);
            System.out.println("Camarero sirve a " + nombreCliente + ": " + vaso + " (quedan " + listaVasos.size() + ")");
            notifyAll();
            return vaso;
        } catch (InterruptedException ie) {

            System.out.println("Camarero servirCerveza interrumpido mientras " + nombreCliente + " esperaba: " + ie.getMessage());
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception e) {
            System.out.println("Camarero error en servirCerveza: " + e.getMessage());
            return null;
        }
    }


    public synchronized void devolverCerveza(VasoCerveza vaso, String nombreCliente) {
        try {
            if (vaso == null) {
                System.out.println("Camarer devolverCerveza: vaso nulo recibido de " + nombreCliente);
                return;
            }
            // Si queremos limitar la capacidad, esperamos si está lleno
            while (listaVasos.size() >= CAPACIDAD) {
                System.out.println("Camarero almacén lleno. " + nombreCliente + " espera para devolver vaso...");
                wait();
            }
            listaVasos.add(vaso);
            System.out.println("Camarero " + nombreCliente + " devuelve vaso: " + vaso + " (ahora hay " + listaVasos.size() + ")");
            notifyAll();
        } catch (InterruptedException ie) {
            System.out.println("Camarero devolverCerveza interrumpido por " + nombreCliente + ": " + ie.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Camarero error en devolverCerveza: " + e.getMessage());
        }
    }


    public synchronized void contarVasos() {
        System.out.println(" Camarero (" + nombre + ") hay " + listaVasos.size() + " vasos disponibles.");
        for (VasoCerveza v : listaVasos) {
            System.out.println("   - " + v);
        }
    }


    public synchronized void incrementarTotalConsumidos(double litros) {

        totalConsumidos++;
        System.out.println("Camarero totalConsumidos incrementado. Total elementos consumidos: " + totalConsumidos + " (litros añadidos: " + litros + "L)");
    }


    public synchronized void mostrarTotalesConsumidos() {
        System.out.println("Camarero  vasos consumidos: " + totalConsumidos);
    }
}
// ======================
// Fin Camarero
// ======================

// ======================
//  Clase HilosClientes
// ======================
class HilosClientes extends Thread {
    private final Camarero camarero;
    private final int vasosAConsumir;
    private double litrosConsumidos = 0.0;
    private int vasosConsumidos = 0;
    private final Random rand = new Random();

    public HilosClientes(String nombre, Camarero camarero, int vasosAConsumir) {
        super(nombre);
        this.camarero = camarero;
        this.vasosAConsumir = vasosAConsumir;
        System.out.println("Hilos Clientes hilo creado con nombre: " + nombre + " vasos consumidos: " + vasosAConsumir);
    }

    @Override
    public void run() {
        System.out.println("HilosClientes " + getName() + " empieza a ejecutarse.");
        try {

            int contador = 0;
            while (!isInterrupted() && (vasosAConsumir == 0 || contador < vasosAConsumir)) {

                VasoCerveza vaso = camarero.servirCerveza(getName());
                if (vaso == null) {

                    if (isInterrupted()) break;
                    else continue;
                }


                System.out.println("Hilos Clientes " + getName() + " está bebiendo " + vaso);
                try {

                    Thread.sleep(100 + rand.nextInt(301));
                } catch (InterruptedException ie) {
                    System.out.println("Hilos Clientes " + getName() + " interrumpido mientras bebía.");
                    Thread.currentThread().interrupt();
                }


                litrosConsumidos += vaso.getLitros();
                vasosConsumidos++;
                camarero.incrementarTotalConsumidos(vaso.getLitros());


                camarero.devolverCerveza(vaso, getName());


                int espera = 250 + rand.nextInt(751); // 250..1000
                try {
                    Thread.sleep(espera);
                } catch (InterruptedException ie) {
                    System.out.println("Hilos Clientes " + getName() + " interrumpido durante tiempo entre cervezas.");
                    Thread.currentThread().interrupt();
                }
                contador++;
            }
        } catch (Exception e) {
            System.out.println("Hilos Clientes " + getName() + " ha lanzado excepción: " + e.getMessage());
        } finally {
            System.out.printf("HilosC lientes %s ha terminado. Vasos consumidos=%d, Litros consumidos=%.2fL%n",
                    getName(), vasosConsumidos, litrosConsumidos);
        }
    }
}
// ==================
// Fin HilosClientes
// ==================
