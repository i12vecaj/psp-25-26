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
			fentrada = new DataInputStream(socket.getInputStream()); // aqui lo que se hace es obtener el flujo de entrada del socket, y lo he hecho con un DataInputStream
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
				comun.setACTUALES(comun.getACTUALES() - 1); // aqui lo que hago es quitar una conexión actual porque el cliente se ha desconectado, aunque haya saltado la excepción de que lo ha hecho a lo bruto
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
	private void EnviarMensajesaTodos(String texto){ // texto a enviar, lo que recibe la funcion es el ultimo mensaje, asi que lo que he hecho es pasarle una variable de tipo string, para que envie ese mensaje a todos los clientes conectados
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

//He llegado a saber que el mecanismo de cliente funciona de manera anomala, ya que no puedo abrir varios clientes en la misma maquina, pero el servidor funciona correctamente y acepta conexiones y envía mensajes a los clientes conectados, pero cuando sales de un cliente no te deja abrir otro, pero no he podido solucionar ese problema.