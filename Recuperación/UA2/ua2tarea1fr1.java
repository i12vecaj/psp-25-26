class ua2tarea1fr1 {
    private static final int NUMERO_HILOS = 5;
    private static final int INCREMENTOS_POR_HILO = 1000;

    /*
     * Esta variable contador es compartida por todos los hilos.
     * En este primer apartado no se sincroniza su acceso para observar
     * que ocurre cuando varios hilos modifican el mismo dato a la vez.
     */
    private static int contador = 0;

    public static void main(String[] args) {
        Thread[] hilos = new Thread[NUMERO_HILOS];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new Incrementador(), "Hilo-" + (i + 1));
            hilos[i].start();
        }

        /*
         * join() hace que el hilo principal espere a que terminen todos los
         * hilos incrementadores antes de mostrar el resultado final.
         */
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
         * Reflexion:
         * Lo esperado seria obtener 5000, porque 5 hilos incrementan 1000 veces.
         * Sin embargo, contador++ no es una operacion atomica: internamente lee
         * el valor, suma 1 y vuelve a guardar el resultado. Si dos hilos hacen
         * esto al mismo tiempo, se pueden perder incrementos.
         *
         * Por eso el resultado puede ser menor que 5000. Si alguna ejecucion
         * muestra 5000, no significa que el programa este bien sincronizado,
         * sino que la condicion de carrera no se ha manifestado en esa prueba.
         */
        if (contador == resultadoEsperado) {
            System.out.println("Observacion: esta vez coincide, pero sigue existiendo riesgo de condicion de carrera.");
        } else {
            System.out.println("Observacion: no coincide por falta de sincronizacion entre hilos.");
        }
    }

    private static class Incrementador implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS_POR_HILO; i++) {
                contador++;
            }
        }
    }
}
