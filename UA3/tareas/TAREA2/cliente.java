package socketstarea1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class cliente {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el host: ");
        String Host = sc.nextLine();
        int Puerto = 6000;

        Socket Cliente = new Socket(Host, Puerto);
        InetAddress dir = Cliente.getInetAddress();

        System.out.println("Puerto Local: " + Cliente.getLocalPort());
        System.out.println("Puerto Remoto: " + Cliente.getPort());
        System.out.println("IP Host Remoto: " + dir.getHostAddress());

        Cliente.close();
        sc.close();
    }
}
