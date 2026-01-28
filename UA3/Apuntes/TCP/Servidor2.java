// Servidor TCP: acepta conexiones, lee un mensaje del cliente y responde.
// Se cierra si recibe exactamente: "Cerrar Servidor -1234-".
//
// PASOS (servidor TCP):
// 1) Crear un ServerSocket ligado a un puerto (6000) para escuchar conexiones.
// 2) Entrar en un bucle y esperar clientes con accept() (bloqueante).
// 3) Con el Socket del cliente, obtener streams de entrada/salida.
// 4) Leer el mensaje del cliente (readUTF) y decidir la respuesta.
// 5) Enviar la respuesta (writeUTF).
import java.io.*;
import java.net.*;

public class Servidor2 {
    public static void main(String[] arg) throws IOException {
        // 1) Puerto en el que el servidor quedará escuchando.
        int numeroPuerto = 6000;

        // 1) Socket servidor (escucha de conexiones TCP entrantes).
        ServerSocket servidor = new ServerSocket(numeroPuerto);

        System.out.println("Servidor iniciado en puerto " + numeroPuerto);

        // Socket para la conexión actual (un cliente por iteración del bucle).
        Socket clienteConectado = null;

        // Mensaje que se envía si no es comando de cierre.
        String mensajeAEnviar = "Saludos al cliente conectado al servidor";

        try {
            // 2) Bucle: el servidor atiende conexiones una por una.
            do {
                System.out.println("---Esperando a los clientes---");

                // 2) Espera bloqueante hasta que un cliente se conecte.
                clienteConectado = servidor.accept();

                System.out.println("-- Informacion de los puertos de los clientes conectados --");
                System.out.println("Puerto remoto del cliente: " + clienteConectado.getPort());
                System.out.println("Puerto local del cliente: " + clienteConectado.getLocalPort());
                System.out.println("-----------------------------------------------------------");

                DataInputStream flujoEntrada = null;
                DataOutputStream flujoSalida1 = null;

                try {
                    // 3) Stream de entrada para leer datos del cliente.
                    flujoEntrada = new DataInputStream(clienteConectado.getInputStream());

                    // 4) Leer el mensaje enviado por el cliente.
                    System.out.println("-- Mensajes recibido del cliente --");
                    String mensajeCliente = flujoEntrada.readUTF();
                    System.out.println("Recibiendo del CLIENTE: \n\t" + mensajeCliente);

                    // 4) Preparar stream de salida para responder.
                    flujoSalida1 = new DataOutputStream(clienteConectado.getOutputStream());

                    // 4/7) Si llega el comando de cierre, responder y salir del bucle.
                    if (mensajeCliente.equals("Cerrar Servidor -1234-")) {
                        System.out.println("Comando de cierre recibido. Saliendo del bucle.");
                        mensajeAEnviar = "Mensaje de cierre recibido. Servidor cerrando.";

                        // 5) Enviar respuesta de cierre.
                        flujoSalida1.writeUTF(mensajeAEnviar);

                        // 7) Romper bucle principal: el servidor se cerrará en finally.
                        break;
                    }

                    // 5) Respuesta normal.
                    flujoSalida1.writeUTF(mensajeAEnviar);
                } catch (IOException e) {
                    System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
                } finally {
                    // 6) Cierre ordenado de recursos de la conexión.
                    if (flujoEntrada != null) {
                        try { flujoEntrada.close(); }
                        catch (IOException ignored) {}
                    }
                    if (flujoSalida1 != null) {
                        try { flujoSalida1.close(); }
                        catch (IOException ignored) {}
                    }
                    if (clienteConectado != null) {
                        try { clienteConectado.close(); }
                        catch (IOException ignored) {}
                    }
                }
            } while (true);
        } finally {
            // 8) Cerrar el socket servidor si sigue abierto.
            if (servidor != null && !servidor.isClosed()) {
                try { servidor.close(); } catch (IOException ignored) {}
            }
            System.out.println("Servidor cerrado.");
        }
    }
}
