import java.io.*;

public class EjecutarAnterior {

    public static void main(String[] args) {

        try{

            File javaMain=new File("./Main.java");

            //Compila
            ProcessBuilder pb = new ProcessBuilder("javac", javaMain.getAbsolutePath());
            Process compileJava=pb.start();
            compileJava.waitFor();

            //Ejecuta
            ProcessBuilder pbExecuteJava = new ProcessBuilder("java", javaMain.getAbsolutePath());
            Process pExecuteJava=pb.start();

            //Leemos
            InputStream inputStream = pExecuteJava.getInputStream();
            //PARTE IMPORTANTE PARA QUE ME LEA
            //Creo buffered reader y dentro un inputstreamreader de manera que me lea la pipe de informacion(creo se llamaba pipe)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            //Mientras tenga algo que leer, leerá lo creado
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();



        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

    }

    }
