// CLIENTE TCP: muestra puertos local, remoto e IP del servidor

import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int puerto = 6000;

        Socket cliente = new Socket(host, puerto);
        InetAddress dir = cliente.getInetAddress();

        System.out.println("Puerto local: " + cliente.getLocalPort());
        System.out.println("Puerto remoto: " + cliente.getPort());
        System.out.println("IP servidor: " + dir.getHostAddress());

        cliente.close();
    }
}
