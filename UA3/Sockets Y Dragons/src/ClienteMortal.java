import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClienteMortal {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        final String HOST = "10.2.0.121"; // O "localhost"
        final int PUERTO = 9876;
        final int TIMEOUT = 3000; // 3 segundos de espera

        // --- NUEVO: CONFIGURACIÓN DE STRIKES ---
        final int MAX_INTENTOS = 3;
        int intentosFallidos = 0;

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT);
            InetAddress ip = InetAddress.getByName(HOST);

            System.out.println("--- CONECTADO AL SISTEMA ---");
            System.out.println("Si el servidor falla " + MAX_INTENTOS + " veces, nos desconectamos.");

            while (true) {
                // 1. COMPROBACIÓN DE SALUD ANTES DE PREGUNTAR
                if (intentosFallidos >= MAX_INTENTOS) {
                    System.out.println("\n ERROR FATAL: El servidor no responde tras " + MAX_INTENTOS + " intentos.");
                    System.out.println("Cerrando programa automáticamente.");
                    break; // ROMPE EL BUCLE Y SALE
                }

                System.out.print("\nEscribe mensaje ('fin' para salir): ");
                String mensaje = scan.nextLine();

                if (mensaje.equalsIgnoreCase("fin")) {
                    break;
                }

                byte[] envio = mensaje.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(envio, envio.length, ip, PUERTO);
                socket.send(paqueteEnvio);

                byte[] recepcion = new byte[1024];
                DatagramPacket paqueteRecepcion = new DatagramPacket(recepcion, recepcion.length);

                try {
                    // Esperamos respuesta...
                    socket.receive(paqueteRecepcion);

                    // --- SI LLEGAMOS AQUÍ, ES QUE TODO FUE BIEN ---
                    String respuesta = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                    System.out.println(" Servidor: " + respuesta);

                    // IMPORTANTE: Reseteamos el contador porque el servidor está vivo
                    intentosFallidos = 0;

                } catch (SocketTimeoutException e) {
                    // --- SI LLEGAMOS AQUÍ, ES UN STRIKE ---
                    intentosFallidos++; // Sumamos 1 error

                    System.out.println(" TIMEOUT (Intento " + intentosFallidos + "/" + MAX_INTENTOS + ")");
                    System.out.println("El servidor no contestó a tiempo.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}