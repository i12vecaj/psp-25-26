import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ContadorCaracteres {

 
    public static void main(String[] args) {

 
        if (args.length == 0) {
            System.out.println("ERROR: Debe indicar al menos un fichero.");
            System.out.println("Ejemplo: java ContadorCaracteres quijote.txt texto2.txt");
            return;
        }

     
        System.out.println("\n=== EJECUCIÓN SECUENCIAL ===");

        long t_comienzo = System.currentTimeMillis();

        for (String fichero : args) {
            contarCaracteresSecuencial(fichero);
        }

        long t_fin = System.currentTimeMillis();
        long tiempoSecuencial = t_fin - t_comienzo;

        System.out.println("Tiempo total secuencial: " + tiempoSecuencial + " ms");

      
        System.out.println("\n=== EJECUCIÓN CONCURRENTE (HILOS) ===");

        t_comienzo = System.currentTimeMillis();

        Thread[] hilos = new Thread[args.length];

        for (int i = 0; i < args.length; i++) {
            hilos[i] = new HiloContador(args[i]);
            hilos[i].start();
        }

     
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar al hilo.");
            }
        }

        t_fin = System.currentTimeMillis();
        long tiempoConcurrente = t_fin - t_comienzo;

        System.out.println("Tiempo total concurrente: " + tiempoConcurrente + " ms");

      
        System.out.println("\n=== COMPARACIÓN DE TIEMPOS ===");

        if (tiempoConcurrente < tiempoSecuencial) {
            System.out.println("La ejecución concurrente es más rápida.");
        } else {
            System.out.println("La ejecución secuencial es más rápida.");
        }

        System.out.println("\nDiferencia: " + Math.abs(tiempoSecuencial - tiempoConcurrente) + " ms");
    }

   
    private static void contarCaracteresSecuencial(String nombre) {

        File archivo = new File(nombre);

       
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado: " + nombre);
            return;
        }

        int contador = 0;

        try (FileReader lector = new FileReader(archivo)) {

            while (lector.read() != -1) {
                contador++;
            }

            System.out.println("[" + nombre + "] Caracteres: " + contador);

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + nombre);
        }
    }
}


class HiloContador extends Thread {

    private String fichero;

    public HiloContador(String fichero) {
        this.fichero = fichero;
    }

    @Override
    public void run() {

        File archivo = new File(fichero);

        // Control de errores
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado: " + fichero);
            return;
        }

        int contador = 0;

        try (FileReader lector = new FileReader(archivo)) {

            while (lector.read() != -1) {
                contador++;
            }

            System.out.println("[" + fichero + "] Caracteres (Hilo): " + contador);

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + fichero);
        }
    }
}

