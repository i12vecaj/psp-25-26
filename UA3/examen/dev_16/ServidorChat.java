import java.io.*;
import java.net.*;

public class ServidorChat {
	static final int MAXIMO = 5; // MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {
		int PUERTO = 44444; // Puerto elegido en el README y en el cliente

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; // para controlar las conexiones
		ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); // Constructor

		while (comun.getCONEXIONES() < MAXIMO) {

			/*
			 * Socket socket = new Socket();
			 * socket = servidor.accept();
			 * Con este código había un memory-leak pq el socket nunca se cerraba
			 * he hecho que directamente se cree y se "acepte"
			 */

			Socket socket = servidor.accept(); // El servidor se pone a la espera de que un cliente
												// se conecte y se le acepta la conexión

			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);
			hilo.start();
		}
		servidor.close();
	}// main
}// ServidorChat..
