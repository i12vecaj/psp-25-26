import java.io.*;
import java.util.*;
public class Ejecutor {
    private static final String validadorFile = "Validador";
    public static void main(String[] args){
        System.out.println("Pruebas del validador");
        // creo diferentes escenarios para probar cada escenario de error
        ejecutarPrueba(null);
        ejecutarPrueba("texto");
        ejecutarPrueba("-10");
        ejecutarPrueba("30");
    }

    public static void ejecutarPrueba(String argumento){
        List<String> comando = new ArrayList<>();
        comando.add("java");
        comando.add(validadorFile);
        System.out.println("--");
        //si el argumento no es null, se construye el comando
        if (argumento !=null){
            comando.add(argumento);
            System.out.println("lanzando: java " + validadorFile + " " + argumento);
        }else{
            System.out.println("lanzando: java " + validadorFile + " (Sin argumentos)");
        }

        // creo el proceso con el comando construido
        ProcessBuilder pb = new ProcessBuilder(comando);

        try {
            // se inicia el proceso
            Process proceso = pb.start();

            // esto hace esperar hasta que captura el codigo de retorno
            int retorno = proceso.waitFor();

            // interpreta el resultado para hacerlo legible
            interpretarRetorno(retorno);
        }catch (IOException e){
            System.out.println("el programa validador no se pudo ejecutar" + e.getMessage());
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public static void interpretarRetorno(int codigo){
        System.out.println("codigo de salida " + codigo + "Significado: ");

        // dependiendo del codigo retornado muestra su significado correspondoente
        switch (codigo){
            case 0:
                System.out.println("Argumento vaslido, numero entero positivo" + codigo);
                break;

            case 1:
                System.out.println("el numero de argumentos es nulo( < 1)");
                break;

            case 2:
                System.out.println("el argumento es una cadena de texto");
                break;

            case 3:
                System.out.println("el argumento es un numero entero menor de 0");
                break;
        }
    }
}