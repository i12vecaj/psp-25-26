import java.io.*;
import java.net.*;


public class ServidorChat
{
	static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {
		int PUERTO = 44444; // puerto de conexion que pide el readme

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
		ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); // objeto comun para los hilos

		while (comun.getCONEXIONES() < MAXIMO) {
		Socket socket = servidor.accept(); // CAMBIADO: acepta conexion directamente del cliente
 
			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);
			hilo.start();
		}
		servidor.close();
	}//main
}//ServidorChat..

