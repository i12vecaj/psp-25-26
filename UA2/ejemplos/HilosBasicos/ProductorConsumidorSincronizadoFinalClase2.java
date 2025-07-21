// EJEMPLO. Hilos: Productor - Consumidor
// Basado en: Programación de servicios y procesos - Técnico Superior en DAM. Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.
// @author: dperez@ceslopedevega.com
//
// Compilar y ejecutar: del *.class && javac ProductorConsumidorSincronizadoFinalClase2.java && java ProductorConsumidorSincronizadoFinalClase2
// Si visualizamos los mensajes de salida en la clase Cola en lugar de los hilos productos y consumidor muestra el consumo correcto en la cola.

class Cola
{
    private int bolitas = 0;  // bolitas a Producir por los Hilos Productores y a Consumir por los Hilos Consumidores
    private boolean disponible = false; // Inicilmente la cola debe estar vacía
        
    public synchronized int get() {
        while(!disponible) {        // Si hay bolitas ...
            try {
                wait();             // Espero a poder acceder al objeto sincronizado
            } catch(InterruptedException e) {}            
        }
        int bolitas_tmp = 0;
        if(bolitas == 6)
        {
            bolitas_tmp = bolitas;
            bolitas = 0;
        }
        System.out.println( "Se consume: " + bolitas_tmp);
        disponible = false; // Se pone la cola vacía ...
        notify();           // El objeto sincronizado queda libre
        return bolitas_tmp;       // Se devuelven los bolitas
    }

    public synchronized int getBolitas() {
              // El objeto sincronizado queda libre
        return bolitas;       // Se devuelven los bolitas
    }

    public synchronized void put(int valor) {
        while(disponible) {        // Si hay bolitas ...
            try {
                wait();         // Espero a poder acceder al objeto sincronizado
            } catch(InterruptedException e) {}        
        }
        bolitas++; // = valor;          // Coloca valor en la cola
        disponible = true;      // Disponible para consumir
        //System.out.println("Se produce: " + bolitas);
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
        for(int i=0; i<10;i++)
        {
            cola.put(i); // Pongo bolitas            
            System.out.println(i + " => Productor : "  + n + " produce: " +cola.getBolitas());

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
        for(int i=0; i<10;i++)
        {
            valor = cola.get(); // Pongo bolitas
            //System.out.println(i + " => Consumidor : "  + n + " consume: " + valor);

            try {
                sleep(100);
            }
            catch (InterruptedException e) {}
        }
    }
} // Fin Class Productor


public class ProductorConsumidorSincronizadoFinalClase2 {
    public static void main(String[] args) {
        System.out.println("-----------------------------------");
        System.out.println("Hilos: Productor - Consumidor Final");
        System.out.println("-----------------------------------");
        ExecutionTimer timer = new ExecutionTimer();

        Cola cola = new Cola();
        Productor p1 = new Productor(cola, 1);
        Productor p2 = new Productor(cola, 2);
        Consumidor c = new Consumidor(cola, 1);        

        timer.start();
        System.out.println("Comienza la ejecución de los hilos ...");
        System.out.println("--------------------------------------");
        
        p1.start();        
        p2.start();        
        c.start();        

        try {
            p1.join();
            p2.join();
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