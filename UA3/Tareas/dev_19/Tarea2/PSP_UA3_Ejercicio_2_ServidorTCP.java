package Ejercicio2;

/*
Autor: María Luisa Ortega Lucena
Fecha: 14/01/2026
Enunciado: Realiza un programa servidor TCP que acepte dos clientes.
Muestra por cada cliente conectado sus puertos local y remoto.

Crea también el programa cliente que se conecte a dicho servidor.
Muestra los puertos locales y remotos a los que está conectado su socket y la dirección IP
de la máquina remota a la que se conecta.

 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PSP_UA3_Ejercicio_2_ServidorTCP {
    public static void main(String[] args) throws IOException {
        //Declaro la variable Puerto que es la que después le voy a asignar cuando cree el servidor
        int Puerto = 6000;
        ServerSocket Servidor = new ServerSocket(Puerto);

        System.out.println("Escuchando en: "+Servidor.getLocalPort());

        //Primer Cliente
        Socket cliente1 = Servidor.accept();
        System.out.println("Cliente 1 conectado");

        //Segundo cliente
        Socket cliente2 = Servidor.accept();
        System.out.println("Cliente 2 conectado");

        //Cierro servidor:
        Servidor.close();

    }
}
