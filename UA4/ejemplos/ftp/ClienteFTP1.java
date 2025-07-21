import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.*;

/*
 https://sites.google.com/site/nikos3194rfcindex/home/old-school/ftp
 ftp.uv.es
 ftp.unavarra.es
 ftp.rediris.es
 ftp.uma.es
 ftp.udc.es
 ftp.dit.upm.es
 ftp.freenet.de

 */
public class ClienteFTP1 {
	public static void main(String[] args) throws SocketException, IOException {

		FTPClient cliente = new FTPClient();
		String servFTP = "ftp.rediris.es"; // servidor FTP
		System.out.println("Nos conectamos a: " + servFTP);
		cliente.connect(servFTP);

		// respuesta del servidor FTP
		System.out.print(cliente.getReplyString());
		// código de respuesta
		int respuesta = cliente.getReplyCode();

		System.out.println("Respuesta: "+respuesta);

		// comprobación del código de respuesta
		if (!FTPReply.isPositiveCompletion(respuesta)) {
			cliente.disconnect();
			System.out.println("Conexión rechazada: " + respuesta);
			System.exit(0);
		}
		// desconexión del servidor FTP
		cliente.disconnect();
		System.out.println("Conexión finalizada.");
	}

}// ..