package BarMoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
FR4 [2 puntos]. Crea una Aplicación (main) que genere un Camarero, de nombre Mou y creará los siguientes
Clientes: Homer, Barney, Carl, Lenny y Lurleen. Cada Cliente recibirá el objeto compartido Camarero.
Adicionalmente, se deberá sincronizar el uso del objeto compartido y se deberán realizar varias pruebas de ejecución
 para garantizar que el sistema implementado funciona correctamente.
 */

public class Main {
    public static void main(String[] args) {
        Camarero mou = new Camarero(new ArrayList<>(), "Mou");

        //Crear clientes con el mismo camarero compartido
        HilosClientes homer   = new HilosClientes("Homer", mou, 0);
        HilosClientes barney  = new HilosClientes("Barney", mou, 0);
        HilosClientes carl    = new HilosClientes("carl", mou, 0);
        HilosClientes lenny   = new HilosClientes("Lenny", mou, 0);
        HilosClientes lurleen = new HilosClientes("lurleen", mou, 0);

        //Lanza los hilos
        homer.start();
        barney.start();
        carl.start();
        lenny.start();
        lurleen.start();
    }
}

/*
R1 [2 puntos]. Clase VasoCerveza: representa el elemento que será consumido por los Clientes y preparado por los Camareros.

1.1. Atributos:
id: int (identificador del vaso) - Valores aceptados: 0,1,2,3, ...
tipo: int - Valores aceptados: 0 media pinta, 1 pinta (https://es.wikipedia.org/wiki/Pinta)
1.2. Métodos:
Constructor
Getters y setters
toString
*/

class VasoCerveza{
    private int id;
    private int tipo; //Tipo del vaso (0 media pinta, 1 pinta)

    public VasoCerveza(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized int getTipo() {
        return tipo;
    }

    public synchronized void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "VasoCerveza{" +
                "id=" + id +
                ", tipo=" + tipo +
                '}';
    }
}

/*FR2 [2 puntos]. Clase Camarero: simula la persona encargada de servir y devolver vasos de cerveza.
Deberá recibir un nombre como argumento en el constructor.


2.1. Atributos:
listaVasos: lista que contendrá los Vasos de Cerveza.
2.2. Métodos:
-Constructor: deberá crear 3 vasos (de tipo aleatorio (0 o 1)) y añadirlos a la lista así como asignarse a si mismo un nombre.
-servirCerveza: elegirá un vaso aleatoriamente de la lista, lo sacará de ella y lo entregará al cliente para que pueda beber su cerveza.
-devolverCerveza: inserta de nuevo en la lista el vaso devuelto.
-contarVasos: imprime los vasos disponibles en el bar

 */
class Camarero{
    private List<VasoCerveza> listaVasos;
    private String nombre;

    public Camarero(List<VasoCerveza> listaVasos, String nombre) {
        this.listaVasos = new ArrayList<>();
        this.nombre = nombre;

        Random random = new Random();

        //Crea tres vasos de tipo aleatorio y los añade a la lista
        for (int i = 0; i<3;i++){
            int tipoNum = random.nextInt(2);
            listaVasos.add(new VasoCerveza(i,tipoNum)); //Se añaden a la lista de vasos, se le pasa el id y el tipo
        }
    }


    public synchronized VasoCerveza servirCerveza(){
        while (listaVasos.isEmpty()){// Si no hay vasos disponibles, espera hasta que se devuelva alguno
            try {
                System.out.println("No hay vasos disponibles, espera...");
                wait(); //esperar hasta que alguien devuelva un vaso
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Random random = new Random();
        int indiceLista = random.nextInt(listaVasos.size());
        VasoCerveza vaso = listaVasos.remove(indiceLista);
        System.out.println("El camarero " + nombre + " sirve " + vaso);
        return vaso;
    }

    //Recibe un vaso devuelto por el cliente
    public synchronized void devolverCerveza(VasoCerveza vaso){
        if(vaso == null){
            System.out.println(nombre+" recibe vaso nulo y no lo acepta");
            return;
        }
        listaVasos.add(vaso);
        System.out.println("Recibe devuelta "+vaso);
        notifyAll();//Notifica que el vaso se ha devuelto y se puede pedir con ese vaso
    }

    //Imprime por pantalla los vasos disponibles en el bar
    public synchronized void contarVasos(){
        System.out.println("== Vasos disponibles en el bar ==");
        for (VasoCerveza vaso : listaVasos){
            System.out.println("\n=== "+vaso);
        }
    }

}

/*
FR3 [2 puntos]. Clase HilosClientes: que extienda Thread. Deberá recibir un nombre como argumento en el constructor
y asignarlo al hilo usando la función adecuada. El metodo run() deberá implementar el siguiente algoritmo:

Indicar que el hilo está ejecutándose
Infinitamente repetir:
Pedir un vaso de Cerveza
Beber la rica y deliciosa Cerveza
Ir contabilizando la cantidad total de cerveza bebida (en LITROS)
Devolver el vaso de Cerveza
Esperar antes de pedir otra Cerveza (dormir al cliente un tiempo aleatorio entre 250 ms y 1000 ms)
 */

//Representa a un cliente que consume cerveza en el bar
class HilosClientes extends Thread{
    private String nombre;
    private Camarero camarero;
    private double cantidadLitros;

    public HilosClientes(String nombre, Camarero camarero, double cantidadLitros) {
        super(nombre); //Se asigna el nombre al hilo
        this.nombre = nombre;
        this.camarero = camarero;
        this.cantidadLitros = cantidadLitros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Camarero getCamarero() {
        return camarero;
    }

    public void setCamarero(Camarero camarero) {
        this.camarero = camarero;
    }

    public double getCantidadLitros() {
        return cantidadLitros;
    }

    public void setCantidadLitros(double cantidadLitros) {
        this.cantidadLitros = cantidadLitros;
    }

    @Override
    public String toString() {
        return "HilosClientes{" +
                "nombre='" + nombre + '\'' +
                ", camarero=" + camarero +
                ", cantidadLitros=" + cantidadLitros +
                '}';
    }

    @Override
    public void run(){
        System.out.println("El hilo se esta ejecutando");
        Random random = new Random();
        while (true){
            //Primero pedir vaso
            VasoCerveza vaso = camarero.servirCerveza();

            //Segundo se bebe la cerveza
            double litros = (vaso.getTipo()==0)?0.25:0.5;
            cantidadLitros += litros;
            System.out.println(nombre+" bebe su cerveza ("+litros+"L). Total: "+cantidadLitros+"L");

            //TErcero devolver vaso
            camarero.devolverCerveza(vaso);

            //Despues se espera entre 250 y 1000 ms
            try {
                Thread.sleep(250 + random.nextInt(751));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            
        }
    }
}
