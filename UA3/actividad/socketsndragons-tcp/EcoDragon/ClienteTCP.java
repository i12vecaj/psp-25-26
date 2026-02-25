package EcoDragon;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {

    public static void main(String[] args) {
        String host = "10.2.0.80";
        int puerto = 5000;

        try (Socket cliente = new Socket(host, puerto)) {

            System.out.println("Aventurero iniciando viaje al servidor...");

            DataOutputStream flujoSalida
                    = new DataOutputStream(cliente.getOutputStream());
            DataInputStream flujoEntrada
                    = new DataInputStream(cliente.getInputStream());

            // Leer mensaje del usuario
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce la frase: ");
            String grito = sc.nextLine();

            // Enviar mensaje
            flujoSalida.writeUTF(grito);
            System.out.println("Grito enviado: " + grito);

            // Recibir respuesta
            String eco = flujoEntrada.readUTF();
            System.out.println("Eco recibido: " + eco);

            flujoEntrada.close();
            flujoSalida.close();

        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
