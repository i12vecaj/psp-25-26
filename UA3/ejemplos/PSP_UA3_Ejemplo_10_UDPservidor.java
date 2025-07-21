import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// Ejecución: java PSP_UA3_Ejemplo_10_UDPservidor.java

public class PSP_UA3_Ejemplo_10_UDPservidor {
  public static void main(String[] argv) throws Exception {

	  // Se crea el socket en el puerto especificado
      DatagramSocket serverSocket = new DatagramSocket(9876);

      // Arrays para recibir y enviar datos
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];

      System.out.println("Servidor esperando mensajes...");

      while(true)
      {
    	  // Se crea un paquete de datagrama
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

          // Se reciben los datos de un cliente
          serverSocket.receive(receivePacket);

          // Se imprimen los datos recibidos
          String sentence = new String( receivePacket.getData());
          System.out.println("Mensaje recibido: " + sentence);

          // Se obtienen los datos de origen del paquete
          InetAddress IPAddress = receivePacket.getAddress();
          int port = receivePacket.getPort();

          // Se modifica la cadena recibida (para convertirlo a mayúsculas)
          String capitalizedSentence = sentence.toUpperCase();
          sendData = capitalizedSentence.getBytes();

          // Se envía el mensaje modificado de vuelta al emisor
          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
          serverSocket.send(sendPacket);
       }
  	}
}
