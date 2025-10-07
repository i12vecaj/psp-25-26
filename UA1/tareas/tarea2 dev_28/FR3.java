import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FR3 {
    public static void main(String[] args){
        //Realizamos un try-catch para controlar los errores
        try {
            //Creamos la clase ProcessBuilder con el comando y el programa que queremos ejecutar. En este caso java y nuestro Main
            ProcessBuilder pb = new ProcessBuilder("java", "Main");

            //Le tenemos que indicar el directorio donde se encuentra el .class del programa a ejecutar ya que usa dicho .class para ejecutarlo
            pb.directory(new File("C:\\Users\\Usuario\\IdeaProjects\\tarea 2\\out\\production\\tarea 2"));

            /*
            Debemos introducir el inheritIO, ya que como el programa que queremos ejecutar, requiere de que el usuario
            introduzca datos por consola
             */
            pb.inheritIO();

            // Ejecutamos el programa
            Process proceso = pb.start();


            // Por ultimo mostramos tambien el codigo, 0 si ha salido todo bien o -1 si hay errores
                int codigo = proceso.waitFor();
                System.out.println("El proceso termino con codigo " + codigo);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
