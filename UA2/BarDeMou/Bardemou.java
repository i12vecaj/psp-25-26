import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bardemou {
    public static class VasoCerveza {
        private int id;
        private int tipo;

        public VasoCerveza(int id, int tipo) {
            this.id = id;
            this.tipo = tipo;
            System.out.println("VasoCerveza creado: id=" + id + ", tipo=" + (tipo==0?"media pinta":"pinta"));
        }

        public synchronized int getId() {
            System.out.println("getId() llamado en Vaso " + id);
            return id;
        }

        public synchronized void setId(int id) {
            System.out.println("setId(" + id + ") llamado en Vaso (antes=" + this.id + ")");
            this.id = id;
        }

        public synchronized int getTipo() {
            System.out.println("getTipo() llamado en Vaso " + id);
            return tipo;
        }

        public synchronized void setTipo(int tipo) {
            System.out.println("setTipo(" + tipo + ") llamado en Vaso " + id);
            this.tipo = tipo;
        }

        @Override
        public synchronized String toString() {
            String tipoStr = (tipo == 0) ? "media pinta" : "pinta";
            return "VasoCerveza{id=" + id + ", tipo=" + tipoStr + "}";
        }
    }

    public static class Camarero {
        private String nombre;
        private final List<VasoCerveza> listaVasos;

        public Camarero(String nombre) {
            this.nombre = nombre;
            this.listaVasos = new ArrayList<>();
            System.out.println("Camarero '" + nombre + "' creado. Inicializando vasos...");

            Random rand = new Random();

            for (int i = 0; i < 3; i++) { // Crear 3 vasos
                int tipo = rand.nextInt(2); // Tipo aleatorio: 0 media pinta, 1 pinta
                VasoCerveza v = new VasoCerveza(i, tipo);
                listaVasos.add(v);
            }
            contarVasos();
        }

        // Si no hay vasos, espera hasta que se devuelva alguno
        public synchronized VasoCerveza servirCerveza() {
            System.out.println(nombre + ": servirCerveza() solicitado.");
            try {
                while (listaVasos.isEmpty()) {
                    System.out.println(nombre + " espera vasos disponibles...");

                    // Aquí estaba el problema: si el hilo era interrumpido, se quedaba atrapado
                    wait();
                }
            } catch (InterruptedException e) {
                System.out.println(nombre + " interrumpido mientras esperaba vaso.");
                Thread.currentThread().interrupt();
                return null; // <- Solución: permite terminar al hilo cliente
            }

            Random rand = new Random();
            int index = rand.nextInt(listaVasos.size());
            VasoCerveza vaso = listaVasos.remove(index);
            System.out.println(nombre + " sirve " + vaso);
            return vaso;
        }

        // Añade el vaso devuelto y notifica a posibles clientes esperando
        public synchronized void devolverCerveza(VasoCerveza vaso) {
            if (vaso == null) {
                System.out.println(nombre + " ha recibido un vaso nulo, lo ignora.");
                return;
            }
            listaVasos.add(vaso);
            System.out.println(nombre + " recibe de vuelta " + vaso);
            notifyAll();
        }

        public synchronized void contarVasos() {
            System.out.println("Vasos disponibles en el bar (" + nombre + "):");
            for (VasoCerveza vaso : listaVasos) {
                System.out.println("  " + vaso);
            }
        }
    }

    public static class HilosClientes extends Thread {
        private Camarero camarero;
        private double totalCervezaBebida; // en LITROS
        private final Random rand = new Random();

        public HilosClientes(String nombre, Camarero camarero) {
            super(nombre); // asigna el nombre al hilo
            this.camarero = camarero;
            this.totalCervezaBebida = 0.0;
            System.out.println("Cliente creado: " + nombre);
        }

        @Override
        public void run() {
            System.out.println(getName() + " está ejecutándose.");
            while (true) { // Bucle infinito para pedir cerveza
                try {
                    // Pedir un vaso de Cerveza
                    VasoCerveza vaso = camarero.servirCerveza();
                    if (vaso == null) {
                        System.out.println(getName() + " no obtuvo vaso (null), termina su ejecución.");
                        break;
                    }

                    // Beber la rica y deliciosa Cerveza
                    double cantidad = (vaso.getTipo() == 0) ? 0.25 : 0.5; // media pinta = 0.25L, pinta = 0.5L
                    totalCervezaBebida += cantidad;
                    System.out.println(getName() + " bebe " + cantidad + " litros de cerveza. (total=" + totalCervezaBebida + "L)");

                    // Devolver el vaso de Cerveza
                    camarero.devolverCerveza(vaso);

                    // Esperar antes de pedir otra Cerveza
                    int sleepTime = 250 + rand.nextInt(751); // entre 250 ms y 1000 ms
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        System.out.println(getName() + " interrumpido durante el sueño.");
                        Thread.currentThread().interrupt();
                        break;
                    }
                } catch (Exception ex) {
                    System.out.println(getName() + " ha sufrido un error: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }
            }
            System.out.println(getName() + " finaliza. Total bebida: " + totalCervezaBebida + "L");
        }

        public double getTotalCervezaBebida() {
            System.out.println(getName() + " getTotalCervezaBebida() -> " + totalCervezaBebida + "L");
            return totalCervezaBebida;
        }
    }

    public static void main(String[] args) {
        Camarero mou = new Camarero("Mou");

        HilosClientes homer = new HilosClientes("Homer", mou);
        HilosClientes barney = new HilosClientes("Barney", mou);
        HilosClientes carl = new HilosClientes("Carl", mou);
        HilosClientes lenny = new HilosClientes("Lenny", mou);
        HilosClientes lurleen = new HilosClientes("Lurleen", mou);

        homer.start();
        barney.start();
        carl.start();
        lenny.start();
        lurleen.start();

        // Ejecutar prueba por 10 segundos y luego detener los clientes
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main: deteniendo clientes...");
        homer.interrupt();
        barney.interrupt();
        carl.interrupt();
        lenny.interrupt();
        lurleen.interrupt();

        try {
            homer.join();
            barney.join();
            carl.join();
            lenny.join();
            lurleen.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Resumen de consumo:");
        System.out.println("Homer: " + homer.getTotalCervezaBebida() + "L");
        System.out.println("Barney: " + barney.getTotalCervezaBebida() + "L");
        System.out.println("Carl: " + carl.getTotalCervezaBebida() + "L");
        System.out.println("Lenny: " + lenny.getTotalCervezaBebida() + "L");
        System.out.println("Lurleen: " + lurleen.getTotalCervezaBebida() + "L");

    }
}