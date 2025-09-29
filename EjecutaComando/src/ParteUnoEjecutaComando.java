import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParteUnoEjecutaComando {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com");
        //"2" numero de veces que vas a hacer ping, "-c" ->  Enrutamiento del identificador del compartimiento.

        // redirigir errores a la salida estandar
        pb.redirectErrorStream(true);

        //iniciar proceso
        Process p = pb.start();

        //Leer salida del proceso
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }
    }
}