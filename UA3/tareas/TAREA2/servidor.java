package socketstarea1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class servidor {

    public static void main(String[] args) throws IOException {

        int Puerto = 6001;
        ServerSocket Servidor = new ServerSocket(Puerto);
        System.out.println("Servidor escuchando en el puerto " + Servidor.getLocalPort());

        Socket cliente1 = Servidor.accept();
        System.out.println("Cliente 1 conectado");
        System.out.println("Puerto local (servidor): " + cliente1.getLocalPort());
        System.out.println("Puerto remoto (cliente): " + cliente1.getPort());
        System.out.println("-----------------------------");

        Socket cliente2 = Servidor.accept();
        System.out.println("Cliente 2 conectado");
        System.out.println("Puerto local (servidor): " + cliente2.getLocalPort());
        System.out.println("Puerto remoto (cliente): " + cliente2.getPort());
        System.out.println("-----------------------------");

        cliente1.close();
        cliente2.close();
        Servidor.close();
    }
}
