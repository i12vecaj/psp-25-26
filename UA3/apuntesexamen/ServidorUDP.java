import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(6000);
        System.out.println("Servidor UDP esperando...");

        byte[] buffer = new byte[1024];
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

        socket.receive(paquete); // recibe mensaje

        String mensaje = new String(
                paquete.getData(), 0, paquete.getLength()
        );
        System.out.println("Cliente dice: " + mensaje);

        String respuesta = "Mensaje UDP recibido 👍";
        byte[] bufferRespuesta = respuesta.getBytes();

        DatagramPacket paqueteRespuesta = new DatagramPacket(
                bufferRespuesta,
                bufferRespuesta.length,
                paquete.getAddress(),
                paquete.getPort()
        );

        socket.send(paqueteRespuesta);
        socket.close();
    }
}
