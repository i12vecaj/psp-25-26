import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidorChat extends Thread {
	DataInputStream fentrada;
	Socket socket = null;
	ComunHilos comun;

	public HiloServidorChat(Socket s, ComunHilos comun) {
		this.socket = s;
		this.comun = comun;
		try {
			// CREO FLUJO DE entrada para leer los mensajes
			fentrada = new DataInputStream(socket.getInputStream()); // Obtengo el flujo de entrada del socket
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
					// Aquí se ve el ciclo de vida TCP:
					// Si el cliente cierra forzosamente (corte de luz, error, ctrl+C), el flujo lanza una IOException. Si no decrementamos ACTUALES
					// en el catch, el servidor seguiría pensando que ese usuario ocupa un hueco, y eventualmente se llenaría y nadie más podría entrar.
					System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
				}

				comun.setUltimo(cadena);
				EnviarMensajesaTodos(comun.getUltimo());

			} catch (Exception e) {// EL CLIENTE SE DESCONECTA A LO BRUTO Ctrl+C o similar
				comun.setACTUALES(comun.getACTUALES() -1); // Decrementar contador si hay error brusco
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
	private void EnviarMensajesaTodos(String texto) { // Defino el parámetro que necesita, texto
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