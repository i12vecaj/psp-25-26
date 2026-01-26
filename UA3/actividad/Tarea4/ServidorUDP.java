import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(6002)) {
          byte[] buffer = new byte[1024];
          boolean palante = true;

            while (palante) {
               DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
               socket.receive(paquete);

               String mensajito = new String (paquete.getData(), 
               0, paquete.getLength());

               if(mensajito.equals("*")){
                break;
               }

               mensajito = mensajito.toUpperCase();
                byte[] datos = mensajito.getBytes();

                System.out.println("Mensaje recibido: " + mensajito);

                DatagramPacket paqueteRespuesta = new DatagramPacket(
                    datos, datos.length, paquete.getAddress(), paquete.getPort());
                socket.send(paqueteRespuesta);
            }
    }
    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
    }

}
