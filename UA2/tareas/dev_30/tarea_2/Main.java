import java.util.ArrayList;
import java.util.List;

/**
 * FR3 y FR4: Crea una cuenta, lanza varios hilos que comparten el objeto
 * y compara resultados usando ingreso sincronizado vs no sincronizado.
 */
public class Main {
    public static void main(String[] args) {
        double saldoInicial = 1000.0;
        double[] ingresos = {200.0, 150.0, 300.0, 50.0, 100.0};

        // FR3: escenario sincronizado
        CuentaCorriente cuentaSync = new CuentaCorriente(saldoInicial);
        System.out.printf("Saldo inicial (SYNC): %.2f%n", cuentaSync.getSaldo());
        List<Thread> ts1 = crearYLanzarHilos(cuentaSync, ingresos, true);
        esperar(ts1);
        double esperado = saldoInicial + suma(ingresos);
        System.out.printf("Saldo final (SYNC): %.2f | Esperado: %.2f%n%n", cuentaSync.getSaldo(), esperado);

        // FR4: escenario sin sincronizar
        CuentaCorriente cuentaNoSync = new CuentaCorriente(saldoInicial);
        System.out.printf("Saldo inicial (NOSYNC): %.2f%n", cuentaNoSync.getSaldo());
        List<Thread> ts2 = crearYLanzarHilos(cuentaNoSync, ingresos, false);
        esperar(ts2);
        System.out.printf("Saldo final (NOSYNC): %.2f | Esperado: %.2f%n", cuentaNoSync.getSaldo(), esperado);

        // Comentario: con NOSYNC es probable observar un saldo menor al esperado
        // por condiciones de carrera (lecturas/escrituras solapadas).
    }

    private static List<Thread> crearYLanzarHilos(CuentaCorriente cuenta, double[] ingresos, boolean sync) {
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < ingresos.length; i++) {
            Thread t = new IngresoThread("Cliente-" + (i + 1), cuenta, ingresos[i], sync);
            hilos.add(t);
            t.start();
        }
        return hilos;
    }

    private static void esperar(List<Thread> hilos) {
        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Main interrumpido esperando hilos: " + e.getMessage());
            }
        }
    }

    private static double suma(double[] xs) {
        double s = 0.0;
        for (double x : xs) s += x;
        return s;
    }
}