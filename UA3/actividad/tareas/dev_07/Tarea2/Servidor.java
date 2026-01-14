/* Realiza un programa servidor TCP que acepte dos clientes. Muestra por cada cliente conectado sus puertos local y remoto.
Crea también el programa cliente que se conecte a dicho servidor. Muestra los puertos locales y remotos a los que está 
conectado su socket y la dirección IP de la máquina remota a la que se conecta.*/

package tareas.dev_07.Tarea2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {

        int Puerto = 6000;
        ServerSocket servidor = new ServerSocket(Puerto);
        System.out.println("Servidor iniciado. Esperando conexiones...");

        for (int i = 1; i <= 2; i++) {
            Socket cliente = servidor.accept();
            System.out.println("-----------------------------------");
            System.out.println("Cliente " + i + " conectado.");
            System.out.println("Puerto Local: " + cliente.getLocalPort());
            System.out.println("Puerto Remoto: " + cliente.getPort());
            System.out.println("Nombre IP: " + cliente.getInetAddress());
            System.out.println("Host Remoto: " + cliente.getInetAddress().getHostName());
            System.out.println("IP Host Remoto: " + cliente.getInetAddress().getHostAddress());
            cliente.close();
        }

        servidor.close();
    }
}
