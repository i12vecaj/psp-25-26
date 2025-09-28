import java.io.*;

public class EncadenarProcesos {

    public static void main(String[] args) {

        File archivoSalida = new File("listaArchivosEncadenados.txt");

        try {
            System.out.println("Comando 1 iniciado");
            ProcessBuilder pb1 = new ProcessBuilder("CMD", "/C", "dir");

            pb1.redirectOutput(archivoSalida);

            Process p1 = pb1.start();

            p1.waitFor();

            System.out.println("Comando 1 terminado");

            System.out.println("\nComando 2 iniciado");
            ProcessBuilder pb2 = new ProcessBuilder("CMD", "/C", "type", archivoSalida.getName());

            pb2.inheritIO();

            Process p2 = pb2.start();

            p2.waitFor();

            System.out.println("Comando 2 terminado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Reflexiona: ¿cómo controlarías que el segundo comando solo se ejecute si el primero ha tenido exit code 0?

// Para hacerlo cambiaria el "p1.waitFor();" haciendo que haya un int con la informacion del exit code y despues
// un if que compruebe que este exit code sea el correcto para avanzar al paso 2