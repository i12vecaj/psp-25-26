// El programa cliente en primer lugar envia un mensaje al servidor
// Despues recibe un mensaje del servidor visualizandolo en pantalla
// Se ha simplificado la obtencion de los flujos de entrada y salida

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UA3tarea1 {
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        while (true) {

            System.out.print("Introduce una IP o URL (localhost para salir): ");
            String Host = teclado.nextLine();

            if (Host.equalsIgnoreCase("localhost")) {
                System.out.println("Fin del programa.");
                break;
            }

            int Puerto = 6000;

            try {
                Socket Cliente = new Socket(Host, Puerto);

                InetAddress i = Cliente.getInetAddress();

                System.out.println("Puerto Local: " + Cliente.getLocalPort());
                System.out.println("Puerto Remoto: " + Cliente.getPort());
                System.out.println("Nombre Host/IP: " + Cliente.getInetAddress());
                System.out.println("Host Remoto: " + i.getHostName());
                System.out.println("IP Host Remoto: " + i.getHostAddress());

                Cliente.close();

            } catch (IOException e) {
                System.out.println("No se puede conectar con el host introducido.");
            }

            System.out.println();
        }

        teclado.close();
    }
}
