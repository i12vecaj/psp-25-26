import java.util.Random;

/**
 * Clase que representa una Cuenta Corriente compartida por múltiples hilos.
 * Contiene un saldo y métodos sincronizados para manejar ingresos concurrentes.
 */
public class CuentaCorriente {

    // Atributo que indica el saldo de la cuenta
    private double saldo;

    // Objeto Random para generar sleeps aleatorios
    private final Random random = new Random();

    /**
     * Constructor que inicializa la cuenta con un saldo inicial.
     * 
     * @param saldoInicial Valor inicial del saldo.
     */
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    /**
     * Simula una pausa aleatoria en la ejecución del hilo.
     */
    private void sleepAleatorio() {
        // Genera un tiempo de espera entre 250 y 2000 milisegundos
        int tiempoEspera = random.nextInt(1751) + 250;
        try {
            Thread.sleep(tiempoEspera);
        } catch (InterruptedException e) {
            // Control de errores básico: Si el hilo es interrumpido.
            System.err.println("Error en sleep: El hilo fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    // =======================================================
    // Métodos Setter y Getter con sleep aleatorio
    // =======================================================

    /**
     * Devuelve el saldo actual de la cuenta.
     * Añade un sleep aleatorio para simular latencia de acceso.
     * 
     * @return Saldo actual.
     */
    public double getSaldo() {
        sleepAleatorio();
        return saldo;
    }

    /**
     * Establece un nuevo saldo en la cuenta.
     * Añade un sleep aleatorio para simular latencia de acceso.
     * 
     * @param nuevoSaldo El nuevo saldo a establecer.
     */
    public void setSaldo(double nuevoSaldo) {
        sleepAleatorio();
        this.saldo = nuevoSaldo;
    }

    // =======================================================
    // Método para ingresar saldo (CRÍTICO)
    // =======================================================

    /**
     * Añade una cantidad al saldo de la cuenta.
     * * NOTA PARA FR4: Para comprobar el resultado sin sincronización,
     * simplemente COMENTE la palabra 'synchronized' en la línea de abajo.
     * * @param cantidad Cantidad a ingresar.
     * 
     * @param nombreIngresor Nombre del hilo/persona que realiza el ingreso.
     */
    public synchronized void ingresarCantidad(double cantidad, String nombreIngresor) {
        double saldoPrevio = this.getSaldo(); // Leer saldo (con sleep)

        System.out.printf("[%s] **INICIO INGRESO**: Saldo previo: %.2f€. Cantidad a ingresar: %.2f€\n",
                nombreIngresor, saldoPrevio, cantidad);

        // Simulación de la operación crítica (modificación del estado)
        double nuevoSaldo = saldoPrevio + cantidad;

        this.setSaldo(nuevoSaldo); // Escribir nuevo saldo (con sleep)

        System.out.printf("[%s] **FIN INGRESO**: Saldo final: %.2f€\n",
                nombreIngresor, this.saldo);
    }
}