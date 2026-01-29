import java.io.*;
import java.net.*;

/**
 * Aplicación de Servidor para gestionar múltiples entradas de usuarios
 * e imprimir detalles de los canales de comunicación usados.
 */
public class Servidor {
    // Puerto de escucha estático
    private static final int PORT = 6000;

    public static void main(String[] args) {
        ServerSocket miServidor = null;
        int idUsuario = 1;

        try {
            // Inicialización del socket servidor
            miServidor = new ServerSocket(PORT);
            System.out.println(">>> CENTRAL TCP ACTIVA <<<");
            System.out.println("Escuchando peticiones en puerto: " + PORT);

            // Bucle persistente de escucha
            boolean activo = true;
            while (activo) {
                System.out.println("\nEsperando al integrante número " + idUsuario + "...");

                // Bloqueo hasta recibir conexión
                Socket enlaceCliente = miServidor.accept();

                // Procesa y muestra los metadatos del enlace
                imprimirDetallesEnlace(enlaceCliente, idUsuario);

                // Envío de paquete de bienvenida
                notificarCliente(enlaceCliente, "Saludos. Eres el usuario #" + idUsuario);

                idUsuario++;
            }

        } catch (IOException error) {
            System.err.println("Excepción detectada: " + error.getMessage());
        } finally {
            // Procedimiento de apagado de emergencia del puerto
            if (miServidor != null && !miServidor.isClosed()) {
                try {
                    miServidor.close();
                    System.out.println("Puerta de red liberada.");
                } catch (IOException e) {
                    System.err.println("Fallo al liberar recurso.");
                }
            }
        }
    }

    private static void imprimirDetallesEnlace(Socket s, int id) {
        System.out.println("--- Reporte Conexión #" + id + " ---");
        // Datos de los puertos involucrados
        System.out.println("Canal Local: " + s.getLocalPort());
        System.out.println("Canal Externo: " + s.getPort());

        // Identificación de red del terminal remoto
        InetAddress ipInfo = s.getInetAddress();
        System.out.println("IP Remota: " + ipInfo.getHostAddress());
        System.out.println("Dominio: " + ipInfo.getHostName());
    }

    private static void notificarCliente(Socket s, String texto) {
        try {
            // Flujo de salida para strings UTF
            DataOutputStream streamSalida = new DataOutputStream(s.getOutputStream());
            streamSalida.writeUTF(texto);
        } catch (IOException ex) {
            System.err.println("No se pudo enviar el paquete.");
        }
    }
}