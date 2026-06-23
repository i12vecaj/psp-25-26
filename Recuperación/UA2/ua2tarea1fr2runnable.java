class ua2tarea1fr2runnable {
    private static final int NUMERO_HILOS = 5;
    private static final int INCREMENTOS_POR_HILO = 1000;

    /*
     * Contador compartido por todos los hilos.
     * Igual que en la version con Thread, se sincroniza el incremento.
     */
    private static int contador = 0;

    public static void main(String[] args) {
        Thread[] hilos = new Thread[NUMERO_HILOS];

        for (int i = 0; i < hilos.length; i++) {
            /*
             * En esta version la tarea se define en una clase que implementa
             * Runnable. Despues se entrega esa tarea a un objeto Thread.
             */
            hilos[i] = new Thread(new IncrementadorRunnable(), "Hilo-Runnable-" + (i + 1));
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
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
         * El resultado debe volver a ser 5000 porque el contador se incrementa
         * dentro de un metodo synchronized.
         *
         * La diferencia con extender Thread esta en el diseno:
         * - Con Thread, la clase representa directamente un hilo.
         * - Con Runnable, la clase representa la tarea y Thread se encarga de
         *   ejecutarla. Esta opcion suele ser mas flexible porque separa mejor
         *   la tarea del mecanismo de ejecucion.
         */
        if (contador == resultadoEsperado) {
            System.out.println("Observacion: el resultado coincide usando Runnable y synchronized.");
        } else {
            System.out.println("Observacion: resultado no esperado; revisa la sincronizacion.");
        }
    }

    private static synchronized void incrementarContador() {
        contador++;
    }

    private static class IncrementadorRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS_POR_HILO; i++) {
                incrementarContador();
            }
        }
    }
}
