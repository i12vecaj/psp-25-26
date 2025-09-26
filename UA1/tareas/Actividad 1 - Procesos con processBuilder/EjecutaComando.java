import java.io.*;
import java.util.Scanner;

public class EjecutaComando {
    public static void main(String[] args) throws IOException, InterruptedException {
        
        ProcessBuilder pb1 = new ProcessBuilder("ping", "www.google.com");
        pb1.redirectErrorStream(true);
        Process p1 = pb1.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        }
  
        ProcessBuilder pb2 = new ProcessBuilder("ping", "www.direccionInexistente.test");
        pb2.redirectErrorStream(true);
        Process p2 = pb2.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        }

       
        String fichero = "salida.txt";
        ProcessBuilder pb3 = new ProcessBuilder("ping", "www.google.com");
        pb3.redirectErrorStream(true);
        Process p3 = pb3.start();

        try (
            BufferedReader br = new BufferedReader(new InputStreamReader(p3.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))
        ) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                bw.write(linea);
                bw.newLine();
            }
        }
        String ficheroLista = "lista.txt";

        ProcessBuilder pb4 = new ProcessBuilder("cmd", "/c", "dir");
        pb4.redirectOutput(new File(ficheroLista));
        Process p4 = pb4.start();
        int exitCode4 = p4.waitFor();

        if (exitCode4 == 0) {
            ProcessBuilder pb4b = new ProcessBuilder("cmd", "/c", "type", ficheroLista);
            pb4b.inheritIO();
            Process p4b = pb4b.start();
            p4b.waitFor();
        } else {
            System.out.println("Fallo: " + exitCode4);
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un comando: ");
        String comando = sc.nextLine();

        ProcessBuilder pb5 = new ProcessBuilder("cmd", "/c", comando);
        pb5.redirectErrorStream(true);
        Process p5 = pb5.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p5.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        }
        sc.close();
    }
}
