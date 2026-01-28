import java.io.*;
import java.net.*;


public class ServidorChat
{
	static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {
		int PUERTO = 44444; //Este es el puerto de escucha del servidor, q debe ser el mismo que el del cliente, asi que lo unico que he hecho es poner el mismo puerto que salia en el Servidor Cliente

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
		ComunHilos comun = new ComunHilos(MAXIMO, 5, 5, tabla); //este es el objeto comun q comparten todos los hilos, y lo unico que he hecho ha sido poner el new ComunHilos

		while (comun.getCONEXIONES() < MAXIMO) {
			Socket socket = new Socket(); // Se cierra en el HiloServidor
			socket = servidor.accept(); // esperando cliente, y lo que he hecho ha sido usar el accept para aceptar conexiones, ya que no supera el maximo de conexiones
			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);
			hilo.start(); // se inicia el hilo para que haga la tarea de atender al cliente, asi que lo unico que he hecho ha sido iniciar el hilo con el .start() para que sea concurrente
		}
		servidor.close();
	}//main
}//ServidorChat..

