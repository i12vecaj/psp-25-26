import java.util.concurrent.ThreadLocalRandom;

class ua2tarea2fr4 {
    private static final int SALDO_INICIAL = 1000;
    private static final int[] INGRESOS = {300, 500, 700, 200, 400};

    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(SALDO_INICIAL);

        /*
         * Este programa repite el mismo caso que la version sincronizada,
         * pero el metodo ingresar() no usa synchronized. La cuenta sigue siendo
         * compartida por todos los hilos, asi que aparece riesgo de carrera.
         */
        System.out.println("Saldo inicial: " + cuenta.getSaldo() + " euros");

        HiloIngreso[] hilos = new HiloIngreso[INGRESOS.length];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new HiloIngreso(cuenta, "Cliente-" + (i + 1), INGRESOS[i]);
            hilos[i].start();
        }

        for (HiloIngreso hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error: la espera de " + hilo.getName() + " fue interrumpida.");
                return;
            }
        }

        int saldoEsperado = SALDO_INICIAL;
        for (int ingreso : INGRESOS) {
            saldoEsperado += ingreso;
        }

        int saldoFinal = cuenta.getSaldo();

        System.out.println();
        System.out.println("Saldo esperado si no se pierden ingresos: " + saldoEsperado + " euros");
        System.out.println("Saldo final obtenido: " + saldoFinal + " euros");

        /*
         * Diferencia observada respecto al apartado 3:
         * Al quitar synchronized, varios hilos pueden leer el mismo saldo
         * previo antes de que otro hilo guarde su ingreso. Cuando cada uno
         * escribe su resultado, puede sobrescribir el cambio realizado por otro.
         *
         * Por eso el saldo final puede ser inferior al esperado. Si en alguna
         * ejecucion coincide, no significa que el programa sea correcto, sino
         * que la condicion de carrera no se ha manifestado en esa prueba.
         */
        if (saldoFinal == saldoEsperado) {
            System.out.println("Observacion: esta vez coincide, pero sigue existiendo riesgo de condicion de carrera.");
        } else {
            System.out.println("Observacion: el saldo difiere porque se han perdido actualizaciones entre hilos.");
        }
    }

    private static class CuentaCorriente {
        private int saldo;

        CuentaCorriente(int saldoInicial) {
            this.saldo = saldoInicial;
        }

        int getSaldo() {
            dormirAleatoriamente("consultar el saldo");
            return saldo;
        }

        void setSaldo(int saldo) {
            dormirAleatoriamente("actualizar el saldo");
            this.saldo = saldo;
        }

        /*
         * Este metodo modifica el saldo compartido sin synchronized. La lectura
         * y la escritura del saldo no quedan protegidas como una unica operacion.
         */
        void ingresar(String nombre, int cantidad) {
            int saldoPrevio = getSaldo();
            int saldoNuevo = saldoPrevio + cantidad;

            setSaldo(saldoNuevo);

            System.out.println(nombre + " ingresa " + cantidad + " euros.");
            System.out.println("Saldo previo leido: " + saldoPrevio + " euros");
            System.out.println("Saldo final escrito: " + saldoNuevo + " euros");
            System.out.println();
        }

        private void dormirAleatoriamente(String operacion) {
            int milisegundos = ThreadLocalRandom.current().nextInt(250, 2001);

            try {
                Thread.sleep(milisegundos);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error: hilo interrumpido al " + operacion + ".");
            }
        }
    }

    private static class HiloIngreso extends Thread {
        private final CuentaCorriente cuenta;
        private final int cantidad;

        HiloIngreso(CuentaCorriente cuenta, String nombre, int cantidad) {
            super(nombre);
            this.cuenta = cuenta;
            this.cantidad = cantidad;
        }

        @Override
        public void run() {
            cuenta.ingresar(getName(), cantidad);
        }
    }
}
