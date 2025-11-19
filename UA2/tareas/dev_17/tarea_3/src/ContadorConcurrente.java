import java.io.FileReader;

class ContadorHilo implements Runnable {
    private String nombre;

    public ContadorHilo(String nombre) {
        this.nombre = nombre;
    }

    public void run() {
        int contador = 0;
        try {
            FileReader fr = new FileReader(nombre);
            int c = fr.read();
            while (c != -1) {
                contador++;
                c = fr.read();
            }
            fr.close();
            System.out.println("El fichero " + nombre + " tiene " + contador + " caracteres.");
        } catch (Exception e) {
            System.out.println("Error con el fichero " + nombre);
        }
    }
}

public class ContadorConcurrente {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("Uso: java ContadorConcurrente <archivo1> <archivo2> ...");
            return;
        }

        long start = System.currentTimeMillis();

        Thread[] hilos = new Thread[args.length];
        for(int i=0; i<args.length; i++) {
            hilos[i] = new Thread(new ContadorHilo(args[i]));
            hilos[i].start();
        }

        // Esperar a que terminen todos los hilos
        for(Thread t : hilos) {
            try { t.join(); } catch(Exception e) {}
        }

        long end = System.currentTimeMillis();
        System.out.println("Tiempo total concurrente: " + (end - start) + " ms");
    }
}
