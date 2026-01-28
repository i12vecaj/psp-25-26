import java.net.*;

public class OdinServidorUDP {

    public static void main(String[] args) {
        int puerto = 7000;

        try {
            DatagramSocket socket = new DatagramSocket(puerto);
            socket.setSoTimeout(5000);

            System.out.println("Odín escucha cuervos en el puerto " + puerto);

            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paquete);

                    String mensaje = new String(paquete.getData(), 0, paquete.getLength());

                    InetAddress direccion = paquete.getAddress();
                    int puertoOrigen = paquete.getPort();

                    String[] partes = mensaje.split(":", 2);
                    String jarl = partes.length > 1 ? partes[0].trim() : "Desconocido";
                    String contenido = partes.length > 1 ? partes[1].trim() : mensaje;

                    System.out.println("Cuervo recibido:");
                    System.out.println("De: " + jarl);
                    System.out.println("Mensaje: " + contenido);
                    System.out.println("IP: " + direccion.getHostAddress());
                    System.out.println("Puerto: " + puertoOrigen);
                    System.out.println("------------------------");

                } catch (SocketTimeoutException e) {
                    System.out.println("Silencio en el Valhalla, ningún cuervo llegó");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}