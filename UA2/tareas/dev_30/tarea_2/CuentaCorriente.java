import java.util.Random;

/**
 * Programa de concurrencia que demuestra el efecto de sincronizar (FR3) frente a no sincronizar (FR4).
 *
 * FR1: Clase CuentaCorriente con saldo, constructor, getters/setters con sleep aleatorio (250–2000 ms).
 * FR2: Hilos que realizan ingresos concurrentes usando métodos de esta clase.
 * FR3: Escenario sincronizado: suma correcta y previsible.
 * FR4: Escenario sin sincronizar: posibles pérdidas de actualizaciones y saldo final incorrecto.
 *
 * Diferencias observadas entre FR3 y FR4:
 * - Con synchronized, cada ingreso se ejecuta de forma exclusiva; no se pierden actualizaciones.
 * - Sin synchronized, varios hilos leen el mismo saldo y escriben sobre él, produciendo resultados incoherentes.
 * - La magnitud del error depende del interleaving de hilos; suele ser menor que la suma esperada.
 */
public class CuentaCorriente {
    private double saldo;
    private final Random rng = new Random();

    /**
     * Construye la cuenta dando un valor inicial al saldo.
     */
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    /**
     * Getter del saldo con pausa aleatoria entre 250 y 2000 ms.
     */
    public double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    /**
     * Setter del saldo con pausa aleatoria entre 250 y 2000 ms.
     */
    public void setSaldo(double nuevoSaldo) {
        dormirAleatorio();
        this.saldo = nuevoSaldo;
    }

    /**
     * Método sincronizado que ingresa una cantidad en la cuenta.
     * Muestra estado previo, estado final y quién realiza el ingreso.
     */
    public synchronized void ingresarSync(double cantidad, String autor) {
        if (!validarCantidad(cantidad, autor)) return;
        double antes = getSaldo();
        double despues = antes + cantidad;
        setSaldo(despues);
        System.out.printf("[SYNC] %s ingresa %.2f -> saldo: %.2f -> %.2f%n", autor, cantidad, antes, despues);
    }

    /**
     * Método NO sincronizado que ingresa una cantidad.
     * Sirve para comparar resultados frente a ingresarSync.
     */
    public void ingresarNoSync(double cantidad, String autor) {
        if (!validarCantidad(cantidad, autor)) return;
        double antes = getSaldo();
        double despues = antes + cantidad;
        setSaldo(despues);
        System.out.printf("[NOSYNC] %s ingresa %.2f -> saldo: %.2f -> %.2f%n", autor, cantidad, antes, despues);
    }

    /**
     * Control de errores básico: cantidad debe ser positiva y finita.
     */
    private boolean validarCantidad(double cantidad, String autor) {
        if (Double.isNaN(cantidad) || Double.isInfinite(cantidad) || cantidad <= 0.0) {
            System.err.printf("%s: cantidad inválida: %.2f%n", autor == null ? "<sin-autor>" : autor, cantidad);
            return false;
        }
        return true;
    }

    /**
     * Pausa aleatoria de 250–2000 ms para simular latencia/acceso a recurso.
     */
    private void dormirAleatorio() {
        int ms = 250 + rng.nextInt(1751); // 250..2000
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido durante sleep: " + e.getMessage());
        }
    }
}