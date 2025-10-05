
public class Script3 {
    public static void main(String[] args) {
        long iterations = 4_000_000_000L;

        System.out.println("-> Script 3: Iniciado.");

        double result = 0.0;
        for (long i = 0; i < iterations; i++) {
            result += Math.sqrt(i) / (i + 1);
        }

        System.out.println("<- Script 3: Finalizado. (Resultado parcial: " + (result % 100) + ").");
    }
}