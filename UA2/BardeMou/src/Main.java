import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// FR1: Clase interna VasoCerveza
class VasoCerveza {
    private int id;
    private int tipo; // 0 = media pinta, 1 = pinta

    public VasoCerveza(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "Vaso con id=" + id + ", tipo=" + (tipo == 0 ? "media pinta" : "pinta");
    }
}

// FR2: Clase interna Camarero
class Camarero {
    private String nombre;
    private List<VasoCerveza> listaVasos = new ArrayList<>();
    private Random r = new Random();

    public Camarero(String nombre) {
        this.nombre = nombre;

        for (int i = 0; i < 3; i++) {
            int tipo = r.nextInt(2);
            VasoCerveza vaso = new VasoCerveza(i, tipo);
            listaVasos.add(vaso);
        }
        contarVasos();
    }

    public synchronized VasoCerveza servirCerveza() {
        while (listaVasos.isEmpty()) {
            try {
                System.out.println("No hay vasos. "
                        + Thread.currentThread().getName() + " espera.");
                wait();
            } catch (InterruptedException e) {}
        }

        int index = r.nextInt(listaVasos.size());
        VasoCerveza vaso = listaVasos.remove(index);

        System.out.println(nombre + " sirve " + vaso + " a "
                + Thread.currentThread().getName());

        contarVasos();
        return vaso;
    }

    public synchronized void devolverCerveza(VasoCerveza vaso) {
        listaVasos.add(vaso);
        System.out.println(Thread.currentThread().getName() + " devuelve " + vaso
                + " a " + nombre);
        contarVasos();
        notifyAll();
    }

    public synchronized void contarVasos() {
        System.out.println("Vasos disponibles: " + listaVasos);
    }
}

// ===== FR3: Clase interna HilosClientes =====
class HilosClientes extends Thread {

    private Camarero camarero;
    private double litrosBebidos = 0;

    public HilosClientes(String nombre, Camarero camarero) {
        super(nombre);
        this.camarero = camarero;
    }

    @Override
    public void run() {
        System.out.println(getName() + " entra al bar.");

        while (true) {
            System.out.println(getName() + " pide una cerveza.");
            VasoCerveza vaso = camarero.servirCerveza();

            System.out.println(getName() + " bebe " + vaso);

            if (vaso.getTipo() == 0) litrosBebidos += 0.284;
            else litrosBebidos += 0.568;

            System.out.println(getName() + " ha bebido " + litrosBebidos + " litros.");

            System.out.println(getName() + " devuelve el vaso.");
            camarero.devolverCerveza(vaso);

            try {
                int espera = 250 + (int)(Math.random() * 751);
                System.out.println(getName() + " espera " + espera + " ms.");
                Thread.sleep(espera);
            } catch (InterruptedException e) {}
        }
    }
}

public class Main {

        public static void main(String[] args) {

            Camarero mou = new Camarero("Mou");

            HilosClientes h1 = new HilosClientes("Homer", mou);
            HilosClientes h2 = new HilosClientes("Barney", mou);
            HilosClientes h3 = new HilosClientes("Carl", mou);
            HilosClientes h4 = new HilosClientes("Lenny", mou);
            HilosClientes h5 = new HilosClientes("Lurleen", mou);

            h1.start();
            h2.start();
            h3.start();
            h4.start();
            h5.start();
        }
    }





