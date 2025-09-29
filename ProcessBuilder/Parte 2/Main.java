import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Process p = new ProcessBuilder("CMD", "/C", "ping inexistente.co").start();

        try {

            InputStream is = p.getInputStream();

            // mostramos en pantalla caracter a caracter

                int c;
                while ((c = is.read()) != -1)
                    System.out.print((char) c);
                is.close();


        } catch (Exception e) {
            e.printStackTrace();
        }



        }

    }
