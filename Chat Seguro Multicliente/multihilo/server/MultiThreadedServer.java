package server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Servidor TCP multicliente que gestiona conexiones mediante hilos
 */
public class MultiThreadedServer {
    private static final int PUERTO = 8080;
    private static int clienteId = 0;

    // Lista thread-safe para guardar los manejadores de clientes activos
    private static Set<ClientHandler> clientesConectados = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Servidor Chat Broadcast iniciado en puerto " + PUERTO);
        System.out.println("Esperando conexiones...\n");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clienteId++;

                System.out.println("Nueva conexion - Cliente #" + clienteId);

                ClientHandler clientHandler = new ClientHandler(clientSocket, clienteId);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Método para enviar mensaje a todos menos al remitente (opcional, o a todos)
    // En este caso, lo enviamos a TODOS menos al que envía para que no tenga
    // duplicados
    // ya que su propia consola muestra lo que escribe.
    public static void broadcast(String mensaje, ClientHandler remitente) {
        synchronized (clientesConectados) {
            System.out.println("Broadcasting: '" + mensaje + "' a " + clientesConectados.size() + " clientes.");
            for (ClientHandler cliente : clientesConectados) {
                if (cliente != remitente) {
                    cliente.enviarMensaje(mensaje);
                }
            }
        }
    }

    /**
     * Clase interna que maneja cada cliente en un hilo separado
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

                // Mensaje de bienvenida
                salida.println("Bienvenido al Chat Global!");
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