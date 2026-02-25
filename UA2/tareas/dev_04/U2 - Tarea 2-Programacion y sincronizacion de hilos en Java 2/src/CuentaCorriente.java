import java.util.Random;

/**
 * FR1: Clase CuentaCorriente. Gestiona el saldo y la lógica de ingreso.
 * Contiene métodos que simulan la latencia con Thread.sleep().
 */
class CuentaCorriente {

    // Atributo que indica el saldo de la cuenta.
    private double saldo;
    private final Random random = new Random();

    /**
     * Constructor que le da un valor inicial al saldo.
     * @param saldoInicial Valor inicial del saldo.
     */
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // --- Métodos Setter y Getter con sleep aleatorio ---

    /**
     * Getter para el saldo. Añade un sleep aleatorio para simular latencia.
     * @return El saldo actual.
     */
    public double getSaldo() {
        simularLatencia();
        return saldo;
    }

    /**
     * Setter para el saldo. Añade un sleep aleatorio para simular latencia.
     * @param nuevoSaldo El nuevo valor del saldo.
     */
    public void setSaldo(double nuevoSaldo) {
        simularLatencia();
        this.saldo = nuevoSaldo;
    }

    /**
     * Simula una latencia de operación durmiendo al hilo.
     * El tiempo de sueño oscila entre 250 y 2000 milisegundos.
     */
    private void simularLatencia() {
        try {
            // Calcula un tiempo de sueño entre 250 y 2000 ms.
            long tiempoSueño = 250 + random.nextInt(1751); // 1751 = 2000 - 250 + 1
            Thread.sleep(tiempoSueño);
        } catch (InterruptedException e) {
            // Control de errores básico (FR5): Manejo de interrupción.
            System.err.println("El hilo " + Thread.currentThread().getName() + " fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    // --- Lógica de Ingreso de Saldo ---

    /**
     * FR1: Método para añadir una cantidad al saldo. Está SINCRONIZADO.
     * Garantiza que solo un hilo a la vez pueda modificar el saldo (sección crítica).
     * @param cantidad Cantidad a ingresar.
     * @param nombreHilo Nombre del hilo que realiza el ingreso.
     */
    public synchronized void ingresarSaldoSincronizado(double cantidad, String nombreHilo) {
        // Al ser 'synchronized', solo un hilo puede ejecutar este bloque a la vez.
        // Esto previene la Condición de Carrera.

        System.out.printf("[%s - Sincronizado] Intentando ingreso...\n", nombreHilo);

        double saldoPrevio = this.saldo; // Lectura del saldo
        simularLatencia(); // Pausa simulada después de leer

        double saldoFinal = saldoPrevio + cantidad;
        simularLatencia(); // Pausa simulada antes de escribir

        this.saldo = saldoFinal; // Escritura del saldo

        // Indicando por pantalla el estado.
        System.out.printf("[%s - Sincronizado] Ingreso OK. Saldo previo: %.2f €, Saldo final: %.2f €.\n",
                nombreHilo, saldoPrevio, saldoFinal);
    }

    /**
     * FR4: Método para añadir una cantidad al saldo. NO está sincronizado.
     * Permite que múltiples hilos accedan simultáneamente a la sección crítica.
     * @param cantidad Cantidad a ingresar.
     * @param nombreHilo Nombre del hilo que realiza el ingreso.
     */
    public void ingresarSaldoNoSincronizado(double cantidad, String nombreHilo) {
        // Al NO ser 'synchronized', múltiples hilos pueden ejecutar este bloque a la vez.
        // Esto provoca una Condición de Carrera.

        System.out.printf("[%s - NO Sincronizado] Intentando ingreso...\n", nombreHilo);

        double saldoPrevio = this.saldo; // Lectura del saldo
        simularLatencia(); // Pausa simulada después de leer

        double saldoFinal = saldoPrevio + cantidad;
        simularLatencia(); // Pausa simulada antes de escribir

        this.saldo = saldoFinal; // Escritura del saldo

        // Indicando por pantalla el estado.
        System.out.printf("[%s - NO Sincronizado] Ingreso OK. Saldo previo: %.2f €, Saldo final: %.2f €.\n",
                nombreHilo, saldoPrevio, saldoFinal);
    }
}