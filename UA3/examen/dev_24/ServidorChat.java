import java.io.*;
import java.net.*;


public class ServidorChat
{
	static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {

			//7º EJERCICIO//

		int PUERTO = 44444; //RESPUESTA: el puerto donde se va a conectar el servidor. (Es el puerto que pide el enunciado y el mismo que usa el cliente).

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones

			//8º EJERCICIO//

		ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); //RESPUESTA: con el ComunHilos controlamos las conexiones y los mensajes del chat. (Empezamos con 0 conexiones actuales y 0 conexiones totales).

		while (comun.getCONEXIONES() < MAXIMO) {
			Socket socket = new Socket(); // Se cierra en el HiloServidor

			//9º EJERCICIO//

			socket = servidor.accept(); // esperando cliente
			//RESPUESTA: con este metodo aceptamos la conexion al cliente que se quiera conectar al servidor.

			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);

			//10º EJERCICIO//

			hilo.start(); //RESPUESTA: con este metodo damos inicio al hilo que maneja la conexion con los clientes.
		}
		servidor.close();
	}//main
}//ServidorChat..

