import java.io.*;

public class EjecutarComando {
    public static void main(String[] args) {
        try {
            ProcessBuilder proceso = new ProcessBuilder("cmd", "/c", "ping -n 2 google.com");
            proceso.redirectErrorStream(true);

            Process ejecucion = proceso.start();
            try (BufferedReader salida = new BufferedReader(new InputStreamReader(ejecucion.getInputStream()))) {
                String lineaSalida;
                while ((lineaSalida = salida.readLine()) != null) {
                    System.out.println(lineaSalida);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
