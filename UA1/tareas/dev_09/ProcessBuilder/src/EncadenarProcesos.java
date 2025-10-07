import java.io.*;

public class EncadenarProcesos {

    public static void main(String[] args) {
        try {
            // Ejecuta un comando y guarda su salida
            ProcessBuilder listado = new ProcessBuilder("cmd", "/c", "dir");
            listado.redirectOutput(new File("salida.txt"));
            Process proc1 = listado.start();
            int codigo = proc1.waitFor();

            // Si fue exitoso, muestra el contenido generado
            if (codigo == 0) {
                lanzarLectura(new ProcessBuilder("cmd", "/c", "type", "salida.txt").start());
            } else {
                System.err.println("Proceso terminado con error: " + codigo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void lanzarLectura(Process proc) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String l;
            while ((l = br.readLine()) != null) {
                System.out.println(l);
            }
        }
    }
}
