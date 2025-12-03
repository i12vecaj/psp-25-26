package repaso;

import java.util.ArrayList;
import java.util.Random;

public class main extends Thread{

public static class VasoCerveza {
    private int id;
    private int tipo;

    public VasoCerveza(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
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

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }


}

public static class Camarero {
    private String nombre;
    private ArrayList<VasoCerveza> listaVasos;
    private Random random;

    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        this.random = new Random();

        // Generamos 3 vasos aleatorios
        for (int i = 0; i < 3; i++) {
            int tipo = random.nextInt(2); // 0 o 1
            listaVasos.add(new VasoCerveza(i, tipo));
        }
    }

    public VasoCerveza servirCerveza() {
        if (listaVasos.isEmpty()) {
            System.out.println("No hay más vasos de cerveza");
            return null;
        }

        int indice = random.nextInt(listaVasos.size());
        VasoCerveza vaso = listaVasos.remove(indice);

        System.out.println(nombre + " sirve un vaso de cerveza del tipo " + vaso.getTipo());
        return vaso;
    }

    public void devolverVaso(VasoCerveza vaso) {
        listaVasos.add(vaso);
        System.out.println(nombre + " recibe de vuelta el vaso tipo " + vaso.getTipo());
    }

    public void contarVasos() {
        System.out.println(nombre + " tiene " + listaVasos.size() + " vasos disponibles: " + listaVasos);
    }
}

public static class ClientesHilo extends Thread {
    private Camarero camarero;
    private double litrosBebidos = 0;

    public ClientesHilo(String nombre, Camarero camarero) {
        super(nombre);
        this.camarero = camarero;
    }

    @Override
    public void run() {
        System.out.println(getName() + " se está ejecutando");

        while (true) {
            try {
                VasoCerveza vaso = camarero.servirCerveza();

                if (vaso != null) {
                    System.out.println(
                        getName() + " está bebiendo una " + 
                        (vaso.getTipo() == 0 ? "media pinta" : "pinta")
                    );

                    if (vaso.getTipo() == 0) {
                        litrosBebidos += 0.28;
                    } else {
                        litrosBebidos += 0.57;
                    }

                    System.out.println(getName() + " lleva bebidos: " +
                        String.format("%.2f", litrosBebidos) + " L");

                    camarero.devolverVaso(vaso);
                }

                Thread.sleep(250 + (int)(Math.random() * 751));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
    public static void main(String[] args) {
        Camarero mou = new Camarero("mou");

        ClientesHilo c1 = new ClientesHilo("Homer", mou);
        ClientesHilo c2 = new ClientesHilo("Barney", mou);
        ClientesHilo c3 = new ClientesHilo("Carl", mou);
        ClientesHilo c4 = new ClientesHilo("Lenny ", mou);
        ClientesHilo c5 = new ClientesHilo("Lurleen ", mou);

        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        Thread t3 = new Thread(c3);
        Thread t4 = new Thread(c4);
        Thread t5 = new Thread(c5);

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
    }
}
