// El siguiente ejemplo crea un socket de servidor y lo enlaza al puerto 6000.
// Visualiza el puerto por el que se esperan las conexiones y espera que ser conecten 2 clientes.

import java.io.*;
import java.net.*;

public class PSP_UA3_Ejemplo_7_ServerSocket {
  public static void main(String[] arg) throws IOException {
	int Puerto = 6000;// Puerto
	ServerSocket Servidor = new ServerSocket(Puerto);
    System.out.println("Escuchando en " + Servidor.getLocalPort());

    Socket cliente1 = Servidor.accept();
    // Realizar Acciones con cliente 1

    Socket cliente2 = Servidor.accept();
    // Realizar Acciones con cliente 2

    Servidor.close();
  }

}