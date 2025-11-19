public class MainConcurrente {
    public static void main(String[] args) {
        if (args.length == 0) {
            // Si no se proporcionan argumentos, usar ficheros por defecto
            String[] ficherosPorDefecto = {
                "DONQUIJOTEDELAMANCHA.txt",
                "dracula.txt", 
            };
            
            System.out.println("No se proporcionaron ficheros como argumentos. Usando ficheros por defecto:");
            for (String fichero : ficherosPorDefecto) {
                System.out.println(" - " + fichero);
            }
            System.out.println();
            
            ejecutarConcurrentemente(ficherosPorDefecto);
        } else {
            // Usar los ficheros proporcionados como argumentos
            ejecutarConcurrentemente(args);
        }
    }
    
    private static void ejecutarConcurrentemente(String[] nombresFicheros) {
        System.out.println("Iniciando procesamiento concurrente de " + nombresFicheros.length + " ficheros...\n");
        
        long inicio = System.currentTimeMillis();
        Thread[] hilos = new Thread[nombresFicheros.length];

        // Crear e iniciar todos los hilos
        for (int i = 0; i < nombresFicheros.length; i++) {
            hilos[i] = new LectorFichero(nombresFicheros[i]);
            hilos[i].start();
            System.out.println("Hilo iniciado para: " + nombresFicheros[i]);
        }

        // Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error al esperar por los hilos: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        
        long fin = System.currentTimeMillis();
        System.out.println("\nProcesamiento completado en " + (fin - inicio) + " ms");
    }
}
