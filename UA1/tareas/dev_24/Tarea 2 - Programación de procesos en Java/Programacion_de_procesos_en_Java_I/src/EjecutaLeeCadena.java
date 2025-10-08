import java.io.*;

//Programa que ejecuta el programa --> (LeeCadena)

public class EjecutaLeeCadena {
    public static void main(String[] args) {
        try {
            // Crear proceso que ejecuta el programa LeeCadena
            ProcessBuilder pb = new ProcessBuilder("java", "LeeCadena");
            pb.redirectErrorStream(true); // Combina salida estándar y de error
            Process proceso = pb.start();

            // Mostrar salida del proceso
            BufferedReader salida = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = salida.readLine()) != null) {
                System.out.println(linea);
            }

            int codigo = proceso.waitFor();
            if (codigo == 0) {
                System.out.println("El proceso finalizó correctamente.");
            } else {
                System.err.println("El proceso terminó con errores. Código: " + codigo);
            }

        } catch (IOException e) {
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Ejecución interrumpida: " + e.getMessage());
        }
    }
}
