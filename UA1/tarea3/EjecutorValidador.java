import java.io.BufferedReader;
import java.io.InputStreamReader;
public class EjecutorValidador {

    public static void main(String[] args) {
        String argumentoPrueba = args.length > 0 ? args[0] : "";

        try {
            System.out.println("=== Ejecutando ValidadorArgumentos ===");
            System.out.println("Argumento enviado: " +
                    (argumentoPrueba.isEmpty() ? "(ninguno)" : argumentoPrueba));
            System.out.println();

            ProcessBuilder pb;
            if (argumentoPrueba.isEmpty()) {
                pb = new ProcessBuilder("java", "ValidadorArgumentos");
            } else {
                pb = new ProcessBuilder("java", "ValidadorArgumentos", argumentoPrueba);
            }

            Process proceso = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );

            String linea;
            System.out.println("--- Salida del programa ---");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            int codigoSalida = proceso.waitFor();

            System.out.println();
            System.out.println("Resultado");
            System.out.println("Código de salida: " + codigoSalida);

            interpretarCodigo(codigoSalida);

        } catch (Exception e) {
            System.err.println("Error al ejecutar el programa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void interpretarCodigo(int codigo) {
        switch (codigo) {
            case 0:
                System.out.println("El argumento era un número entero válido (>=0)");
                break;
            case 1:
                System.out.println("No se proporcionaron argumentos suficientes");
                break;
            case 2:
                System.out.println("El argumento era una cadena de texto");
                break;
            case 3:
                System.out.println("El argumento era un número entero negativo");
                break;
            default:
                System.out.println("Código de salida desconocido");
                break;
        }
    }
}