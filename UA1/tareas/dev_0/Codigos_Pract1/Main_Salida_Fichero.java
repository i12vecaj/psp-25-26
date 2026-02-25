import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main_Salida_Fichero {
    public static void main(String[] args) {
        // Lista para guardar la salida del comando
        List<String> salida = new ArrayList<>();
        String fichero = "salida.txt";

        try {
            // Comando ping en Windows (-n indica el número de repeticiones)
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com");

            // Redirigir errores al mismo flujo que la salida estándar
            pb.redirectErrorStream(true);

            // Iniciar el proceso
            Process p = pb.start();

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);   // Mostrar en consola
                salida.add(line);           // Guardar en lista
            }

            // Esperar a que finalice y guardar código de salida
            int exitCode = p.waitFor();
            String fin = "El comando terminó con código: " + exitCode;
            System.out.println(fin);
            salida.add(fin);

            // Guardar todo en fichero
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
                for (String linea : salida) {
                    bw.write(linea);
                    bw.newLine();
                }
            }

            System.out.println("\nLa salida también se guardó en " + fichero);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
