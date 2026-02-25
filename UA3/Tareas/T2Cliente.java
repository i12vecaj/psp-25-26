package U3.Tareas;

import java.io.IOException;
import java.net.Socket;

public class T2Cliente {
    public static void main(String[] args) {
        String servidorIP = "127.0.0.1"; // Cambia si el servidor está en otra máquina
        int servidorPuerto = 5000;

        try (Socket socket = new Socket(servidorIP, servidorPuerto)) {

            System.out.println("Cliente conectado:");
            System.out.println("  IP local      : " + socket.getLocalAddress().getHostAddress());
            System.out.println("  Puerto local  : " + socket.getLocalPort());
            System.out.println("  IP remota     : " + socket.getInetAddress().getHostAddress());
            System.out.println("  Puerto remoto : " + socket.getPort());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
