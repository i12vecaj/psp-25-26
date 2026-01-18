import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] arg) throws IOException {
        int numeroPuerto = 6000;
        ServerSocket servidor = new ServerSocket(numeroPuerto);


        for (int i = 1; i <= 2; i++) {
            System.out.println("Esperando al cliente " + i + ".....");
            Socket clienteConectado = servidor.accept();


            System.out.println("Cliente " + i + " conectado en puerto remoto: " + clienteConectado.getPort());
            System.out.println("Mi puerto local de escucha: " + clienteConectado.getLocalPort());

            // Flujos de entrada y salida
            DataInputStream flujoEntrada = new DataInputStream(clienteConectado.getInputStream());
            System.out.println("Recibiendo: " + flujoEntrada.readUTF());

            DataOutputStream flujoSalida = new DataOutputStream(clienteConectado.getOutputStream());
            flujoSalida.writeUTF("Saludos al cliente " + i + " desde el servidor");

            // Cerrar cliente actual para que pueda entrar el siguiente
            clienteConectado.close();
        }

        System.out.println("Servidor cerrado tras atender a 2 clientes.");
        servidor.close();
    }
}