package server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Servidor TCP Multihilo que identifica a los clientes por su IP y Puerto
 * utilizando getInetAddress() e implementa Broadcast.
 */
public class MultiThreadedServerInet {
    private static final int PUERTO = 8082; // Puerto diferente

    // Lista thread-safe para guardar los hilos manejadores (ClientHandler) de cada
    // cliente
    private static Set<ClientHandler> clientesConectados = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Servidor Inet (IP Ident) iniciado en puerto " + PUERTO);
        System.out.println("Esperando conexiones...\n");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Identificación usando InetAddress
                InetAddress ipCliente = clientSocket.getInetAddress();
                System.out.println("Nueva conexión desde IP: " + ipCliente.getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
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
     * ClientHandler usando info de socket para asignar ID
     */
    static class ClientHandler implements Runnable {
        private Socket socket;
        private String clientIdentifier;
        private BufferedReader entrada;
        private PrintWriter salida;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            // Identificador basado en IP y Puerto Remoto
            this.clientIdentifier = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
        }

        @Override
        public void run() {
            try {
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new PrintWriter(socket.getOutputStream(), true);

                clientesConectados.add(this);

                salida.println("Bienvenido al Chat Global [Servidor Inet]!");
                salida.println("Te identificas como: " + clientIdentifier);

                broadcast("Se ha unido: " + clientIdentifier, this);

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("[" + clientIdentifier + "]: " + mensaje);

                    if (mensaje.equalsIgnoreCase("salir")) {
                        break;
                    } else {
                        broadcast("[" + clientIdentifier + "]: " + mensaje, this);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error con cliente " + clientIdentifier + ": " + e.getMessage());
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
                broadcast("Ha salido: " + clientIdentifier, this);

                if (entrada != null)
                    entrada.close();
                if (salida != null)
                    salida.close();
                if (socket != null)
                    socket.close();
                System.out.println("Desconectado: " + clientIdentifier);
            } catch (IOException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}
