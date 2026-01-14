import java.io.*;
import java.net.*;
import java.sql.SQLOutput;

public class Servidor2 {
    public static void main(String[] arg) throws IOException {
        int numeroPuerto = 6000;
        ServerSocket servidor = new ServerSocket(numeroPuerto);

        System.out.println("Servidor iniciado en puerto " + numeroPuerto);
        Socket clienteConectado = null;
        String mensajeAEnviar = "Saludos al cliente conectado al servidor";
        try {
            do {
                System.out.println("---Esperando a los clientes---");
                clienteConectado = servidor.accept();

                System.out.println("-- Informacion de los puertos de los clientes conectados --");
                System.out.println("Puerto remoto del cliente: " + clienteConectado.getPort());
                System.out.println("Puerto local del cliente: " + clienteConectado.getLocalPort());
                System.out.println("-----------------------------------------------------------");

                DataInputStream flujoEntrada = null;
                DataOutputStream flujoSalida1 = null;

                try {
                    flujoEntrada = new DataInputStream(clienteConectado.getInputStream());

                    System.out.println("-- Mensajes recibido del cliente --");
                    String mensajeCliente = flujoEntrada.readUTF();
                    System.out.println("Recibiendo del CLIENTE: \n\t" + mensajeCliente);

                    if (mensajeCliente.equals("Cerrar Servidor -1234-")) {
                        System.out.println("Comando de cierre recibido. Saliendo del bucle.");
                        mensajeAEnviar="Mensaje de cierre recibido. Servidor cerrando.";
                        flujoSalida1 = new DataOutputStream(clienteConectado.getOutputStream());
                        flujoSalida1.writeUTF(mensajeAEnviar);
                        break;
                    }

                    flujoSalida1 = new DataOutputStream(clienteConectado.getOutputStream());
                    flujoSalida1.writeUTF(mensajeAEnviar);
                } catch (IOException e) {
                    System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
                } finally {
                    if (flujoEntrada != null) {
                        try { flujoEntrada.close();}
                        catch (IOException ignored) {}
                    }
                    if (flujoSalida1 != null) {
                        try { flujoSalida1.close(); }
                        catch (IOException ignored) {}
                    }
                    if (clienteConectado != null) {
                        try { clienteConectado.close();}
                        catch (IOException ignored) {}
                    }
                }
            } while (true);
        } finally {
            if (servidor != null && !servidor.isClosed()) {
                try { servidor.close(); } catch (IOException ignored) {}
            }
            System.out.println("Servidor cerrado.");
        }
    }
}
