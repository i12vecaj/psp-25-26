import java.io.*;
import java.util.Scanner;

public class EjecutaUsuario {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un comando: ");
        String comando = sc.nextLine();

        Process p = new ProcessBuilder(comando.split(" ")).redirectErrorStream(true).start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String l; while ((l = r.readLine()) != null) System.out.println(l);

        //Es muy peligro permitid al usuario introducir cualquier comando ya que tiene asceso a la totalidad del dispositivo
    }
}
