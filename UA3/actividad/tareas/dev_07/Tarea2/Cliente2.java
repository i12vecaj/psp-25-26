package tareas.dev_07.Tarea2;

import java.net.InetAddress;
import java.net.Socket;

public class Cliente2 {
    public static void main(String[] args) throws Exception {

     String Host = "localhost";
	  int Puerto = 6000;

    Socket Cliente2 = new Socket(Host, Puerto);
    InetAddress i = Cliente2.getInetAddress();
    System.out.println("Puerto Local: " + Cliente2.getLocalPort());
    System.out.println("Puerto Remoto: " + Cliente2.getPort());
    System.out.println("Nombre Host/IP: " + Cliente2.getInetAddress());
    System.out.println("Host Remoto: " + i.getHostName().toString());
    System.out.println("IP Host Remoto: " + i.getHostAddress().toString());

    Cliente2.close();
    }
}
