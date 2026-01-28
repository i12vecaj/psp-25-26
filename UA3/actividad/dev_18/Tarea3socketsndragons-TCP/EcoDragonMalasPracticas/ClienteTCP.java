package EcoDragonMalasPracticas;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {

    public static void main(String[] args) {
        String host = "192.168.56.1";
        int puerto = 5000;

        Socket cliente = null;
        
        try {
            cliente = new Socket(host, puerto);

            System.out.println("Aventurero iniciando viaje al servidor...");

            DataOutputStream flujoSalida
                    = new DataOutputStream(cliente.getOutputStream());
            DataInputStream flujoEntrada
                    = new DataInputStream(cliente.getInputStream());

            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce la frase: ");
            String grito = sc.nextLine();

            flujoSalida.writeUTF(grito);
            System.out.println("Grito enviado: " + grito);

            String eco = flujoEntrada.readUTF();
            System.out.println("Eco recibido: " + eco);

        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        } finally {
            if (cliente != null) {
                try {
                    cliente.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
