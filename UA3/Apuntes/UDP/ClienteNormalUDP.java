// Cliente normal usando UDP: envía un saludo al servidor y recibe respuesta
//
// PASOS (cliente normal):
// 1) Definir host y puerto del servidor.
// 2) Resolver el host a una IP (InetAddress).
// 3) Crear un DatagramSocket (puerto local aleatorio) para enviar/recibir.
// 4) (Opcional) Configurar timeout para no bloquearse indefinidamente en receive().
// 5) Codificar el mensaje a bytes (UTF-8) y crear un DatagramPacket de salida.
// 6) Enviar el datagrama con send().
// 7) Preparar un buffer y un DatagramPacket de entrada.
// 8) Recibir la respuesta con receive() (bloqueante hasta respuesta o timeout).
// 9) Convertir bytes recibidos en String y mostrarla.

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class ClienteNormalUDP {
    public static void main(String[] args) throws Exception {
        // 1) Datos del servidor.
        String host = "localhost";
        int puerto = 6000;

        System.out.println("Cliente Fran (UDP) iniciado....");

        // 2) Resolver nombre de host a dirección IP.
        InetAddress serverAddr = InetAddress.getByName(host);

        // 3) Crear el socket UDP del cliente (se cerrará automáticamente al salir del bloque).
        try (DatagramSocket socket = new DatagramSocket()) {
            // 4) Timeout de lectura: si no llega nada en 5s, lanza SocketTimeoutException.
            socket.setSoTimeout(5000);

            System.out.println("-- Informacion del Socket Cliente (UDP) --");
            System.out.println("Puerto local del socket cliente: " + socket.getLocalPort());
            System.out.println("Puerto remoto (destino): " + puerto);
            System.out.println("Direccion ip de la maquina remota: " + serverAddr.getHostAddress());
            System.out.println("----------------------------------------");

            // 5) Mensaje a enviar y conversión a bytes.
            String mensaje = "Saludos al Servidor -Fran-";
            byte[] out = mensaje.getBytes(StandardCharsets.UTF_8);

            // 5) Paquete datagrama a enviar (bytes + IP destino + puerto destino).
            DatagramPacket paqueteSalida = new DatagramPacket(out, out.length, serverAddr, puerto);

            // 6) Envío del datagrama al servidor.
            socket.send(paqueteSalida);

            // 7) Preparar buffer y paquete para recibir.
            byte[] inBuf = new byte[1024];
            DatagramPacket paqueteEntrada = new DatagramPacket(inBuf, inBuf.length);

            // 8) Esperar respuesta del servidor (bloquea hasta recibir o timeout).
            socket.receive(paqueteEntrada);

            // 9) Convertir los bytes recibidos (length real) a String.
            String respuesta = new String(
                    paqueteEntrada.getData(),
                    paqueteEntrada.getOffset(),
                    paqueteEntrada.getLength(),
                    StandardCharsets.UTF_8
            );

            System.out.println("Recibiendo desde el server: \n\t" + respuesta);
        } catch (SocketTimeoutException e) {
            System.out.println("Tiempo de espera agotado: no se recibio respuesta del servidor.");
        }
    }
}
