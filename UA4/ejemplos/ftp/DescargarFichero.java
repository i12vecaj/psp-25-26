import java.io.*;
import org.apache.commons.net.ftp.*;
public class DescargarFichero {
	public static void main(String[] args) {
		FTPClient cliente = new FTPClient();
		
	    String servidor = "localhost";
		String user = "usuario";
		String pasw = "usuario";

		try {
			System.out.println("Conectandose a " + servidor);
			cliente.connect(servidor);			
			cliente.enterLocalPassiveMode();
			boolean login = cliente.login(user, pasw);		
			
			if (login) {
				System.out.println("Login correcto");		

				//descargar fichero
				String direc = "/carpeta";
				cliente.changeWorkingDirectory(direc); 		
				
				//stream de salida para recibir el fichero descargado
				BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream("ficheros/descargado.txt"));
				
				if(cliente.retrieveFile("fichero.txt", out))
					System.out.println("Recuperado correctamente... ");
				else
					System.out.println("No se ha podido descargar... ");
				
				out.close();
	
				cliente.logout();
				cliente.disconnect();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}//main

	
}//..
