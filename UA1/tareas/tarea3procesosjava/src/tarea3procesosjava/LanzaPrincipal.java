package tarea3procesosjava;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LanzaPrincipal {
    public static void main(String[] args) {
        try {
            // Construir el comando para ejecutar el otro programa
        	ProcessBuilder pb = new ProcessBuilder(
        		    "java", "-cp", "bin", "tarea3procesosjava.Principal", args.length > 0 ? args[0] : ""
        		);
        	
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            // Leer la salida (aunque Principal no imprime nada, es una buena práctica)
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que termine el proceso y capturar el código de salida
            int codigoSalida = proceso.waitFor();

            // Mostrar el resultado según el valor devuelto
            switch (codigoSalida) {
                case 1:
                    System.out.println("Error: no se ha pasado ningún argumento (código 1).");
                    break;
                case 2:
                    System.out.println("Error: el argumento no es un número entero (código 2).");
                    break;
                case 3:
                    System.out.println("Error: el número es menor que 0 (código 3).");
                    break;
                case 0:
                    System.out.println("Ejecución correcta (código 0).");
                    break;
                default:
                    System.out.println("Código de salida desconocido: " + codigoSalida);
            }

        } catch (Exception e) {
            System.err.println("Error al ejecutar el programa Principal: " + e.getMessage());
        }
    }
}
