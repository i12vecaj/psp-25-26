// Alberto Nieto Lozano
// ClienteChat.java: clase que permite la conexión de un cliente al chat

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteChat extends Thread {

    private Socket socket;
    private DataInputStream entrada;
    private boolean repetir = true; // Variable para controlar el bucle de lectura

    // -- Declaración de la clase --
    public ClienteChat(Socket s) throws IOException {
        socket = s;
        entrada = new DataInputStream(socket.getInputStream());
    }

    // -- Método run --
    public void run() {
        try {
            while (repetir) {
                String texto = entrada.readUTF();
                System.out.println(texto);
            }
        } catch (IOException e) {
        }
    }

    // -- Main --
    public static void main(String[] args) {

        String host = "localhost"; // Dirección del servidor
        int puerto = 44444; // Puerto del servidor

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre o nick: ");
        String nombre = sc.nextLine();

        try {
            Socket socket = new Socket(host, puerto); // Conexión al servidor

            System.out.println("CONEXION DEL CLIENTE CHAT: " + nombre);

            DataOutputStream salida = new DataOutputStream(socket.getOutputStream()); // Flujo de salida (los datos que el programa envía)

            salida.writeUTF(" > Entra en el Chat ... " + nombre);

            ClienteChat hilo = new ClienteChat(socket);
            hilo.start();

            String texto = ""; // Variable para almacenar el mensaje a enviar

            while (!texto.trim().equals("*")) { // Mientras el texto no sea un * se sigue leyendo
                texto = sc.nextLine();
                salida.writeUTF(nombre + " > " + texto);
            }

            salida.writeUTF(" > Abandona el Chat ... " + nombre);
            hilo.repetir = false;

            socket.close(); // Cierra la conexión

        } catch (IOException e) {
            System.out.println("Error de conexión");
        }
    }
}
