// EJEMPLO. Hilos: Productor - Consumidor
// Basado en: Programación de servicios y procesos - Técnico Superior en DAM. Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.
// @author: dperez@ceslopedevega.com
//
// Compilar y ejecutar: javac ProductorConsumidorSincronizadoFinal.java && java ProductorConsumidorSincronizadoFinal
// Si visualizamos los mensajes de salida en la clase Cola en lugar de los hilos productos y consumidor muestra el consumo correcto en la cola.

class Cola
{
    private int datos = 0;  // Datos a Producir por los Hilos Productores y a Consumir por los Hilos Consumidores
    private boolean disponible = false; // Inicilmente la cola debe estar vacía
        
    public synchronized int get() {
        while(!disponible) {        // Si hay datos ...
            try {
                wait();             // Espero a poder acceder al objeto sincronizado
            } catch(InterruptedException e) {}            
        }
        System.out.println( "Se consume: " + datos);
        disponible = false; // Se pone la cola vacía ...
        notify();           // El objeto sincronizado queda libre
        return datos;       // Se devuelven los datos
    }

    public synchronized void put(int valor) {
        while(disponible) {        // Si hay datos ...
            try {
                wait();         // Espero a poder acceder al objeto sincronizado
            } catch(InterruptedException e) {}        
        }
        datos = valor;          // Coloca valor en la cola
        disponible = true;      // Disponible para consumir
        System.out.println("Se produce: " + datos);
        notify();           // El objeto sincronizado queda libre
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
    private int n;

    public Productor(Cola c, int n) {
        cola = c;
        this.n = n;
    }

    public void run() {        
        for(int i=0; i<5;i++)
        {
            cola.put(i); // Pongo datos            
            //System.out.println(i + " => Productor : "  + n + " produce: " +i);

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
    private int n;

    public Consumidor(Cola c, int n) {
        cola = c;
        this.n = n;
    }

    public void run() { 
        int valor = 0;       
        for(int i=0; i<5;i++)
        {
            valor = cola.get(); // Pongo datos
            //System.out.println(i + " => Consumidor : "  + n + " consume: " + valor);

            try {
                sleep(100);
            }
            catch (InterruptedException e) {}
        }
    }
} // Fin Class Productor


public class ProductorConsumidorSincronizadoFinal {
    public static void main(String[] args) {
        System.out.println("-----------------------------------");
        System.out.println("Hilos: Productor - Consumidor Final");
        System.out.println("-----------------------------------");
        ExecutionTimer timer = new ExecutionTimer();

        Cola cola = new Cola();
        Productor p = new Productor(cola, 1);
        Consumidor c = new Consumidor(cola, 1);        

        timer.start();
        System.out.println("Comienza la ejecución de los hilos ...");
        System.out.println("--------------------------------------");
        
        p.start();        
        c.start();        

        try {
            p.join();
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