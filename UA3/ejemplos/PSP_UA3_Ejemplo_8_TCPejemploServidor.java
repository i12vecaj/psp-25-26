// Ejemplo de programa servidor que recibe uyn mensaje de un cliente y lo muestra por pantalla
// Despues envia un mensaje al cliente
// Se han eliminado los try-catch para que resulte mas legible inicialmente,

import java.io.*;
import java.net.*;

public class PSP_UA3_Ejemplo_8_TCPejemploServidor {
  public static void main(String[] arg) throws IOException {
	int numeroPuerto = 6000;// Puerto
	ServerSocket servidor = new ServerSocket(numeroPuerto);
	Socket clienteConectado = null;
	System.out.println("Esperando al cliente.....");
	clienteConectado = servidor.accept();

	// CREO FLUJO DE ENTRADA DEL CLIENTE
	InputStream entrada = null;
	entrada = clienteConectado.getInputStream();
	DataInputStream flujoEntrada = new DataInputStream(entrada);

	// EL CLIENTE ME ENVIA UN MENSAJE
	System.out.println("Recibiendo del CLIENTE: \n\t" + flujoEntrada.readUTF());

	// CREO FLUJO DE SALIDA AL CLIENTE
	OutputStream salida = null;
	salida = clienteConectado.getOutputStream();
	DataOutputStream flujoSalida = new DataOutputStream(salida);

	// ENVIO UN SALUDO AL CLIENTE
	flujoSalida.writeUTF("Saludos al cliente del servidor");

	// CERRAR STREAMS Y SOCKETS
	entrada.close();
	flujoEntrada.close();
	salida.close();
	flujoSalida.close();
	clienteConectado.close();
	servidor.close();
  } // main
}// fin
