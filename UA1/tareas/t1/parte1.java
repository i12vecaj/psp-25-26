package t1;

import java.io.IOException;
import java.io.InputStream;

public class parte1 {
    public static void main(String[] args) throws IOException{
        Process p = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com").start();
		try {

			InputStream is = p.getInputStream();

			// mostramos en pantalla caracter a caracter
			 int c;
			 while ((c = is.read()) != -1)
				System.out.print((char) c);
			 is.close();

		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
