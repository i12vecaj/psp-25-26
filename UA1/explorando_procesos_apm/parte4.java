import java.io.IOException;
import java.io.InputStream;

public class parte4 {
     public static void main(String[] args) throws IOException {

        Process p1 = new ProcessBuilder("cmd.exe", "/c", "dir").start();

        try {
            InputStream is = p1.getInputStream();
            int c;
            while ((c = is.read()) != -1)
                System.out.print((char) c);
            is.close();

            Process p2 = new ProcessBuilder("cmd.exe", "/c", "type salida.txt").start();
            InputStream is2 = p2.getInputStream();
            while ((c = is2.read()) != -1)
                System.out.print((char) c);
            is2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
