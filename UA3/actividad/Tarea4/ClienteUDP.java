import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        Scanner scanercito = new Scanner(System.in);
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);
            InetAddress direccion = InetAddress.getByName("localhost");
            String textito = "";

            while (!textito.equals("*")) {
                System.out.println("Introduce un mensaje (* para salir): ");
                textito = scanercito.nextLine();
                byte[] mensajito = textito.getBytes();
                DatagramPacket paquete = new DatagramPacket(
                    mensajito, mensajito.length, direccion, 6002);
                socket.send(paquete);

                if (textito.equals("*")) {
                    break;
                }

                byte[] buffer = new byte[1024];
                DatagramPacket paqueteRespuesta = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRespuesta);
                String respuesta = new String(
                    paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            }
    

} catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    } 
        scanercito.close();
    }
}

