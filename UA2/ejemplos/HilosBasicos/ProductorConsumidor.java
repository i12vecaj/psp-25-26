// EJEMPLO. Hilos: Productor - Consumidor
// Basado en: Programación de servicios y procesos - Técnico Superior en DAM. Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.
// @author: dperez@ceslopedevega.com
//
// Compilar y ejecutar: javac ProductorConsumidor.java && java ProductorConsumidor


class Cola
{
    private int datos = 0;  // Datos a Producir por los Hilos Productores y a Consumir por los Hilos Consumidores
    private boolean disponible = false; // Inicilmente la cola debe estar vacía
        
    public int get() {
        if(disponible) {        // Si hay datos ...
            disponible = false; // Se pone la cola vacía ...
            return datos;       // Se devuelven los datos
        }
        else {                  // Si no hay disponible, cola vacía
            return -1;
        }
    }

    public void put(int valor) {
        datos = valor;          // Coloca valor en la cola
        disponible = true;      // Disponible para consumir
    }
    
} // Fin Class Contador


class ExecutionTimer
{
    private long startTime = 0;
    private long endTime = 0;
    private long timeElapsed = 0;
        
    public void start() {
        startTime = System.nanoTime();
    }
    
    public void stop() {
        endTime = System.nanoTime();
    }

    public void elapsedTime() {
        timeElapsed = endTime - startTime;
    }

    public void printElapsedTime()    
    {
        elapsedTime();
        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
    }
} // Fin Class ExecutionTimer

class Productor extends Thread 
{
    private Cola cola;
    private int idProductor;

    public Productor(Cola c, int n) {
        cola = c;
        this.idProductor = n;
    }

    public void run() {        
        for(int i=0; i<5;i++)
        {
            cola.put(i); // Pongo datos
            System.out.println("Productor : "  + idProductor + " en Iteración: " + i + " produce: " + i);

            try {
                sleep(100);
            }
            catch (InterruptedException e) {}
        }
    }
} // Fin Class Productor

class Consumidor extends Thread 
{
    private Cola cola;
    private int idConsumidor;

    public Consumidor(Cola c, int n) {
        cola = c;
        this.idConsumidor = n;
    }

    public void run() { 
        int valor = 0;       
        for(int i=0; i<5;i++)
        {
            valor = cola.get(); // Pongo datos
            System.out.println("Consumidor : "  + idConsumidor + " en Iteración: " + i + " consume: " + valor);

            try {
                sleep(100);
            }
            catch (InterruptedException e) {}
        }
    }
} // Fin Class Productor


public class ProductorConsumidor {
    public static void main(String[] args) {
        System.out.println("-----------------------------");
        System.out.println("Hilos: Productor - Consumidor");
        System.out.println("-----------------------------");
        ExecutionTimer timer = new ExecutionTimer();

        Cola cola = new Cola();
        Productor p1 = new Productor(cola, 1);
        //Productor p2 = new Productor(cola, 2);
        //Productor p3 = new Productor(cola, 3);
        Consumidor c = new Consumidor(cola, 1);

        timer.start();
        System.out.println("Comienza la ejecución de los hilos ...");
        System.out.println("--------------------------------------");
        
        p1.start();
        //p2.start();
        //p3.start();
        c.start();

        try {
            p1.join();
            //p2.join();
            //p3.join();
            c.join();
        }
        catch (InterruptedException e)
        {
            // Nothing to do here ...
        }

        System.out.println("--------------------------------------");
        System.out.println("... Finaliza la ejecución de los hilos");
        System.out.println("--------------------------------------");
        
        timer.stop();
        timer.printElapsedTime();
    }
}