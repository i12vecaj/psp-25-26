import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PSP_UA3_Ejemplo_11_UDPservidorEjemplo2 {

	public static void main(String[] args) throws IOException {

		// Asocio el socket al puerto 12345
		DatagramSocket socket = new DatagramSocket(12345);

		System.out.println("Servidor Esperando Datagrama .......... ");
		DatagramPacket recibo;

		byte[] bufer = new byte[1024]; // para recibir el datagrama
		recibo = new DatagramPacket(bufer, bufer.length);
		socket.receive(recibo); //recibo datagrama

	    String mensaje = new String(recibo.getData()).trim();// obtengo String

		System.out.println("Servidor Recibe:" + mensaje);


		//cuento el número de letras a
		int contador=0;
		for(int i=0; i < mensaje.length(); i++ )
			if(mensaje.charAt(i)=='a')
					contador++;

		// DIRECCION ORIGEN DEL MENSAJE
		InetAddress IPOrigen = recibo.getAddress();
		int puerto = recibo.getPort();

		// ENVIANDO DATAGRAMA AL CLIENTE
		System.out.println("Enviando número de apariciones de la letra a=> " + contador);
		byte b = (byte) contador; // paso entero a byte
		byte[] enviados = new byte[2];
		enviados[0] = b;

		DatagramPacket envio = new DatagramPacket(enviados, enviados.length, IPOrigen, puerto);
		socket.send(envio);

		// CERRAR STREAMS Y SOCKETS
		System.out.println("Cerrando conexión...");
		socket.close();

	}

}