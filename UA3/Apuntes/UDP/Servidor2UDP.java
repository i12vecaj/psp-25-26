// Servidor usando UDP: recibe mensajes y contesta; se cierra si recibe "Cerrar Servidor -1234-"
//
// PASOS (servidor):
// 1) Crear un DatagramSocket ligado a un puerto (6000) para escuchar datagramas UDP.
// 2) Preparar un buffer para recibir datos (byte[]).
// 3) Esperar en bucle: receive() bloquea hasta que llega un datagrama.
// 4) Obtener IP y puerto del cliente desde el DatagramPacket recibido.
// 5) Convertir los bytes recibidos a String (UTF-8) usando offset y length del paquete.
// 6) Aplicar la lógica: si llega el comando de cierre, responder y salir del bucle.
// 7) Si no, construir respuesta por defecto y enviarla al IP/puerto del cliente.
// 8) Al salir (break o fin), el try-with-resources cierra el socket automáticamente.

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Servidor2UDP {
    public static void main(String[] arg) throws IOException {
        // 1) Puerto donde el servidor escuchará peticiones UDP.
        int numeroPuerto = 6000;

        // Mensaje que se enviará a cualquier cliente (si no es comando de cierre).
        String mensajePorDefecto = "Saludos al cliente conectado al servidor";

        System.out.println("Servidor UDP iniciado en puerto " + numeroPuerto);

        // 1) Socket UDP del servidor ligado al puerto; se cerrará automáticamente al salir del bloque.
        try (DatagramSocket servidor = new DatagramSocket(numeroPuerto)) {
            // 2) Buffer reutilizable para recibir datos.
            byte[] bufferEntrada = new byte[1024];

            // 3) Bucle principal del servidor: atiende datagramas uno a uno.
            while (true) {
                System.out.println("---Esperando a los clientes (UDP)---");

                // 3) Paquete donde se almacenará el datagrama entrante.
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

                // 3) Espera bloqueante: cuando llega un datagrama, se rellena paqueteEntrada.
                servidor.receive(paqueteEntrada);

                // 4) Datos de retorno para poder contestar al cliente.
                InetAddress ipCliente = paqueteEntrada.getAddress();
                int puertoCliente = paqueteEntrada.getPort();

                System.out.println("-- Informacion del cliente (UDP) --");
                System.out.println("IP cliente: " + ipCliente.getHostAddress());
                System.out.println("Puerto cliente: " + puertoCliente);
                System.out.println("Puerto local (servidor): " + servidor.getLocalPort());
                System.out.println("----------------------------------");

                // 5) Convertimos exactamente los bytes recibidos (length) a String.
                String mensajeCliente = new String(
                        paqueteEntrada.getData(),
                        paqueteEntrada.getOffset(),
                        paqueteEntrada.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println("-- Mensaje recibido del cliente --");
                System.out.println("Recibiendo del CLIENTE: \n\t" + mensajeCliente);

                String respuesta;

                // 6) Lógica del servidor: si llega el comando especial, se responde y se cierra.
                if ("Cerrar Servidor -1234-".equals(mensajeCliente)) {
                    System.out.println("Comando de cierre recibido. Cerrando servidor.");
                    respuesta = "Mensaje de cierre recibido. Servidor cerrando.";

                    // 7) Convertir respuesta a bytes y enviarla al cliente.
                    byte[] out = respuesta.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket paqueteSalida = new DatagramPacket(out, out.length, ipCliente, puertoCliente);
                    servidor.send(paqueteSalida);

                    // 6/8) Salimos del bucle; el socket se cerrará por try-with-resources.
                    break;
                }

                // 7) Respuesta normal para cualquier otro mensaje.
                respuesta = mensajePorDefecto;
                byte[] out = respuesta.getBytes(StandardCharsets.UTF_8);
                DatagramPacket paqueteSalida = new DatagramPacket(out, out.length, ipCliente, puertoCliente);
                servidor.send(paqueteSalida);
            }
        }

        System.out.println("Servidor cerrado.");
    }
}
