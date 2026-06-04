import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class ua2tarea3 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java ua2tarea3 <fichero1.txt> <fichero2.txt> ...");
            System.err.println("Debes indicar al menos un fichero de texto para contar sus caracteres.");
            return;
        }

        Path[] ficheros = new Path[args.length];

        for (int i = 0; i < args.length; i++) {
            ficheros[i] = Path.of(args[i]);

            if (!Files.exists(ficheros[i])) {
                System.err.println("Error: no existe el fichero " + ficheros[i]);
                return;
            }

            if (!Files.isRegularFile(ficheros[i])) {
                System.err.println("Error: la ruta no corresponde a un fichero normal: " + ficheros[i]);
                return;
            }

            if (!Files.isReadable(ficheros[i])) {
                System.err.println("Error: no se puede leer el fichero " + ficheros[i]);
                return;
            }
        }

        long[] resultadosSecuenciales = new long[ficheros.length];
        long[] resultadosConcurrentes = new long[ficheros.length];

        /*
         * FR1: Ejecucion secuencial.
         * El programa recorre la lista de ficheros uno por uno. Hasta que no
         * termina de contar los caracteres de un fichero, no empieza el siguiente.
         */
        long comienzoSecuencial = System.currentTimeMillis();

        for (int i = 0; i < ficheros.length; i++) {
            try {
                resultadosSecuenciales[i] = contarCaracteres(ficheros[i]);
            } catch (IOException e) {
                System.err.println("Error al leer " + ficheros[i] + ": " + e.getMessage());
                return;
            }
        }

        long finSecuencial = System.currentTimeMillis();
        long tiempoSecuencial = finSecuencial - comienzoSecuencial;

        /*
         * FR2: Ejecucion concurrente.
         * Se crea un hilo por fichero. Cada hilo cuenta los caracteres de su
         * propio fichero, por lo que varios conteos pueden avanzar al mismo tiempo.
         */
        HiloContador[] hilos = new HiloContador[ficheros.length];
        long comienzoConcurrente = System.currentTimeMillis();

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new HiloContador(ficheros[i], i, resultadosConcurrentes);
            hilos[i].start();
        }

        for (HiloContador hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error: la espera de " + hilo.getName() + " fue interrumpida.");
                return;
            }

            if (hilo.getErrorLectura() != null) {
                System.err.println("Error al leer " + hilo.getFichero() + ": " + hilo.getErrorLectura().getMessage());
                return;
            }
        }

        long finConcurrente = System.currentTimeMillis();
        long tiempoConcurrente = finConcurrente - comienzoConcurrente;

        mostrarResultados(ficheros, resultadosSecuenciales, resultadosConcurrentes);

        /*
         * FR3: Comparacion de tiempos.
         * La version concurrente puede ser mas rapida con varios ficheros
         * grandes, porque reparte el trabajo entre hilos. Aun asi, el resultado
         * depende del disco, del sistema operativo, del tamano de los archivos
         * y de la sobrecarga de crear y coordinar hilos.
         *
         * Si hay pocos ficheros o son pequenos, la ejecucion concurrente puede
         * tardar igual o incluso mas que la secuencial por esa sobrecarga.
         */
        System.out.println();
        System.out.println("Tiempo secuencial: " + tiempoSecuencial + " ms");
        System.out.println("Tiempo concurrente: " + tiempoConcurrente + " ms");

        if (tiempoConcurrente < tiempoSecuencial) {
            System.out.println("Observacion: la ejecucion concurrente ha sido mas rapida en esta prueba.");
        } else if (tiempoConcurrente > tiempoSecuencial) {
            System.out.println("Observacion: la ejecucion secuencial ha sido mas rapida en esta prueba.");
        } else {
            System.out.println("Observacion: ambas ejecuciones han tardado lo mismo en esta prueba.");
        }
    }

    private static long contarCaracteres(Path fichero) throws IOException {
        /*
         * Se lee como texto UTF-8 y se cuentan caracteres, no bytes.
         * Esto es importante porque algunos caracteres pueden ocupar mas de un byte.
         */
        String contenido = Files.readString(fichero, StandardCharsets.UTF_8);
        return contenido.length();
    }

    private static void mostrarResultados(Path[] ficheros, long[] secuencial, long[] concurrente) {
        System.out.println();
        System.out.println("Fichero | Secuencial | Concurrente");
        System.out.println("----------------------------------");

        for (int i = 0; i < ficheros.length; i++) {
            System.out.println(ficheros[i] + " | " + secuencial[i] + " | " + concurrente[i]);

            if (secuencial[i] != concurrente[i]) {
                System.out.println("Aviso: los resultados no coinciden para " + ficheros[i]);
            }
        }
    }

    private static class HiloContador extends Thread {
        private final Path fichero;
        private final int posicionResultado;
        private final long[] resultados;
        private IOException errorLectura;

        HiloContador(Path fichero, int posicionResultado, long[] resultados) {
            super("Contador-" + fichero.getFileName());
            this.fichero = fichero;
            this.posicionResultado = posicionResultado;
            this.resultados = resultados;
        }

        @Override
        public void run() {
            try {
                resultados[posicionResultado] = contarCaracteres(fichero);
            } catch (IOException e) {
                errorLectura = e;
            }
        }

        Path getFichero() {
            return fichero;
        }

        IOException getErrorLectura() {
            return errorLectura;
        }
    }
}
