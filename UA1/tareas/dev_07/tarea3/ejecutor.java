import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ejecutor {

    public static void main(String[] args) {
        try {
            // Comando base: ejecutar la clase main con el .add
            List<String> comando = new ArrayList<>();
            comando.add("java");
            comando.add("main");

            // Añadir argumentos dados al ejecutor
            for (String arg : args) {
                comando.add(arg);
            }

            // Crear el ProcessBuilder con el comando completo
            ProcessBuilder pb = new ProcessBuilder(comando);

            // Indicar el directorio actual (donde están los .class)
            pb.directory(new File("."));

            // Redirigir cosas del proceso hijo a la consola principal
            pb.inheritIO();

            // Lanzar el proceso
            Process proceso = pb.start();

            // Esperar a que finalice
            int codigoSalida = proceso.waitFor();

            // Interpretar código de salida
            switch (codigoSalida) {
                case 1:
                    System.out.println("Error: No se ha introducido ningún argumento.");
                    break;
                case 2:
                    System.out.println("Error: El argumento no es un número.");
                    break;
                case 3:
                    System.out.println("Error: El número introducido es negativo.");
                    break;
                case 0:
                    System.out.println("Ejecución correcta: Número válido mayor o igual que 0.");
                    break;
                default:
                    System.out.println("Código de salida desconocido: " + codigoSalida);
            }

        } catch (IOException e) {
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("La ejecución del proceso fue interrumpida.");
        }
    }
}
