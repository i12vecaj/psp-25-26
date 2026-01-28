package Oraculo;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class ServidorTCP {

    private static final int PUERTO = 5000;
    private static final int MAX_CLIENTES = 40;
    private static final int TIMEOUT_MS = 30000;
    private static final Semaphore semaforo = new Semaphore(MAX_CLIENTES, true);

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PUERTO, MAX_CLIENTES)) {

            System.out.println("Oráculo Activo.");
            System.out.println("Servidor escuchando en el puerto " + PUERTO);
            System.out.println("Máximo REAL de clientes: " + MAX_CLIENTES);

            while (true) {
                Socket cliente = servidor.accept();

                if (!semaforo.tryAcquire()) {
                    System.out.println("Cliente rechazado (límite alcanzado)");
                    try {
                        cliente.close();
                    } catch (IOException ignored) {
                    }
                    continue;
                }

                new Thread(new ManejadorCliente(cliente)).start();
            }

        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
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
                cliente.setSoTimeout(TIMEOUT_MS);

                System.out.println("Cliente aceptado | Activos: "
                        + (MAX_CLIENTES - semaforo.availablePermits()));

                // streams
                try (DataInputStream entrada = new DataInputStream(cliente.getInputStream()); DataOutputStream salida = new DataOutputStream(cliente.getOutputStream())) {

                    int numero = entrada.readInt();
                    System.out.println("Número recibido: " + numero);

                    int cuadrado = numero * numero;
                    int cubo = numero * numero * numero;

                    String respuesta = "Cuadrado: " + cuadrado + " | Cubo: " + cubo;

                    salida.writeUTF(respuesta);
                    System.out.println("Respuesta enviada: " + respuesta);
                }

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
