import java.io.*;

// Programa ejecutor
public class Main {
    public static void main(String[] args) {
        ProcessBuilder pb = null;
        Process process = null;

        try {
            pb = new ProcessBuilder("java", "Reader.java");

            // Directorio de trabajo si tu IDE no quiere ayudar
            // processBuilder.directory(new File("C:/Users/USER/IdeaProjects/Tarea2/src"));

            pb.inheritIO(); // Sin esto no funciona el programa, permite usar la terminal

            process = pb.start();

            if (process == null) {
                throw new IOException("No se pudo iniciar el proceso");
            }

            int exitStatus = process.waitFor();

            if (exitStatus == 0) {
                System.out.println("Ejecución completada sin problemas (código: " + exitStatus + ")");
            } else {
                System.err.println("El proceso finalizó con errores (código: " + exitStatus + ")");
            }

        } catch (IOException e) {
            System.err.println("Error IO: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}