import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejecutor {
    public static void main(String[] args) {
        try {
            //Que cree un proceso para ejecutar Lector.java
            ProcessBuilder pb = new ProcessBuilder("java", "Lector.java");
            pb.inheritIO(); // Aqui puede hacer q se utilice tanto de entrada como de salida en la consola

            Process proceso = pb.start();
            int codigoSalida = proceso.waitFor(); // Esperar a que haga el proceso

            System.out.println("El proceso terminó con código: " + codigoSalida); //Codigo de salida 0 es que ha ido bien
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage()); //El control de errores generico para todos los errores
        }
    }
}