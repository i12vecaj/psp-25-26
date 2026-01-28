import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorUDP {
    public static void main(String[] args) throws SocketException {
        //Ponemos un puerto no ocupado
        int puerto = 9876;
        //Invocamos el UDP
        try (DatagramSocket serverSocket = new DatagramSocket(puerto)) {
            //creamos el buffer de datos a recibir, siendo 1024 lo que equivale a 1kb
            byte[] buffer = new byte[1024];
            System.out.println("Servidor UDP escuchando en el puerto " + puerto);
            //Invocamos un bucle para mantener encendido el servidor
            boolean running = true;

                while (running) {
                    // 1. Recibir
                    DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                    serverSocket.receive(paqueteRecibido);
                    String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                    System.out.println("Recibido de " + paqueteRecibido.getAddress() + ": " + mensaje);
                    // --- ZONA DE CAOS (AÑADE ESTO) ---
                    // Simulamos que el 30% de las veces, el servidor "pierde" el mensaje
                    // Math.random() genera un número entre 0.0 y 1.0
                    if (Math.random() < 0.3) {
                        System.out.println(":fantasma: [GREMLIN] He robado este paquete. No habrá respuesta.");
                        continue; // ¡Saltamos al inicio del bucle sin responder!
                    }
                    // ----------------------------------
                    // 2. Procesar (Solo si sobrevive al caos)
                    String respuesta = mensaje.toUpperCase();
                    byte[] respuestaBytes = respuesta.getBytes();
                    // 3. Responder
                    DatagramPacket paqueteRespuesta = new DatagramPacket(
                            respuestaBytes,
                            respuestaBytes.length,
                            paqueteRecibido.getAddress(), // ¡Bien hecho aquí!
                            paqueteRecibido.getPort()     // ¡Y aquí!
                    );
                    serverSocket.send(paqueteRespuesta);
                    System.out.println(":marca_de_verificación_blanca: Respuesta enviada");
                }

        //En caso de fallar, saltara el catch error.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
