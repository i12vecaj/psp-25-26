// Ejemplo de construccion de datagrama de envio.
// El mensaje se envia o bien a localhost o a una IP definida, que debe estar esperando en el puerto 12345
// El mensaje esta formado por la cadena "Enviando Saludos !!" que es necesario codificar en una secuencia de bytes.
// Despues sera necesario calcular la longitud del mensaje a enviar.
// Con InetAddress.getLocalHost() obtengo la direccioin IP del host al que enviare el mensaje, en este caso localhost.

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPenvioDatagrama{
  public static void main(String[] argv) throws Exception {
	  int port = 12345; //puerto por el que escucha
	  InetAddress destino = InetAddress.getLocalHost();//IP host local
	  //InetAddress destino = InetAddress.getByName("80.120.54.1"); //Para definir el destino de un host con una IP concreta

	  byte[] mensaje = new byte[1024]; //matriz de bytes
	  String Saludo = "Enviando Saludos !!";
	  mensaje = Saludo.getBytes();  //codificarlo a bytes para enviarlo

	  //construyo el datagrama a enviar
	  DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, destino, port);

      DatagramSocket socket = new DatagramSocket(34567);
	  socket.send(envio);//envio datagrama a destino y port
	  socket.close();
  }
}

