// Para recibir el datagrama usamos tambien un DataGramSocket.
// En primer lugar habra que enlazar el socket al puertoi por el que se va a recibir el mensaje, en este caso el 12345.
// Despues se construye el datagrama para recepcion y mediante el metodo receive() obtenemos los datos
// Luego obtenemos la lontigutd, la cadena y visualizamos los puertos origen y destino del mensaje.

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PSP_UA3_Ejemplo_9_UDPreciboDatagrama {
  public static void main(String[] argv) throws Exception {
    byte[] bufer = new byte[1024];//para recibir el datagrama

    //Asocio el socket al puerto 12345
     DatagramSocket socket = new DatagramSocket(12345);

    //construyo datagrama a recibir
    DatagramPacket recibo = new DatagramPacket(bufer, bufer.length);
    System.out.println("Esperando Datagrama .......... ");

    socket.receive(recibo);//recibo datagrama

    int bytesRec = recibo.getLength();//obtengo numero de bytes
    String paquete= new String(recibo.getData());//obtengo String

    System.out.println("Numero de Bytes recibidos: "+ bytesRec);
    System.out.println("Contenido del Paquete: " + paquete.trim());
    System.out.println("Puerto origen del mensaje: " + recibo.getPort());
    System.out.println("IP de origen: " + recibo.getAddress().getHostAddress());
    System.out.println("Puerto destino del mensaje: " + socket.getLocalPort());

    socket.close(); //cierro el socket

  }
}


