package actProcessBuilder;

import java.io.IOException;
import java.io.InputStream;

public class direccioninexistente {

	//Creo un proceso que abre el cmd, escribe /C y hace el ping a 192.222.333.777. Al dar error, lo muestra. Y al dar fallo, el valor de salida es 1
	public static void main(String[] args) throws IOException   {
		Process pb = new ProcessBuilder("CMD", "/C" ,"ping 192.222.333.777").start();
		try {
			InputStream is = pb.getInputStream();
			
			int c;
			 while ((c = is.read()) != -1)
				System.out.print((char) c);
			 is.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// COMPROBACION DE ERROR - 0 bien - 1 mal
				int exitVal;
				try {
					exitVal = pb.waitFor();
					System.out.println("Valor de Salida: " + exitVal);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	}
}
