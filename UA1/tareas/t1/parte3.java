package t1;
import java.io.*;

public class parte3 {
    public static void main(String[] args) throws IOException{
		String fichero = "salida.txt";
        Process p = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com").start();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {

			InputStream is = p.getInputStream();

			 int c;
			 while ((c = is.read()) != -1)
			 bw.write((char) c);
			 is.close();

		
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}
