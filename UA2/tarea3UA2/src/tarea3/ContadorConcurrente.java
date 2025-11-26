package tarea3;

public class ContadorConcurrente {

    public static <ContadorHilo> void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            System.out.println("Uso: java tarea3.ContadorConcurrente <fichero1> <fichero2> ...");
            return;
        }

        long t_comienzo, t_fin;
        t_comienzo = System.currentTimeMillis();

        ContadorHilo[] hilos = new ContadorHilo[args.length];

        for (int i = 0; i < args.length; i++) {
            hilos[i] = new ContadorHilo(args[i]);
            hilos[i].start();
        }

        long totalCaracteres = 0;

        for (ContadorHilo hilo : hilos) {
            hilo.join();
            if (hilo.getCaracteres() != -1) {
                System.out.println("Fichero: " + hilo.getNombreFichero() + ", Caracteres: " + hilo.getCaracteres());
                totalCaracteres += hilo.getCaracteres();
            }
        }

        t_fin = System.currentTimeMillis();
        long t_total = t_fin - t_comienzo;

        System.out.println("---");
        System.out.println("Total de caracteres de todos los ficheros: " + totalCaracteres);
        System.out.println("Tiempo de ejecucion concurrente (ms): " + t_total);
    }
}