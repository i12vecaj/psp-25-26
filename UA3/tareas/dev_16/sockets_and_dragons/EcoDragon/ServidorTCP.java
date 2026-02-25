package EcoDragon;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class ServidorTCP {

    private static final int PUERTO = 5000;
    private static final int MAX_CLIENTES = 40;
    private static final int TIMEOUT_MS = 30000; // 30 segundos AFK
    private static final Semaphore semaforo = new Semaphore(MAX_CLIENTES, true);

    public static void main(String[] args) {

        try (ServerSocket servidor = new ServerSocket(PUERTO, MAX_CLIENTES)) {

            System.out.println("Servidor iniciado en puerto " + PUERTO);
            System.out.println("Máximo REAL de clientes: " + MAX_CLIENTES);

            while (true) {
                Socket cliente = servidor.accept();

                if (!semaforo.tryAcquire()) {
                    System.out.println("Cliente rechazado (límite alcanzado)");
                    cliente.close();
                    continue;
                }

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
                cliente.setSoTimeout(TIMEOUT_MS); // ⏱️ Timeout AFK

                System.out.println("Cliente aceptado | Activos: "
                        + (MAX_CLIENTES - semaforo.availablePermits()));

                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

                String mensaje = entrada.readUTF(); // bloquea máx TIMEOUT_MS
                salida.writeUTF(mensaje.toUpperCase());

            } catch (SocketTimeoutException e) {
                System.out.println("Cliente expulsado por AFK (timeout)");
            } catch (IOException e) {
                System.out.println("Error con cliente: " + e.getMessage());
            } finally {
                try {
                    cliente.close();
                } catch (IOException ignored) {
                }

                semaforo.release();

                System.out.println("Cliente desconectado | Activos: "
                        + (MAX_CLIENTES - semaforo.availablePermits()));
            }
        }
    }
}
