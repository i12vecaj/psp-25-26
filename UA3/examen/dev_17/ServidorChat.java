import java.io.*;
import java.net.*;


public class ServidorChat
{
	static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {
		int PUERTO = 44444;  //Aquí he escrito el puerto al que se conecta el cliente

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
		ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); //He añadido ComunHilos que es la clase de la variable comun y el nombre es el mismo porque no se hace polimorfismo con niguna otra clase

		while (comun.getCONEXIONES() < MAXIMO) {
			Socket socket = new Socket(); // Se cierra en el HiloServidor
			socket = servidor.accept();/* RELLENAR */; // esperando cliente

			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);
			hilo.start(); /* RELLENAR */; //Inicio el hilo con start() para que pueda haber concurrencia en las conexiones
		}
		servidor.close();
	}//main
}//ServidorChat..

