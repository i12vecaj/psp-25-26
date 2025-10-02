package parte_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutaComando {
    public static void main(String[] args) throws IOException {
        // 1. Primer comando: lista los archivos del directorio y guarda la salida en salida.txt
        ProcessBuilder listarArchivos = new ProcessBuilder("ls", "-l");
        listarArchivos.redirectErrorStream(true);
        listarArchivos.redirectOutput(new File("salida.txt"));

        // Iniciar primer proceso
        Process p1 = listarArchivos.start();

        try {
            int exitCode1 = p1.waitFor();  // Esperamos a que termine
            System.out.println("Listado completado. Código de salida: " + exitCode1);

            // 2. Solo si el primer proceso fue exitoso (exit code == 0)
            if (exitCode1 == 0) {
                // Segundo comando: mostrar el contenido del archivo
                ProcessBuilder mostrarContenido = new ProcessBuilder("cat", "salida.txt");
                mostrarContenido.redirectErrorStream(true);

                Process p2 = mostrarContenido.start();

                // Leemos y mostramos el contenido línea a línea
                BufferedReader reader = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }

                int exitCode2 = p2.waitFor();
                System.out.println("Mostrar contenido finalizado con código: " + exitCode2);
            } else {
                System.out.println("El primer proceso falló. No se ejecuta el segundo.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
