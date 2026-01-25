import java.net.*;

public class ClienteUDP {
    public static void main(String[] args) throws Exception {

        // 1️⃣ Creamos el socket UDP (no necesita puerto fijo)
        DatagramSocket socket = new DatagramSocket();

        // 2️⃣ Mensaje que queremos enviar
        byte[] mensaje = "Hola servidor UDP".getBytes();

        // 3️⃣ Dirección del servidor
        InetAddress direccion = InetAddress.getByName("localhost");

        // 4️⃣ Creamos el paquete con datos, dirección y puerto
        DatagramPacket paquete = new DatagramPacket(
                mensaje, mensaje.length, direccion, 6000);

        // 5️⃣ Enviamos el paquete
        socket.send(paquete);

        // 6️⃣ Cerramos el socket
        socket.close();
    }
}
