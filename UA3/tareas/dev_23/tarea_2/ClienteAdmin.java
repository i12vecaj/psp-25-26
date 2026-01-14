

// Este cliente es el admin, puedes elegir entre enviar un saludo o cerrar el servidor

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteAdmin  {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String Host = "localhost";
        int Puerto = 6000, opcion;
        String mensaje;

        System.out.println("Que mensaje deseas enviar al servidor, usuario Admin?");
        System.out.println("1. Cerrar Servidor");
        System.out.println("2. Saludar al Servidor");
        opcion = sc.nextInt();
        do {
            if (opcion < 1 || opcion > 2) {
                System.out.println("Opcion no valida. Introduce 1 o 2");
                opcion = sc.nextInt();
            }
        } while (opcion < 1 || opcion > 2);
        if (opcion == 1) {
            mensaje = "Cerrar Servidor -1234-";
        } else {
            mensaje = "Saludos al Servidor -Admin-";
        }

        System.out.println("Cliente Admin iniciado....");
        Socket Cliente = new Socket(Host, Puerto);

        System.out.println("-- Informacion del Socket Cliente --");

        System.out.println("Puerto local del socket cliente: " + Cliente.getLocalPort());
        System.out.println("Puerto remoto del socket cliente: " + Cliente.getPort());
        System.out.println("Direccion ip de la maquina remota: " + Cliente.getInetAddress().getHostAddress());

        System.out.println("------------------------------------");

        DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

        flujoSalida.writeUTF(mensaje);

        DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

        System.out.println("Recibiendo del Server: \n\t" + flujoEntrada.readUTF());;

        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();
    }
}