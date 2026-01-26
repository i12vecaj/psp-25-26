import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteMayusculas {
    public static void main(String[] args) {
        Scanner scannercito = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 6001)) {
            PrintWriter salidita = new PrintWriter(
                socket.getOutputStream(), true);
            BufferedReader entradita = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            
            System.out.println("Introduce un mensaje: ");
            String mensaje = scannercito.nextLine();
            salidita.println(mensaje);
            String respuesta = entradita.readLine();
            System.out.println("Respuesta: " + respuesta);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } 
            scannercito.close();
        }
    }


