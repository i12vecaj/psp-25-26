// EXPLICACIÓN PERSONAL: Después de crear el objeto HiloServidorChat (que hereda de Thread), es obligatorio llamar a start() para
// que la Máquina Virtual de Java (JVM) inicie otra ejecución (call stack). Si llamamos a run(), se ejecutaría secuencialmente
// bloqueando elservidor principal.


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorChat
{
	static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

	public static void main(String args[]) throws IOException {
		int PUERTO = 44444; // Puerto pactado

		ServerSocket servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

		Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
		ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); // Creo el objeto compartido

		while (comun.getCONEXIONES() < MAXIMO) {
			Socket socket = new Socket(); // Se cierra en el HiloServidor
			socket = servidor.accept(); // esperando cliente y aceptando la conexión.

			comun.addTabla(socket, comun.getCONEXIONES());
			comun.setACTUALES(comun.getACTUALES() + 1);
			comun.setCONEXIONES(comun.getCONEXIONES() + 1);

			HiloServidorChat hilo = new HiloServidorChat(socket, comun);
			hilo.start(); //  Arranco el hilo
		}
		servidor.close();
	}//main
}//ServidorChat..

