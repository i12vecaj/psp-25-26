package EcoDragonMalasPracticas;

import java.io.*;
import java.net.*;

public class ServidorTCP {

    private static final int PUERTO = 5000;

    public static void main(String[] args) {

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {

            System.out.println("Servidor iniciado en puerto " + PUERTO);
            System.out.println("Sin límites de clientes");

            while (true) {
                Socket cliente = servidor.accept();
                // Aceptar sin validación
                new Thread(new ManejadorCliente(cliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ManejadorCliente implements Runnable {

        private final Socket cliente;

        public ManejadorCliente(Socket cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            try {
                // Sin timeout configurado

                System.out.println("Cliente aceptado");

                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

                // Lectura sin límite de caracteres
                String mensaje = entrada.readUTF();
                salida.writeUTF(mensaje.toUpperCase());

            } catch (IOException e) {
                System.out.println("Error con cliente: " + e.getMessage());
            } finally {
                try {
                    cliente.close();
                } catch (IOException ignored) {
                }

                System.out.println("Cliente desconectado");
            }
        }
    }
}
