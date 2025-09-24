import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EjecutaComando5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un comando a ejecutar: ");
        String input = sc.nextLine();
        sc.close();

        String[] partes = input.split(" ");
        List<String> comando = new ArrayList<>();
        for (String p : partes) {
            comando.add(p);
        }

        try {
            File registro = new File("RegistrodeComandos.txt");

            ProcessBuilder pb1 = new ProcessBuilder(comando);
            pb1.redirectOutput(registro);
            pb1.redirectErrorStream(true);
            Process proceso1 = pb1.start();
            int exitCode1 = proceso1.waitFor();

            System.out.println("El proceso terminó con código: " + exitCode1 + "\nRevise el archivo RegistrodeComandos.txt");

            if (exitCode1 == 0) {
                ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "type", "RegistrodeComandos.txt");
                pb2.inheritIO();
                Process p2 = pb2.start();
                int exitCode2 = p2.waitFor();
                System.out.println("Segundo comando terminó con código: " + exitCode2);
            } else {
                System.out.println("El primer comando falló, no se ejecuta el segundo.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
