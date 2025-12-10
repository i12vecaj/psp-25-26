public class SimulacionBanco {

    private static void ejecutarSimulacion(String titulo, CuentaCorriente cuenta, double totalIngresosEsperado, boolean sincronizado) {
        System.out.println("-" + titulo + "-");

        double saldoInicial = cuenta.getSaldo();
        System.out.printf("Saldo inicial: %.2f%n", saldoInicial);

        final int NUM_HILOS = 5;
        final double CANTIDAD_INGRESO = 100.0;
        HiloIngreso[] hilos = new HiloIngreso[NUM_HILOS];

        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new HiloIngreso(cuenta, CANTIDAD_INGRESO, "Cliente" + (i + 1), sincronizado);
            hilos[i].start();
        }

        try {
            for (HiloIngreso hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        double saldoFinal = cuenta.getSaldo();
        System.out.println("Resumen");
        System.out.printf("Saldo final: %.2f%n", saldoFinal);
        System.out.printf("Saldo esperado: %.2f%n", totalIngresosEsperado);

        if (Math.abs(saldoFinal - totalIngresosEsperado) < 0.01) {
            System.out.println("Resultado: CORRECTO (Suma correcta).");
        } else {
            System.out.println("Resultado: INCORRECTO (Error de concurrencia).");
        }
    }

    public static void main(String[] args) {
        final double SALDO_INICIAL = 500.0;
        final double TOTAL_ESPERADO = 500.0 + (5 * 100.0);

        CuentaCorriente cuentaSincronizada = new CuentaCorriente(SALDO_INICIAL);
        ejecutarSimulacion(
                "SIMULACIÓN SINCRONIZADA",
                cuentaSincronizada,
                TOTAL_ESPERADO,
                true
        );

        CuentaCorriente cuentaNoSincronizada = new CuentaCorriente(SALDO_INICIAL);
        ejecutarSimulacion(
                "SIMULACIÓN NO SINCRONIZADA",
                cuentaNoSincronizada,
                TOTAL_ESPERADO,
                false
        );
    }
}