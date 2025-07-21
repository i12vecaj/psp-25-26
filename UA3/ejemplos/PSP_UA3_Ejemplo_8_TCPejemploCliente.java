// El programa cliente en primer lugar envia un mensaje al servidor
// Despues recibe un mensaje del servidor visualizandolo en pantalla
// Se ha simplificado la obtencion de los flujos de entrada y salida

import java.io.*;
import java.net.*;

public class PSP_UA3_Ejemplo_8_TCPejemploCliente  {
  public static void main(String[] args) throws Exception {
	String Host = "localhost";
	int Puerto = 6000;//puerto remoto

	System.out.println("PROGRAMA CLIENTE INICIADO....");
	Socket Cliente = new Socket(Host, Puerto);

	// CREO FLUJO DE SALIDA AL SERVIDOR
	DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

	// ENVIO UN SALUDO AL SERVIDOR
	flujoSalida.writeUTF("Saludos al SERVIDOR DESDE EL CLIENTE");

	// CREO FLUJO DE ENTRADA AL SERVIDOR
	DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

	// EL SERVIDOR ME ENVIA UN MENSAJE
	System.out.println("Recibiendo del SERVIDOR: \n\t" + flujoEntrada.readUTF());

	// CERRAR STREAMS Y SOCKETS
	flujoEntrada.close();
	flujoSalida.close();
	Cliente.close();
  }// main
}//
