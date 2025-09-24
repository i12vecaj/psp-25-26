import java.io.*;

public class Parte4_EncadenamientoDeProcesos {
    public static void main(String[] args) {

        File archivoSalida = new File("C:\\Users\\alejo\\Desktop\\ProcesosYServicios\\Procesos_Act1\\salida_dir.txt");


        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "dir");
        pb1.redirectOutput(archivoSalida);

        try {

            Process p1 = pb1.start();
            int exitCode1 = p1.waitFor();
            System.out.println("Primer proceso (dir) terminó con código: " + exitCode1);


            if (exitCode1 == 0) {
                // Segundo comando: type salida_dir.txt
                ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "type", archivoSalida.getAbsolutePath());
                pb2.redirectErrorStream(true);

                Process p2 = pb2.start();


                try (BufferedReader reader = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
                    String linea;
                    System.out.println("Contenido del archivo salida_dir.txt:");
                    while ((linea = reader.readLine()) != null) {
                        System.out.println(linea);
                    }
                }

                int exitCode2 = p2.waitFor();
                System.out.println("Segundo proceso (type) terminó con código: " + exitCode2);
            } else {
                System.out.println("El primer proceso falló. No se ejecutará el segundo.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
