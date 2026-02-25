import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que contiene el programa para probar la sincronización de hilos.
 * Implementa la lógica para las pruebas sincronizadas y no sincronizadas.
 * * Cumple con los requisitos FR3 y FR4.
 */
public class Main {

    private static final int NUM_HILOS = 5; // Número de hilos a utilizar
    private static final double CANTIDAD_INGRESO = 100.0; // Cantidad fija que ingresa cada hilo

    public static void main(String[] args) {
        // Ejecución de la prueba sincronizada (FR3)
        System.out.println("=====================================================================");
        System.out.println("--- INICIO DE PRUEBA 1: MÉTODO DE INGRESO SINCRONIZADO (FR3) ---");
        System.out.println("=====================================================================");
        ejecutarTest(true);

        System.out.println("\n\n=====================================================================");
        System.out.println("--- INICIO DE PRUEBA 2: MÉTODO DE INGRESO NO SINCRONIZADO (FR4) ---");
        System.out.println("=====================================================================");
        ejecutarTest(false);
    }

    /**
     * Ejecuta la prueba de concurrencia creando y lanzando hilos.
     * @param esSincronizado Indica si se debe usar el método sincronizado o no.
     */
    private static void ejecutarTest(boolean esSincronizado) {
        // FR3: Crea un objeto de tipo CuentaCorriente con un valor inicial.
        CuentaCorriente cuenta = new CuentaCorriente(500.0);

        System.out.println("Saldo inicial de la cuenta: " + cuenta.getSaldo() + " €");

        List<Thread> hilos = new ArrayList<>();

        // Se calcula el saldo esperado para la verificación final
        double saldoEsperado = cuenta.getSaldo() + (NUM_HILOS * CANTIDAD_INGRESO);

        // FR3: Crea varios hilos que compartan dicho objeto.
        for (int i = 1; i <= NUM_HILOS; i++) {
            // Cada hilo recibe un nombre y una cantidad de dinero (fija en este caso).
            String nombreHilo = "Hilo-" + i;
            IngresadorHilo hilo = new IngresadorHilo(cuenta, nombreHilo, CANTIDAD_INGRESO, esSincronizado);
            hilos.add(hilo);
        }

        long tiempoInicio = System.currentTimeMillis();

        // FR3: Lanza los hilos.
        for (Thread hilo : hilos) {
            hilo.start();
        }

        // FR3: Espera a que finalicen para visualizar el saldo final de la cuenta.
        for (Thread hilo : hilos) {
            try {
                hilo.join(); // Espera a que el hilo termine.
            } catch (InterruptedException e) {
                // Control de errores básico (FR5): Captura la interrupción del hilo principal.
                System.err.println("El hilo principal fue interrumpido durante la espera. Error: " + e.getMessage());
                Thread.currentThread().interrupt(); // Re-interrumpe el hilo
            }
        }

        long tiempoFin = System.currentTimeMillis();

        // Visualiza el saldo final y compara.
        System.out.println("\n--- Resumen de la Prueba ---");
        System.out.printf("Saldo Final de la Cuenta: %.2f €\n", cuenta.getSaldo());
        System.out.printf("Saldo Esperado Teórico: %.2f €\n", saldoEsperado);
        System.out.printf("Duración total de la prueba: %.2f segundos\n", (tiempoFin - tiempoInicio) / 1000.0);

        if (Math.abs(cuenta.getSaldo() - saldoEsperado) < 0.01) {
            System.out.println("Resultado: ¡CORRECTO! El saldo final coincide con el esperado.");
        } else {
            System.out.println("Resultado: ¡INCORRECTO! El saldo final es diferente al esperado.");
            System.out.printf("Pérdida de dinero (Error): %.2f €\n", (saldoEsperado - cuenta.getSaldo()));
        }
        System.out.println("----------------------------\n");
    }
}