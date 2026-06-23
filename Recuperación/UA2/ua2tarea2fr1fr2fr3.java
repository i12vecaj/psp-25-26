import java.util.concurrent.ThreadLocalRandom;

class ua2tarea2fr1fr2fr3 {
    private static final int SALDO_INICIAL = 1000;
    private static final int[] INGRESOS = {300, 500, 700, 200, 400};

    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(SALDO_INICIAL);

        /*
         * El objeto cuenta es compartido por todos los hilos. Todos ingresan
         * dinero sobre el mismo saldo, por eso es necesario sincronizar el
         * metodo que modifica la cuenta.
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
        System.out.println("Saldo esperado: " + saldoEsperado + " euros");
        System.out.println("Saldo final obtenido: " + saldoFinal + " euros");

        /*
         * Documentacion del resultado:
         * En este apartado el saldo final debe coincidir con el saldo esperado.
         * El metodo ingresar() esta declarado como synchronized, de modo que
         * solo un hilo puede leer, modificar y guardar el saldo cada vez.
         */
        if (saldoFinal == saldoEsperado) {
            System.out.println("Observacion: el saldo coincide porque los ingresos estan sincronizados.");
        } else {
            System.out.println("Observacion: resultado no esperado; revisa la sincronizacion.");
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
         * synchronized protege la seccion critica: leer el saldo anterior,
         * calcular el nuevo saldo y guardarlo. Asi se evita que dos hilos
         * trabajen al mismo tiempo con el mismo saldo previo.
         */
        synchronized void ingresar(String nombre, int cantidad) {
            int saldoPrevio = getSaldo();
            int saldoNuevo = saldoPrevio + cantidad;

            setSaldo(saldoNuevo);

            System.out.println(nombre + " ingresa " + cantidad + " euros.");
            System.out.println("Saldo previo: " + saldoPrevio + " euros");
            System.out.println("Saldo final: " + saldoNuevo + " euros");
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
