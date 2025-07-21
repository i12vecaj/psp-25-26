import java.io.*;
import org.apache.commons.net.ftp.*;

public class ClienteFTP2 {
  public static void main(String[] args) {
	FTPClient cliente = new FTPClient();
	String servFTP = "ftp.rediris.es";
	System.out.println("Nos conectamos a: " + servFTP);
	String usuario = "anonymous";
	String clave = "anonymous";
	try {
		cliente.connect(servFTP);
        cliente.enterLocalPassiveMode(); //modo pasivo

		boolean login = cliente.login(usuario, clave);
		if (login)
			System.out.println("Login correcto...");
		else {
			System.out.println("Login Incorrecto...");
			cliente.disconnect();
			System.exit(1);
		}
		System.out.println("Directorio actual: "
				         + cliente.printWorkingDirectory());

		FTPFile[] files = cliente.listFiles();
		System.out.println("Ficheros en el directorio actual:"
					+ files.length);
            //array para visualizar el tipo de fichero
		String tipos[] = {"Fichero", "Directorio","Enlace simb."};
		
		for (int i = 0; i < files.length; i++) {
			System.out.println("\t" + files[i].getName() + " => "
					+ tipos[files[i].getType()]);
		}
		boolean logout = cliente.logout();
		if (logout) 
			System.out.println("Logout del servidor FTP...");
                else 
		        System.out.println("Error al hacer Logout...");
		//
		cliente.disconnect();
		System.out.println("Desconectado...");
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}		
  }
}// ..

