import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        String[] pruebas = {"", "Hola", "-5", "10", "0"};

        System.out.println("=== EJECUTOR DE ProgramaPrincipal ===\n");

        for (int i = 0; i < pruebas.length; i++) {
            System.out.println("--- Caso de prueba " + (i + 1) + " ---");
            ejecutar(pruebas[i].isEmpty() ? null : pruebas[i]);
            System.out.println();
        }
    }

    // Ejecuta ProgramaPrincipal con o sin argumento
    private static void ejecutar(String arg) {
        try {
            String classPath = System.getProperty("java.class.path");
            ProcessBuilder pb;

            if (arg == null) {
                System.out.println("Ejecutando sin argumentos...");
                pb = new ProcessBuilder("java", "-cp", classPath, "ProgramaPrincipal");
            } else {
                System.out.println("Ejecutando con argumento: \"" + arg + "\"");
                pb = new ProcessBuilder("java", "-cp", classPath, "ProgramaPrincipal", arg);
            }

            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                System.out.println("Salida del programa:");
                while ((linea = lector.readLine()) != null) {
                    System.out.println("  " + linea);
                }
            }

            int codigo = proceso.waitFor();
            mostrarCodigo(codigo);

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Proceso interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    // Interpreta el código de salida del proceso
    private static void mostrarCodigo(int codigo) {
        System.out.println("\nCódigo de salida: " + codigo);
        System.out.print("Interpretación: ");

        switch (codigo) {
            case 0 -> System.out.println("El argumento es un número válido (>= 0)");
            case 1 -> System.out.println("No se proporcionaron argumentos");
            case 2 -> System.out.println("El argumento no es numérico");
            case 3 -> System.out.println("El argumento es un número negativo");
            default -> System.out.println("Código de salida desconocido");
        }
    }
}
