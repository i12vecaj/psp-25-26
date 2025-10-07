import java.io.*;

public class ManejarErrores {
    public static void main(String[] args) {
        try {
            ProcessBuilder proceso = new ProcessBuilder("cmd", "/c", "ping dominioInvalido.ejemplo");
            proceso.redirectErrorStream(true); // unir salida de error y estándar

            Process ejecucion = proceso.start();
            try (BufferedReader salida = new BufferedReader(new InputStreamReader(ejecucion.getInputStream()))) {
                String texto;
                while ((texto = salida.readLine()) != null) {
                    System.out.println(texto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
