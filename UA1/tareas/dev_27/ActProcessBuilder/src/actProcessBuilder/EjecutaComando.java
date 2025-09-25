package actProcessBuilder;

import java.io.IOException;
import java.io.InputStream;

public class EjecutaComando {

	//Creo un proceso que abre el cmd, escribe /C y hace el ping a Google. Esa información la guarda en una variable int, que imprime una letra que muestra por consola lo que ocurre
	public static void main(String[] args) throws IOException   {
		Process pb = new ProcessBuilder("CMD", "/C" ,"ping www.google.com").start();
		try {
			InputStream is = pb.getInputStream();
			
			int c;
			 while ((c = is.read()) != -1)
				System.out.print((char) c);
			 is.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
