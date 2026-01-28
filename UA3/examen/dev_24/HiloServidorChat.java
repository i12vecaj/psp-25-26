import java.io.*;
import java.net.*;

public class HiloServidorChat extends Thread {
	DataInputStream fentrada;
	Socket socket = null;
	ComunHilos comun;

	public HiloServidorChat(Socket s, ComunHilos comun) {
		this.socket = s;
		this.comun = comun;
		try {
			// CREO FLUJO DE entrada para leer los mensajes

		//4º EJERCICIO//

			fentrada = new DataInputStream(socket.getInputStream());//RESPUESTA: con socket.getInputStream() conseguimos que el flujo empiece a leer desde el socket.
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
		}
	}// ..

	public void run() {
		System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());

		// NADA MAS CONECTARSE LE ENVIO TODOS LOS MENSAJES
		String texto = comun.getMensajes();
		EnviarMensajesaTodos(texto);

		while (true) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF();
				if (cadena.trim().equals("*")) {// EL CLIENTE SE DESCONECTA
					comun.setACTUALES(comun.getACTUALES() - 1);
					System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
				}

				comun.setUltimo(cadena);
				EnviarMensajesaTodos(comun.getUltimo());

			} catch (Exception e) {// EL CLIENTE SE DESCONECTA A LO BRUTO Ctrl+C o similar

				//5º EJERCICIO//

				comun.setACTUALES(comun.getACTUALES() - 1); //RESPUESTA: si el cliente se desconeta de manera forzada, con el -1 decrementamos el numero de conexiones actuales.
				System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
				System.out.println("ERROR: " + e.getMessage());
				break;
			}
		} // fin while

		// se cierra el socket del cliente
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// run

	// ENVIA LOS MENSAJES DEL CHAT A LOS CLIENTES

	//6º EJERCICIO//

	private void EnviarMensajesaTodos(String texto) { //RESPUESTA: este metodo envia el mensaje a todos los clientes que esten conectados.
		int i;
		// recorremos tabla de sockets para enviarles los mensajes
		for (i = 0; i < comun.getCONEXIONES(); i++) {
			Socket s1 = comun.getElementoTabla(i);
			if (!s1.isClosed()) {
				try {
					DataOutputStream fsalida = new DataOutputStream(s1.getOutputStream());
					fsalida.writeUTF(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // for

	}// EnviarMensajesaTodos

}// ..HiloServidorChat