import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class VasoCerveza {

    private int id;
    private int tipo; //0 = media pinta y 1 = pinta

    public VasoCerveza(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getTipo() {return tipo;}
    public void setTipo(int tipo) {this.tipo = tipo;}


    @Override
    public String toString() {
        return "VasoCerveza{" +
                "id=" + id +
                ", tipo=" + tipo +
                '}';
    }
}

class Camarero{

    private final List<VasoCerveza> listaVasos;
    private final String nombre;
    private final Random rmd = new Random();

    public Camarero(String nombre){
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();

        try{
            for (int i =0; i<3;i++){
                int tipo = rmd.nextInt(2);
                VasoCerveza vaso = new VasoCerveza(i,tipo);
                listaVasos.add(vaso);
                System.out.println("Camarerillo, añadido a la lista inicial: "+vaso);
            }
            contarVasos();
        }catch (Exception e){
            System.out.println("Error en el contructor -> "+ e.getMessage());
        }
    }

        public synchronized VasoCerveza servirCerveza(String nombreCliente){
            System.out.println("Camarero, "+nombre+" "+ nombreCliente+", solicita un vaso");
            while(listaVasos.isEmpty()){
                try{
                    System.out.println("Camarero, no hay vasos disponibles, digale al cliente "+nombreCliente+" que espere");
                    wait();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    System.out.println("Camarero, se interrumpió el servicio de servir la cerveza, el cliente seguia esperando");
                    return null;
                }
            }

            int idx = rmd.nextInt(listaVasos.size());
            VasoCerveza vaso = listaVasos.remove(idx);
            System.out.println("Camarero, "+nombre+" entregando "+vaso+" a "+nombreCliente+" vasos restantes -> "+listaVasos.size());
            return vaso;
        }

        public synchronized void devolverCerveza(VasoCerveza vaso, String nombreCliente){
        if (vaso == null){
            System.out.println("Camarero, al devolver la cerveza se recibio un vaso nulo de "+nombreCliente);
            return;
        }
        listaVasos.add(vaso);
            System.out.println("Camarero, "+nombreCliente+" devuelve "+vaso+", vasos restantes ahora -> "+listaVasos.size());
            notifyAll();
        }

        public synchronized void contarVasos(){
            System.out.println("Camarero, "+nombre+ " contando los vasos....");
            System.out.println(" ");
            System.out.println("Toral disponibles: "+listaVasos.size());
            for (VasoCerveza v : listaVasos){
                System.out.println("- "+v);
            }
        }

        public String getNombre(){
        return nombre;
        }
}

class HiloClientes extends Thread{

    private final Camarero cam;
    private final Random rmd = new Random();
    private double litrosTotales = 0.0;

    private static final double pinta_Litros = 0.568;
    private static final double media_Litros = pinta_Litros/2.0;

    public HiloClientes(String nombre, Camarero cam){
        super(nombre);
        this.cam = cam;
        System.out.println("Creando el hilo cliente -> "+nombre);
    }

    @Override
    public void run() {
        System.out.println("[HiloCliente " + getName() + "] Ejecutándose (run).");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("[HiloCliente " + getName() + "] Pidiendo un vaso de cerveza...");
                VasoCerveza vaso = null;
                try {
                    vaso = cam.servirCerveza(getName());
                } catch (Exception e) {
                    System.out.println("[HiloCliente " + getName() + "] Error al pedir vaso: " + e.getMessage());
                }

                if (vaso == null) {
                    // Si camarero devolvió null (posible por interrupción), comprobamos interrupción y salimos
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("[HiloCliente " + getName() + "] Interrumpido tras intentar servir vaso. Saliendo.");
                        break;
                    } else {
                        System.out.println("[HiloCliente " + getName() + "] No obtuvo vaso pero no fue interrumpido; intentará de nuevo.");
                        continue;
                    }
                }

                // Beber la cerveza
                beber(vaso);

                // Devolver el vaso
                try {
                    cam.devolverCerveza(vaso, getName());
                } catch (Exception e) {
                    System.out.println("[HiloCliente " + getName() + "] Error al devolver vaso: " + e.getMessage());
                }

                // Esperar un tiempo aleatorio entre 250 ms y 1000 ms
                int espera = 250 + rmd.nextInt(751); // 250..1000
                System.out.println("[HiloCliente " + getName() + "] Durmiendo " + espera + " ms antes de pedir otra cerveza.");
                try {
                    Thread.sleep(espera);
                } catch (InterruptedException e) {
                    // Si se interrumpe durante el sueño, salir limpiamente
                    System.out.println("[HiloCliente " + getName() + "] Interrumpido durante sleep. Terminando.");
                    Thread.currentThread().interrupt();
                }
            }
        } finally {
            // Reporte final al salir
            System.out.println("[HiloCliente " + getName() + "] Terminando. Litros totales consumidos: " + String.format("%.3f", litrosTotales) + " L.");
        }
    }

    private void beber(VasoCerveza vaso){
        System.out.println("Hilo del cliente "+getName()+", bebiendo del "+ vaso+" ...");
        try{
            int beberMs = 100 +rmd.nextInt(301);
            Thread.sleep(beberMs);
        }catch (InterruptedException e){
            System.out.println("Hilo cliente interrumpido, mientras se bebía el vaso "+vaso);
            Thread.currentThread().interrupt();
            return;
        }

        double litros = (vaso.getTipo() == 1) ? pinta_Litros : media_Litros;
        litrosTotales += litros;
        System.out.println("[HiloCliente " + getName() + "] Ha bebido " + litros + " L (acumulado: " + String.format("%.3f", litrosTotales) + " L).");
    }

    public double getLitrosTotales(){
        System.out.println("[HiloCliente " + getName() + "] getLitrosTotales() -> " + litrosTotales);
        return litrosTotales;
    }
}

public class Bar_De_Moe {
    public static void main(String[] args) {
        System.out.println("== INICIO de la aplicación UA2 Examen ==");
        System.out.println("Creando camarero Mou y clientes (Homer, Barney, Carl, Lenny, Lurleen).");

        Camarero mou = new Camarero("Mou");

        // Crear clientes
        String[] nombres = {"Homer", "Barney", "Carl", "Lenny", "Lurleen"};
        List<HiloClientes> clientes = new ArrayList<>();

        for (String nombre : nombres) {
            HiloClientes cliente = new HiloClientes(nombre, mou);
            clientes.add(cliente);
        }

        // Iniciar hilos
        System.out.println("Iniciando hilos de cliente...");
        for (HiloClientes c : clientes) {
            c.start();
        }

        // Ejecutar la simulación durante un cierto tiempo para pruebas (p. ej. 15 segundos)
        // Esto permite hacer varias pruebas y luego detener la ejecución sin dejar hilos colgando.
        int duracionMs = 15000;
        System.out.println("Simulación en marcha durante " + duracionMs + " ms. Puedes modificar este valor en main para pruebas.");
        try {
            Thread.sleep(duracionMs);
        } catch (InterruptedException e) {
            System.out.println("[main] Interrumpido durante la simulación: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        // Detener los hilos de cliente (interrumpir) y esperar a que terminen
        System.out.println("Deteniendo hilos de cliente...");
        for (HiloClientes c : clientes) {
            c.interrupt();
        }

        for (HiloClientes c : clientes) {
            try {
                c.join(2000); // esperar hasta 2s por hilo
            } catch (InterruptedException e) {
                System.out.println("[main] Interrumpido mientras esperaba a que " + c.getName() + " termine.");
                Thread.currentThread().interrupt();
            }
        }

        // Mostrar resumen final
        System.out.println("=== Resumen final de consumo por cliente ===");
        double totalGeneral = 0.0;
        for (HiloClientes c : clientes) {
            double litros = c.getLitrosTotales();
            System.out.println("Cliente " + c.getName() + " consumió " + String.format("%.3f", litros) + " L.");
            totalGeneral += litros;
        }
        System.out.println("Total general consumido por todos los clientes: " + String.format("%.3f", totalGeneral) + " L.");

        // Mostrar estado final del camarero
        System.out.println("Estado final de vasos en el bar:");
        mou.contarVasos();

        System.out.println("== FIN de la aplicación UA2 Examen ==");
    }
}