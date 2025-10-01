import java.io.*;
import java.util.Scanner;

public class ejercicio5 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un comando: ");
        String cmd = sc.nextLine();

        ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
        pb.redirectErrorStream(true);
        Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
    }
}

//El riesgo está en que si dejas al usuario ejecutar cualquier comando puede por ejemplo ejecutar cat /etc/passwd. Esto ocurre por ejemplo en sistemas vulnerables a Remote Code Execurion (RCE).
