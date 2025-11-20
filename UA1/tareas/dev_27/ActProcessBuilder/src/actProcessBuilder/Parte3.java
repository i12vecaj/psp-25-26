package actProcessBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Parte3 {


	//Hago el constructor de procesor, que primero hace el ping, creo un fichero y redirijo el flujo de texto al txt.
	public static void main(String[] args) throws IOException   {

		ProcessBuilder pb = new ProcessBuilder("CMD", "/C" ,"ping www.google.com");
		
		File salida = new File("salida.txt");
		
		pb.redirectOutput(salida);
		
		Process p = pb.start();
		
		int exitVal;
		try {
			exitVal = p.waitFor();
			System.out.println("Valor de Salida: " + exitVal);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
