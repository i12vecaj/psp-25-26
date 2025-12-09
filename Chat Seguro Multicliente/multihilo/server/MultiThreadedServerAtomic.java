package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servidor TCP Multihilo con AtomicInteger y Broadcast
 */
public class MultiThreadedServerAtomic {
    private static final int PUERTO = 8081;

    // AtomicInteger para IDs thread-safe
    // (para evitar el problema de carrera y que todos accedan a la misma variable)
    private static final AtomicInteger clienteId = new AtomicInteger(0);

    // Lista thread-safe para broadcast
    private static Set<ClientHandler> clientesConectados = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Servidor Atomic iniciado en puerto " + PUERTO);
        System.out.println("Esperando conexiones...\n");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Incremento atómico
                int nuevoId = clienteId.incrementAndGet();

                System.out.println("Nueva conexion - Cliente #" + nuevoId);

                ClientHandler clientHandler = new ClientHandler(clientSocket, nuevoId);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Método broadcast
    public static void broadcast(String mensaje, ClientHandler remitente) {
        synchronized (clientesConectados) {
            for (ClientHandler cliente : clientesConectados) {
                if (cliente != remitente) {
                    cliente.enviarMensaje(mensaje);
                }
            }
        }
    }

    /**
     * Clase interna ClientHandler
     */
    static class ClientHandler implements Runnable {
        private Socket socket;
        private int id;
        private BufferedReader entrada;
        private PrintWriter salida;

        public ClientHandler(Socket socket, int id) {
            this.socket = socket;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new PrintWriter(socket.getOutputStream(), true);

                // Añadir a la lista
                clientesConectados.add(this);

                // Mensajes de bienvenida
                salida.println("Bienvenido al Servidor Atomic!");
                salida.println("Eres el cliente #" + id);

                // Notificar a otros (anuncio global)
                broadcast("Cliente #" + id + " se ha unido al chat.", this);

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("Cliente #" + id + ": " + mensaje);

                    if (mensaje.equalsIgnoreCase("salir")) {
                        break;
                    } else {
                        // REEMITIR A TODOS
                        broadcast("Cliente #" + id + ": " + mensaje, this);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error con cliente #" + id + ": " + e.getMessage());
            } finally {
                cerrarConexion();
            }
        }

        public void enviarMensaje(String msg) {
            salida.println(msg);
        }

        private void cerrarConexion() {
            try {
                clientesConectados.remove(this);
                broadcast("Cliente #" + id + " ha salido del chat.", this);

                if (entrada != null)
                    entrada.close();
                if (salida != null)
                    salida.close();
                if (socket != null)
                    socket.close();
                System.out.println("Cliente #" + id + " desconectado");
            } catch (IOException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}
