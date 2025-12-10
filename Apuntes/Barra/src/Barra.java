import java.util.ArrayList;
import java.util.List;

public class Barra {

    private final int CAPACIDAD_MAXIMA = 3; //Cantidad maxima
    private final List<String>vasos; // Guardamos las cervezas que se van sirviendo


    //Constructor
    public Barra() {
        this.vasos = new ArrayList<>();
    }

    // Productor de cervezas
    public synchronized void ponerCerveza(String cerveza) {

        while(vasos.size() >= CAPACIDAD_MAXIMA) {
            try {
                System.out.println("Barra llena, esperamos que se sirva");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Si queda hueco en la barra, sirve la cerveza
        vasos.add(cerveza);
        System.out.println("Camarero servicio: " + cerveza + "Total cerceas: " + vasos.size());

        //Notifica a todos que hay disponbilidad
        notifyAll();
    }
    public synchronized String beberCervezas(String nombreCliente) {

        // Si no hay vasos
        while(vasos.isEmpty()) {
            try {
                System.out.println(nombreCliente + "esperamos que se sirva");
                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        // Si llega aquí, es que hay cerveza. La coge (siempre la 0, la primera).
        String cerveza = vasos.remove(0);
        System.out.println(nombreCliente + " se bebió: " + cerveza + " (Quedan: " + vasos.size() + ")");


        // Avisa al camarero: "¡He dejado un hueco libre!"
        notifyAll();

        return cerveza;
    }
}
