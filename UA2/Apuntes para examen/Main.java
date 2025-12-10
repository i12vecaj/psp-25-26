
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//Clase cerveza, le meto una función to string para saber que vaso es
class VasoCerveza {
    private int id;
    private int tipo;

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
        return "Vaso{id=" + id + ", tipo=" + (tipo == 0 ? "Media Pinta" : "Pinta") + "}";
    }
}




//IMPORTANTE el camarero es el que tiene que crear el vaso porque así puede controlar el servicio

class Camarero {
    private String nombre;
    private List<VasoCerveza> listaVasos;
    private Random rand = new Random();

    public Camarero(String nombre) {
        this.nombre = nombre;
        listaVasos = new ArrayList<>();

        // Crear 3 vasos con tipo aleatorio
        for (int i = 0; i < 3; i++) {
            //rand me permite obtener un número entero ramdom en este caso sería de 0 a 1
            int tipo = rand.nextInt(2);
            listaVasos.add(new VasoCerveza(i, tipo));
        }
    }

    // Método sincronizado para servir cerveza, al tener un objeto tipo camarero, el cliente puede acceder en su hilo a las funciones del camarero
    public synchronized VasoCerveza servirCerveza(String cliente) throws InterruptedException {
        while (listaVasos.isEmpty()) {
            System.out.println(cliente + "El cliente espera porque no hay vasos dinsponibles");
            wait();
        }

        int index = rand.nextInt(listaVasos.size());
        VasoCerveza vaso = listaVasos.remove(index);

        System.out.println(cliente + " tiene el vaso " + vaso);
        return vaso;
    }

    // Método sincronizado para devolver vaso al ser un array de objeto vaso, puedo simplemente devolverlo
    public synchronized void devolverCerveza(VasoCerveza vaso, String cliente) {
        listaVasos.add(vaso);
        System.out.println(cliente + " devuelve el " + vaso);

        //IMPORTANTE notifyall de lo contrario usar notify, solo notifica a uno de los hilos
        notifyAll();
    }

    public synchronized void contarVasos() {
        System.out.println("Vasos disponibles: " + listaVasos);
    }
}



//Mi clase HilosClientes me permite acceder al resto de objetos
class HilosClientes extends Thread {
    private Camarero camarero;
    private double litrosBebidos = 0;

    public HilosClientes(String nombre, Camarero camarero) {
        super(nombre);
        this.camarero = camarero;
    }

    @Override
    public void run() {

        while (true) {

            //NO OLVIDAR TRYCATCH
            try {

                // Creo objeto cerveza para obtener la cerveza que se va ba beber en caso de obtener una
                VasoCerveza vaso = camarero.servirCerveza(getName());

                // Beber la cerveza
                double litros = (vaso.getTipo() == 0 ? 0.284 : 0.568);
                litrosBebidos += litros;

                System.out.println(getName() + " bebe " + litros + " L  | Total: " + litrosBebidos + " L");

                // Como ya tenemos el objeto camarero y ya la bebio aquí puedo devolverla, es el camarero quien lña añade de vuelta en sus funciones
                camarero.devolverCerveza(vaso, getName());

                int min = 250;
                int max = 1000;

                int espera = min + (int)(Math.random() * (max - min));
                Thread.sleep(espera);

            } catch (InterruptedException e) {
                System.out.println(getName() + " ha sido interrumpido.");
                break;
            }
        }
    }
}




public class Main {
    public static void main(String[] args) {

        Camarero camarero = new Camarero("Mou");

        HilosClientes c1 = new HilosClientes("Homer", camarero);
        HilosClientes c2 = new HilosClientes("Barney", camarero);
        HilosClientes c3 = new HilosClientes("Carl", camarero);
        HilosClientes c4 = new HilosClientes("Lenny", camarero);
        HilosClientes c5 = new HilosClientes("Lurleen", camarero);

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
    }
}
