// EJEMPLO. Hilos: Bloques NO Sincronizados
// Basado en: Programación de servicios y procesos - Técnico Superior en DAM. Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.
// @author: dperez@ceslopedevega.com
//
// Compilar y ejecutar: javac BloquesNoSincronizados.java && java BloquesNoSincronizados


class Contador
{
    private int c = 0;  // Atributo Contador
    Contador (int c) { 
        this.c = c;
    }

    public void incrementa() {
        c++;
    }

    public void decrementa() {
        c--;
    }

    public int valor() {
        return c;
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

class HiloSumador extends Thread 
{
    private Contador contador;

    public HiloSumador(String nombre, Contador c) {
        setName(nombre);
        contador = c;
    }

    public void run() {
        for(int j=0; j<300;j++) // TODO: Cambia a 3000 en ambos a ver qué ocurre ...
        {
            contador.incrementa();
            /*
            try {
                sleep(100);
            }
            catch (InterruptedException e) {}
            */
        }
        System.out.println(getName() + " - contador vale " + contador.valor());
    }
} // Fin Class HiloSumador

class HiloRestador extends Thread 
{
    private Contador contador;

    public HiloRestador(String nombre, Contador c) {
        setName(nombre);
        contador = c;
    }

    public void run() {
        for(int j=0; j<300;j++) // TODO: Cambia a 3000 en ambos a ver qué ocurre ...
        {
            contador.decrementa();
            /*
            try {
                sleep(100);
            }
            catch (InterruptedException e) {}
            */
        }
        System.out.println(getName() + " - contador vale " + contador.valor());
    }
} // Fin Class HiloRestador


public class BloquesNoSincronizados {
    public static void main(String[] args) {
        System.out.println("-------------------------------");
        System.out.println("Hilos: Bloques NO Sincronizados");
        System.out.println("-------------------------------");
        ExecutionTimer timer = new ExecutionTimer();

        Contador cont = new Contador(100);
        HiloSumador hiloSuma = new HiloSumador("Hilo Sumador", cont);
        HiloRestador hiloResta = new HiloRestador("Hilo Restador", cont);

        timer.start();
        System.out.println("Comienza la ejecución de los hilos ...");
        System.out.println("--------------------------------------");
        hiloSuma.start();
        hiloResta.start();

        try {
            hiloSuma.join();
            hiloResta.join();
        }
        catch (InterruptedException e)
        {
            // Nothing to do here ...
        }

        System.out.println("--------------------------------------");
        System.out.println("... Finaliza la ejecución de los hilos");
        System.out.println("--------------------------------------");
        System.out.println("Valor Final del Contador: " + cont.valor());
        System.out.println("--------------------------------------");
        timer.stop();
        timer.printElapsedTime();
    }
}