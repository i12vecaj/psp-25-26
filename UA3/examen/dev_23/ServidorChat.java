import java.io.*;
import java.net.*;


public class ServidorChat
{
	static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {
		int PUERTO = 44444; // Ponemos el puerto que se ha pedido en el ejercicio, para conectar el cliente con el servidor

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
		ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); // Aquí creamos el objeto de la clase Comunhilos que usaran todos los hilos

		while (comun.getCONEXIONES() < MAXIMO) {
			Socket socket = new Socket(); // Se cierra en el HiloServidor
			socket = servidor.accept(); // Este comando se utiliza para esperar a que el cliente se una al servidor

			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);
			hilo.start(); // Aqui iniciamos el hilo con .start() para que el usuario se una al chat
		}
		servidor.close();
	}//main
}//ServidorChat..

