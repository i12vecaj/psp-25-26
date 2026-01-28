// Cliente Admin usando UDP: puedes elegir entre enviar un saludo o cerrar el servidor
//
// PASOS (cliente admin):
// 1) Mostrar un menú por consola y leer opción.
// 2) Validar la opción y construir el mensaje correspondiente.
// 3) Resolver host del servidor a IP.
// 4) Crear DatagramSocket del cliente y configurar timeout.
// 5) Enviar mensaje al servidor con DatagramPacket.
// 6) Esperar respuesta del servidor y mostrarla (o informar de timeout).

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClienteAdminUDP {
    public static void main(String[] args) throws Exception {
        // 1) Entrada por teclado.
        Scanner sc = new Scanner(System.in);

        // Datos del servidor.
        String host = "localhost";
        int puerto = 6000;

        // 1) Menú.
        System.out.println("Que mensaje deseas enviar al servidor, usuario Admin?");
        System.out.println("1. Cerrar Servidor");
        System.out.println("2. Saludar al Servidor");

        // 1) Leer opción.
        int opcion = sc.nextInt();

        // 2) Validar rango.
        while (opcion < 1 || opcion > 2) {
            System.out.println("Opcion no valida. Introduce 1 o 2");
            opcion = sc.nextInt();
        }

        // 2) Construir mensaje según opción.
        // - Si se envía exactamente "Cerrar Servidor -1234-", el servidor se cerrará.
        String mensaje = (opcion == 1)
                ? "Cerrar Servidor -1234-"
                : "Saludos al Servidor -Admin-";

        System.out.println("Cliente Admin (UDP) iniciado....");

        // 3) Resolver host a IP.
        InetAddress serverAddr = InetAddress.getByName(host);

        // 4) Crear socket UDP del cliente.
        try (DatagramSocket socket = new DatagramSocket()) {
            // 4) Timeout para receive() (evita bloqueo infinito).
            socket.setSoTimeout(5000);

            System.out.println("-- Informacion del Socket Cliente (UDP) --");
            System.out.println("Puerto local del socket cliente: " + socket.getLocalPort());
            System.out.println("Puerto remoto (destino): " + puerto);
            System.out.println("Direccion ip de la maquina remota: " + serverAddr.getHostAddress());
            System.out.println("----------------------------------------");

            // 5) Codificar a bytes y crear datagrama de salida.
            byte[] out = mensaje.getBytes(StandardCharsets.UTF_8);
            DatagramPacket paqueteSalida = new DatagramPacket(out, out.length, serverAddr, puerto);

            // 5) Enviar datagrama.
            socket.send(paqueteSalida);

            // 6) Preparar recepción.
            byte[] inBuf = new byte[1024];
            DatagramPacket paqueteEntrada = new DatagramPacket(inBuf, inBuf.length);

            // 6) Esperar respuesta.
            socket.receive(paqueteEntrada);

            // 6) Convertir respuesta a String.
            String respuesta = new String(
                    paqueteEntrada.getData(),
                    paqueteEntrada.getOffset(),
                    paqueteEntrada.getLength(),
                    StandardCharsets.UTF_8
            );

            System.out.println("Recibiendo del Server: \n\t" + respuesta);
        } catch (SocketTimeoutException e) {
            System.out.println("Tiempo de espera agotado: no se recibio respuesta del servidor.");
        }
    }
}
