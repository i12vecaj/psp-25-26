import java.io.*;

public class EjecutaComando3 {
    public static void main(String[] args) {
        String website = "google.com"; // Página a la que haremos ping
        String outputFile = "salida.txt"; // Archivo donde se guardará el resultado

        ProcessBuilder processBuilder = new ProcessBuilder("ping", website);

        try { // lo metes dentro de un try por si falla pa que te tire la excepcion. es MUY importante mirar logs
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();

            // outputStream pa guardar en el archivo
            OutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int bytesRead;

            // copias la salida del ping al archivo
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // importante cierra los streams
            inputStream.close();
            outputStream.close();

            int exitCode = process.waitFor();
            System.out.println("ping terminado con código: " + exitCode);
            System.out.println("resultado guardado en: " + outputFile);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/* Segundo metodo que no me gusta tanto pero fue mi approach inicial
public class EjecutaComando3 {
    public static void main(String[] args) throws IOException {

        Process p = new ProcessBuilder("CMD", "/C","ping -n 3 google.com").start();
        try (

                InputStream is = p.getInputStream();
                FileWriter fw = new FileWriter("salida.txt");
                PrintWriter pw = new PrintWriter(fw);

                ){



            // mostramos en pantalla caracter a caracter
            int c;
            while ((c = is.read()) != -1) {
                char ch = (char) c;
                System.out.print((char) c);
                pw.print(ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

 */