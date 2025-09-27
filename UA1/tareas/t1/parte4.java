package t1;
import java.io.*;

public class parte4 {
    public static void main(String[] args){
        try {
            // Primer comando: dir > salida.txt
            ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "dir");
            pb1.redirectOutput(new File("parte4.txt"));
            Process p1 = pb1.start();

            int exitCode1 = p1.waitFor();
            if (exitCode1 == 0) {
                // Segundo comando: mostrar salida.txt
                ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "type parte4.txt");
                pb2.inheritIO();
                Process p2 = pb2.start();
                p2.waitFor();
            } else {
                System.out.println("Error: el primer comando falló con exit code " + exitCode1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
/* ¿cómo controlarías que el segundo comando solo se ejecute si el primero ha
tenido exit code 0? */
/* Lo contralaria con waitFor el cual te da o si el proceso anterior a terminado y distito a 0 si no ha terminado y despues haria un if comprabando si
el waitFor es 0 ejecutamos el otro ProcessBuilder y si es dinto a 0 se lo decimos al usuario por consola
  */