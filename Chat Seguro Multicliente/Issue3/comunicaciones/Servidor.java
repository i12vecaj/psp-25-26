package comunicaciones;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Servidor {

    public static final int PUERTO = 7000;
    private static Set<HiloCliente> clientes = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado");

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            while (true) {
                Socket socket = servidor.accept();
                HiloCliente cliente = new HiloCliente(socket);
                clientes.add(cliente);
                cliente.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static synchronized boolean nombreDisponible(String nombre) {
        for (HiloCliente c : clientes) {
            if (c.getNombreUsuario().equalsIgnoreCase(nombre)) {
                return false;
            }
        }
        return true;
    }

    public static synchronized void broadcast(String mensaje, HiloCliente emisor) {
        for (HiloCliente c : clientes) {
            if (c != emisor) {
                c.enviarMensaje(mensaje);
            }
        }
    }

    public static synchronized void eliminarCliente(HiloCliente cliente) {
        clientes.remove(cliente);
    }

    public static synchronized String listaUsuarios() {
        StringBuilder sb = new StringBuilder("Usuarios conectados:\n");
        for (HiloCliente c : clientes) {
            sb.append("- ").append(c.getNombreUsuario()).append("\n");
        }
        return sb.toString();
    }
}