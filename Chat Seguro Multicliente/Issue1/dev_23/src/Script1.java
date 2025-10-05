public class Script1 {
    public static void main(String[] args) {
        long iterations = 5_000_000_000L;

        System.out.println("-> Script 1: Iniciado.");

        double result = 0.0;
        for (long i = 0; i < iterations; i++) {
            result += Math.sqrt(i) / (i + 1);
        }

        System.out.println("<- Script 1: Finalizado. (Resultado parcial: " + (result % 100) + ").");
    }
}