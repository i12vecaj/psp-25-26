package Oraculo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {

    public static void main(String[] args) {
        String host = "10.2.0.8";
        int puerto = 5000;

        try (Socket cliente = new Socket(host, puerto)) {

            System.out.println("Aventurero iniciando viaje al servidor...");

            DataOutputStream flujoSalida
                    = new DataOutputStream(cliente.getOutputStream());
            DataInputStream flujoEntrada
                    = new DataInputStream(cliente.getInputStream());

            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce un número: ");
            int num = sc.nextInt();

            // Enviar número
            flujoSalida.writeInt(num);
            System.out.println("Número enviado: " + num);

            // Recibir respuesta
            String respuesta = flujoEntrada.readUTF();
            System.out.println("Respuesta del Oráculo: " + respuesta);

            flujoEntrada.close();
            flujoSalida.close();

        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
