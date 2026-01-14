package U3.Tareas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class T2Servidor {
     public static void main(String[] args) {
        int puerto = 5000;
        int maxClientes = 2;
        int clientesConectados = 0;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor escuchando en el puerto " + puerto + "\n");

            while (clientesConectados < maxClientes) {
                Socket cliente = servidor.accept();
                clientesConectados++;

                System.out.println("Cliente " + clientesConectados + " conectado:");
                System.out.println("  IP remota     : " + cliente.getInetAddress().getHostAddress());
                System.out.println("  Puerto remoto : " + cliente.getPort());
                System.out.println("  IP local      : " + cliente.getLocalAddress().getHostAddress());
                System.out.println("  Puerto local  : " + cliente.getLocalPort());
                System.out.println();

                cliente.close();
            }

            System.out.println("Máximo de clientes alcanzado. Servidor cerrado.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
