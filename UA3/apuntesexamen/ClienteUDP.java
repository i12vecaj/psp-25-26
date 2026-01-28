import java.net.*;

public class ClienteUDP {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();

        String mensaje = "Hola servidor UDP";
        byte[] buffer = mensaje.getBytes();

        InetAddress direccion = InetAddress.getByName("localhost");

        DatagramPacket paquete = new DatagramPacket(
                buffer, buffer.length, direccion, 6000);

        socket.send(paquete);

        byte[] bufferRespuesta = new byte[1024];
        DatagramPacket respuesta = new DatagramPacket(
                bufferRespuesta, bufferRespuesta.length);

        socket.receive(respuesta);

        String mensajeRespuesta = new String(
                respuesta.getData(), 0, respuesta.getLength());
        System.out.println("Servidor dice: " + mensajeRespuesta);

        socket.close();
    }
}
