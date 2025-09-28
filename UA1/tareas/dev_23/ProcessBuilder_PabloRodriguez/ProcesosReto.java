import java.io.*;
import java.util.Scanner;

public class ProcesosReto {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce un comando: ");

        String comandoCompleto = scanner.nextLine();

        try {
            ProcessBuilder pb = new ProcessBuilder("CMD", "/C", comandoCompleto);

            System.out.println("\nEjecutando: " + comandoCompleto);

            pb.redirectErrorStream(true);

            Process p = pb.start();

            InputStream is = p.getInputStream();
            int i;

            while ((i = is.read()) != -1)
                System.out.print((char) i);
            is.close();

            int exitCode = p.waitFor();

            System.out.println("\nComando terminado. Exit Code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//Analiza: ¿qué riesgos tiene permitir ejecutar cualquier comando del sistema?

// El riesgo es que podria ejecutar cualquier comando que pueda poner el pc en algun tipo de riesgo, como desactivar
// el firewall, cambiar permisos de carpetas, etc