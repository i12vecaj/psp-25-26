package Ejercicio2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/*
Autor: María Luisa Ortega Lucena
Fecha: 14/01/2026
Enunciado: Realiza un programa servidor TCP que acepte dos clientes.
Muestra por cada cliente conectado sus puertos local y remoto.

Crea también el programa cliente que se conecte a dicho servidor.
Muestra los puertos locales y remotos a los que está conectado su socket y la dirección IP
de la máquina remota a la que se conecta.

 */

public class PSP_UA3_Ejercicio_2_ClienteTCP {
    public static void main(String[] args) throws IOException {
        String Host= "localhost";
        int Puerto = 6000;

        Socket Cliente1 = new Socket(Host, Puerto);
        InetAddress i = Cliente1.getInetAddress();
        Socket Cliente2 = new Socket(Host, Puerto);
        InetAddress in = Cliente2.getInetAddress();

        System.out.println("Cliente1: Puerto Local: "+Cliente1.getLocalPort());
        System.out.println("Cliente1: Puerto remoto: "+Cliente1.getPort());

        System.out.println("Cliente2: Puerto Local: "+Cliente2.getLocalPort());
        System.out.println("Cliente2: Puerto remoto: "+Cliente2.getPort());

        Cliente1.close();
        Cliente2.close();
    }
}
