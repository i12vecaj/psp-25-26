// SERVIDOR TCP: acepta 2 clientes y muestra puertos local y remoto

import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        int puerto = 6000;
        ServerSocket servidor = new ServerSocket(puerto);

        System.out.println("Servidor escuchando en puerto: " + servidor.getLocalPort());

        Socket cliente1 = servidor.accept();
        System.out.println("CLIENTE 1 CONECTADO");
        System.out.println("Puerto local: " + cliente1.getLocalPort());
        System.out.println("Puerto remoto: " + cliente1.getPort());

        Socket cliente2 = servidor.accept();
        System.out.println("CLIENTE 2 CONECTADO");
        System.out.println("Puerto local: " + cliente2.getLocalPort());
        System.out.println("Puerto remoto: " + cliente2.getPort());

        cliente1.close();
        cliente2.close();
        servidor.close();
    }
}
