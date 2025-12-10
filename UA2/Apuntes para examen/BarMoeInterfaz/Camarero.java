//IMPORTANTE el camarero es el que tiene que crear el vaso porque así puede controlar el servicio

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Camarero {
    private String nombre;
    private List<VasoCerveza> listaVasos;
    private Random rand = new Random();

    public Camarero(String nombre) {
        this.nombre = nombre;
        listaVasos = new ArrayList<>();

        // Crear 3 vasos con tipo aleatorio
        for (int i = 0; i < 3; i++) {
            int tipo = rand.nextInt(2);
            listaVasos.add(new VasoCerveza(i, tipo));
        }
    }

    public synchronized VasoCerveza servirCerveza(String cliente) throws InterruptedException {
        while (listaVasos.isEmpty()) {
            System.out.println(cliente + "El cliente espera porque no hay vasos disponibles");
            wait();
        }

        int index = rand.nextInt(listaVasos.size());
        VasoCerveza vaso = listaVasos.remove(index);

        System.out.println(cliente + " tiene el vaso " + vaso);
        return vaso;
    }

    public synchronized void devolverCerveza(VasoCerveza vaso, String cliente) {
        listaVasos.add(vaso);
        System.out.println(cliente + " devuelve el " + vaso);
        notifyAll();
    }

    public synchronized void contarVasos() {
        System.out.println("Vasos disponibles: " + listaVasos);
    }
}