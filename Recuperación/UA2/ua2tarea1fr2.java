class ua2tarea1fr2 {
    private static final int NUMERO_HILOS = 5;
    private static final int INCREMENTOS_POR_HILO = 1000;

    /*
     * Contador compartido por todos los hilos.
     * En este segundo apartado se protege el acceso mediante synchronized.
     */
    private static int contador = 0;

    public static void main(String[] args) {
        IncrementadorThread[] hilos = new IncrementadorThread[NUMERO_HILOS];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new IncrementadorThread("Hilo-Thread-" + (i + 1));
            hilos[i].start();
        }

        for (IncrementadorThread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error: la espera de " + hilo.getName() + " fue interrumpida.");
                return;
            }
        }

        int resultadoEsperado = NUMERO_HILOS * INCREMENTOS_POR_HILO;

        System.out.println("Resultado esperado: " + resultadoEsperado);
        System.out.println("Resultado obtenido: " + contador);

        /*
         * Documentacion del resultado:
         * Aqui se obtiene 5000 porque el metodo incrementarContador() esta
         * sincronizado. synchronized obliga a que solo un hilo pueda ejecutar
         * ese bloque critico al mismo tiempo, evitando que se pierdan sumas.
         *
         * Esta version crea los hilos extendiendo la clase Thread. La tarea
         * que realiza cada hilo esta dentro del metodo run() de la propia clase.
         */
        if (contador == resultadoEsperado) {
            System.out.println("Observacion: el resultado coincide gracias a la sincronizacion.");
        } else {
            System.out.println("Observacion: resultado no esperado; revisa la sincronizacion.");
        }
    }

    private static synchronized void incrementarContador() {
        contador++;
    }

    private static class IncrementadorThread extends Thread {
        IncrementadorThread(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS_POR_HILO; i++) {
                incrementarContador();
            }
        }
    }
}
