import java.util.Random;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("PRÁCTICA: Sincronización de Hilos en Java - Cuenta Corriente");
        System.out.println("=".repeat(70));
        
        try {
            double saldoInicial = 1000.0;
            CuentaCorriente cuenta = new CuentaCorriente(saldoInicial);
            
            System.out.printf("%nSaldo inicial de la cuenta: %.2f€%n%n", cuenta.getSaldo());
            
            HiloIngreso[] hilos = {
                new HiloIngreso("Hilo-1", cuenta, 200.0),
                new HiloIngreso("Hilo-2", cuenta, 150.0),
                new HiloIngreso("Hilo-3", cuenta, 300.0),
                new HiloIngreso("Hilo-4", cuenta, 100.0),
                new HiloIngreso("Hilo-5", cuenta, 250.0)
            };
            
            System.out.println("Iniciando ingresos concurrentes...");
            System.out.println("-".repeat(70));
            
            for (HiloIngreso hilo : hilos) {
                hilo.start();
            }
            
            for (HiloIngreso hilo : hilos) {
                hilo.join();
            }
            
            System.out.println("-".repeat(70));
            
            double totalIngresado = 200.0 + 150.0 + 300.0 + 100.0 + 250.0;
            double saldoEsperado = saldoInicial + totalIngresado;
            double saldoFinal = cuenta.getSaldo();
            
            System.out.printf("%nSaldo final de la cuenta: %.2f€%n", saldoFinal);
            System.out.printf("Saldo esperado: %.2f€%n", saldoEsperado);
            System.out.printf("Diferencia: %.2f€%n", Math.abs(saldoFinal - saldoEsperado));
            
            if (Math.abs(saldoFinal - saldoEsperado) < 0.01) {
                System.out.println("%n✓ RESULTADO CORRECTO: Con synchronized los ingresos son consistentes");
            } else {
                System.out.println("%n✗ RESULTADO INCORRECTO: Se han perdido operaciones por falta de sincronización");
            }
            
            System.out.println("%n" + "=".repeat(70));
            System.out.println("NOTA: Para el FR4, elimina 'synchronized' de los métodos de");
            System.out.println("CuentaCorriente y observa cómo el saldo final es incorrecto debido");
            System.out.println("a las condiciones de carrera (race conditions).");
            System.out.println("=".repeat(70));
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error de argumento: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error: Hilo principal interrumpido - " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}