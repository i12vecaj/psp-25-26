import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket servidorSocket = new ServerSocket(5000);
            System.out.println("Servidor iniciado y esperando conexiones...");

            while (true) {
                Socket clienteSocket = servidorSocket.accept();
                DataInputStream flujoEntrada = new DataInputStream(clienteSocket.getInputStream());
                DataOutputStream flujoSalida = new DataOutputStream(clienteSocket.getOutputStream());

                String mensajeRecibido = flujoEntrada.readUTF();

                if (mensajeRecibido.equalsIgnoreCase("localhost")) {
                    flujoSalida.writeUTF("Cerrando conexion");
                    clienteSocket.close();
                    break;
                }

                String respuestaFinal = procesarInformacion(mensajeRecibido);
                flujoSalida.writeUTF(respuestaFinal);
                clienteSocket.close();
            }
            servidorSocket.close();
        } catch (IOException excepcionEntradaSalida) {
            System.out.println("Error en el servidor: " + excepcionEntradaSalida.getMessage());
        }
    }

    private static String procesarInformacion(String entrada) {
        StringBuilder constructorRespuesta = new StringBuilder();
        try {
            if (entrada.startsWith("http://") || entrada.startsWith("https://")) {
                URL objetoUrl = new URL(entrada);
                constructorRespuesta.append("Protocolo: ").append(objetoUrl.getProtocol()).append("");
                constructorRespuesta.append("Host: ").append(objetoUrl.getHost()).append("");
                constructorRespuesta.append("Puerto: ").append(objetoUrl.getPort() == -1 ? objetoUrl.getDefaultPort() : objetoUrl.getPort()).append("\n");

                InetAddress ipDesdeUrl = InetAddress.getByName(objetoUrl.getHost());
                constructorRespuesta.append("IP Asociada: ").append(ipDesdeUrl.getHostAddress());
            } else {
                InetAddress direccionIp = InetAddress.getByName(entrada);
                constructorRespuesta.append("Nombre Host: ").append(direccionIp.getHostName()).append("");
                constructorRespuesta.append("IP: ").append(direccionIp.getHostAddress()).append("");
                constructorRespuesta.append("Nombre Canonico: ").append(direccionIp.getCanonicalHostName());
            }
        } catch (UnknownHostException excepcionHost) {
            return "No se pudo resolver el host.";
        } catch (Exception excepcionGeneral) {
            return "Formato no valido o error de red.";
        }
        return constructorRespuesta.toString();
    }
}