import java.io.File;
import java.io.IOException;

public class ejecutarPrograma {
    public static  void main(String[]args){

        /*
        Creamos el process builder para ejecutar el Main. Dependiendo del argumento
        que le pasemos el codigo sera uno u otro;
         */
        ProcessBuilder pb = new ProcessBuilder("java", "Main", "3");

        //Le asignamos el directorio del .class del programa que tiene que ejecutar
        pb.directory(new File("C:\\Users\\Usuario\\IdeaProjects\\Tarea3\\out\\production\\Tarea3"));

        /*
        Realizamos dos try-catch para controlar los errores del pb.start
        y del codigo de respuesta
         */
        try {
            Process proceso = pb.start();
            int exitCode = 0;
            try {
                exitCode = proceso.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //Mostramos el codigo con el que respondera el Main
            System.out.println("El programa terminó con código: " + exitCode);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
