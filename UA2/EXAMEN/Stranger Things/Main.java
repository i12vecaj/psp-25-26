/*//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Productor / Consumidor – “Stranger Things”
        En Hawkins, el Laboratorio está analizando criaturas del Upside Down.
        Para ello, debes implementar el patrón Productor/Consumidor:
El Productor será Eleven, que usa sus poderes para cerrar portales que se abren aleatoriamente.
Cada vez que cierra uno, genera un “evento” (por ejemplo: Demogorgon detectado, Portal inestable,
etc.) y lo deposita en un buffer compartido.
El Consumidor será el Laboratorio de Hawkins, que recoge estos eventos del buffer y los procesa.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Requisitos
Crea un buffer limitado (por ejemplo, de tamaño 5).
Implementa las clases Productor y Consumidor, cada una como un hilo.
Usa wait(), notify() o notifyAll() para gestionar:
El productor espera si el buffer está lleno.
El consumidor espera si el buffer está vacío.
Muestra por pantalla los mensajes clave:
Eleven genera un evento.
El Laboratorio procesa un evento.
Esperas por buffer lleno/vacío.
El programa debe finalizar después de producir y consumir un número determinado de eventos (por ejemplo, 20).
        - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Debes entregar el ejercicio, dentro de UD2, en tu rama, carpeta "EXAMEN" ->  carpeta "Stranger Things".
*/


//Debes entregar el ejercicio, dentro de UD2, en tu rama, carpeta "EXAMEN" ->  carpeta "Stranger Things".   PERO NO DICE NADA MÁS INTERPRETO QUE DEJO EL MAIN





import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {


        Eleven eleven = new Eleven();

        Lavoratorio lab1 = new Lavoratorio("Laboratorio 1", eleven);
        Lavoratorio lab2 = new Lavoratorio("Laboratorio 2", eleven);
        Lavoratorio lab3 = new Lavoratorio("Laboratorio 3", eleven);
        Lavoratorio lab4 = new Lavoratorio("Laboratorio 4", eleven);
        Lavoratorio lab5 = new Lavoratorio("Laboratorio 5", eleven);


        lab1.start();
        lab2.start();
        lab3.start();
        lab4.start();
        lab5.start();




    }
}



//EVENTO
class Evento {
    private int id;
    private int tipo;

    public Evento(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "Evento{id del evento=" + id + ", tipo de evento=" + (tipo == 0 ? "Demogorgon detectado" : "Portal inestable") + "}";
    }
}


//PRODUCTOR ELEVEN
class Eleven {
    private List<Evento> listaEventos;
    private Random rand = new Random();

    public Eleven() {
        listaEventos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int tipo = rand.nextInt(2);
            listaEventos.add(new Evento(i, tipo));
        }
    }



    public synchronized Evento crearEvento(String lab) throws InterruptedException {
        while (listaEventos.isEmpty()) {
            System.out.println(lab + " solicita a Eleven pero está ocupado con los eventos ya detectados");
            wait();
        }

        int index = rand.nextInt(listaEventos.size());
        Evento evento = listaEventos.remove(index);

        System.out.println("Eleven está trabajando en "+lab + " con el evento " + evento);
        return evento;
    }



    public synchronized void devolverEvento(Evento evento, String lab) {
        listaEventos.add(evento);
        System.out.println("En el "+lab + " Eleven soluciona el evento " + evento);

        notifyAll();
    }

    public synchronized void contarEventos() {
        System.out.println("Eventos a detectar disponibles: " + listaEventos.size());
    }


}







//Laboratorio
class Lavoratorio extends Thread {
    private Eleven eleven;

    public Lavoratorio(String nombre, Eleven eleven) {
        super(nombre);
        this.eleven = eleven;
    }

    @Override
    public void run() {



        for (int i = 0; i < 20; i++) {
            try {

                Evento evento = eleven.crearEvento(getName());

                System.out.println(getName() + " tiene el evento " + evento.getTipo());

                eleven.devolverEvento(evento, getName());

                int min = 250;
                int max = 1000;

                int espera = min + (int)(Math.random() * (max - min));
                Thread.sleep(espera);

            } catch (InterruptedException e) {
                System.out.println(getName() + " ha sido interrumpido. "+ e.getMessage());
            }
        }}
}




