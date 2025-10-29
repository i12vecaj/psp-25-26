 q  import java.io.IOException;

public class Lanzador {
    // Ejecuta el programa Main y muestra el resultado

    public static void main(String[] args) {
        try {

            //Instanciamos el proceso para ejecutar el Main
            ProcessBuilder pb = new ProcessBuilder("java", "Main");
            if (args.length > 0) {
                pb.command().add(args[0]);
            }
            Process proceso = pb.start();

            // Esperamos a que termine y capturamos el código de salida
            int codigoSalida = proceso.waitFor();

            // Mostramos el mensaje correspondiente a cada salida
            switch (codigoSalida) {
                case 1 -> System.out.println("Error: No se ha introducido ningún argumento");
                case 2 -> System.out.println("Error: El argumento no es un número entero");
                case 3 -> System.out.println("Error: El número introducido es menor que 0");
                case 0 -> System.out.println("Ejecución correcta: El argumento es válido");
                default -> System.out.println("Código de salida desconocido: " + codigoSalida);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
