import java.net.*;
import java.util.Scanner;

public class JarlClienteUDP {

    public static void main(String[] args) {
        String servidor = "localhost";
        int puertoServidor = 7000;

        try {
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nombre del Jarl: ");
            String nombreJarl = scanner.nextLine();

            System.out.print("Mensaje para Odín: ");
            String mensaje = scanner.nextLine();

            String mensajeCompleto = nombreJarl + ": " + mensaje;
            byte[] datos = mensajeCompleto.getBytes();

            InetAddress direccion = InetAddress.getByName(servidor);

            DatagramPacket paquete = new DatagramPacket(
                    datos,
                    datos.length,
                    direccion,
                    puertoServidor
            );

            socket.send(paquete);
            System.out.println("Cuervo enviado por " + nombreJarl);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}