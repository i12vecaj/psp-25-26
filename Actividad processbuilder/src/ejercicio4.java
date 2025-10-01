

import java.io.*;

public class ejercicio4 {
    public static void main(String[] args) throws Exception {
        String comando1 = System.getProperty("os.name").toLowerCase().contains("win") ? //si el sistema operativo es windows ejecuto el primer condicional, en caso contrario el segundo para linux
                "cmd /c dir" : "ls";

        ProcessBuilder pb1 = new ProcessBuilder(comando1.split(" "));
        pb1.redirectOutput(new File("lista.txt"));
        Process p1 = pb1.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }

        //Para controlarlo usaría un condicional que revise cual ha sido la salida del primero
    }
}
