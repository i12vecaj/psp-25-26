
// Este cliente actúa como un cliente normal enviando un saludo al servidor y recibiendo la respuesta

import java.io.*;
import java.net.*;

public class ClienteNormal  {
    public static void main(String[] args) throws Exception {
        String Host = "localhost";
        int Puerto = 6000;

        System.out.println("Cliente Fran iniciado....");
        Socket Cliente2 = new Socket(Host, Puerto);

        System.out.println("-- Informacion del Socket Cliente --");

        System.out.println("Puerto local del socket cliente: " + Cliente2.getLocalPort());
        System.out.println("Puerto remoto del socket cliente: " + Cliente2.getPort());
        System.out.println("Direccion ip de la maquina remota: " + Cliente2.getInetAddress().getHostAddress());

        System.out.println("------------------------------------");

        DataOutputStream flujoSalida = new DataOutputStream(Cliente2.getOutputStream());

        flujoSalida.writeUTF("Saludos al Servidor -Fran-");

        DataInputStream flujoEntrada = new DataInputStream(Cliente2.getInputStream());

        System.out.println("Recibiendo desde el server: \n\t" + flujoEntrada.readUTF());

        flujoEntrada.close();
        flujoSalida.close();
        Cliente2.close();
    }
}