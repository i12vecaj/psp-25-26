import java.io.*;
import java.net.*;


public class ServidorChat
{
	static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {
		int PUERTO = 44444;
		//Le he puesto el puerto 4444 ya que es el que se conecta mediante la clase ClienteChat.java

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
		ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla);
		//creo un nuevo objeto de la clase Comun hilos que maneja el numero de conexiones del programa

		while (comun.getCONEXIONES() < MAXIMO) {
			Socket socket = new Socket(); // Se cierra en el HiloServidor
			socket = servidor.accept(); // esperando cliente
			//He añadido el metodo servidor.accept ya que lo que quiero es que bloquee hasta que un cliente se conecte

			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);
			hilo.start(); //Inicializo el hilo del chat del servidor arriba y aqui arranco el hilo con el metodo start()
		}
		servidor.close();
	}//main
}//ServidorChat..

