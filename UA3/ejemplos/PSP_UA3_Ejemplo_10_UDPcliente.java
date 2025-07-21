import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//Ejecución: java PSP_UA3_Ejemplo_10_UDPcliente.java

public class PSP_UA3_Ejemplo_10_UDPcliente {
  public static void main(String[] argv) throws Exception {
	  // Se crea un búffer para leer la entrada escrita del usuario
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

      // Se crea un datagram socket
      DatagramSocket clientSocket = new DatagramSocket();

      // Se obtiene la dirección localhost (donde está el servidor)
      InetAddress IPAddress = InetAddress.getByName("localhost");

      // Arrays para recibir y enviar datos
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];

      // Se lee información del usuario
      System.out.print("Escribe un mensaje: ");
      String sentence = inFromUser.readLine();
      sendData = sentence.getBytes();

      // Se envía el paquete al servidor
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);

      // Se recibe el paquete de vuelta del servidor
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());

      // Se imprime el mensaje modificado
      System.out.println("Respuesta desde el servidor:" + modifiedSentence);

      // Se cierra el socket
      clientSocket.close();
  }
}
