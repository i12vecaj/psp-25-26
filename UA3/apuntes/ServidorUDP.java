import java.net.*;   // Clases para UDP

public class ServidorUDP {
    public static void main(String[] args) throws Exception {

        // 1️⃣ Creamos el DatagramSocket en el puerto 6000
        DatagramSocket socket = new DatagramSocket(6000);

        // 2️⃣ Creamos un buffer para recibir los datos
        byte[] buffer = new byte[1024];

        // 3️⃣ Creamos el paquete donde se guardará el mensaje recibido
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

        // 4️⃣ receive() espera a que llegue un datagrama
        socket.receive(paquete);

        // 5️⃣ Convertimos los bytes a String
        String mensaje = new String(
                paquete.getData(), 0, paquete.getLength());

        System.out.println("Cliente dice: " + mensaje);

        // 6️⃣ Cerramos el socket
        socket.close();
    }
}
