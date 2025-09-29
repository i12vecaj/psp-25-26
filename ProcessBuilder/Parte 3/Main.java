import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File archivo = new File("C:\\Users\\Rafa\\Desktop\\2dam\\psp\\prueba.txt"); // Reemplaza con la ruta deseada
        FileWriter fw = new FileWriter(archivo);
        Process p = new ProcessBuilder("CMD", "/C", "ping www.google.com").start();

        try {

            InputStream is = p.getInputStream();

            // mostramos en pantalla caracter a caracter

                int c;
                while ((c = is.read()) != -1)
                    fw.write(c);
                is.close();
                System.out.println("Se ha escrito en el archivo correctamente.");


        } catch (Exception e) {
            e.printStackTrace();
        }



        }

    }
