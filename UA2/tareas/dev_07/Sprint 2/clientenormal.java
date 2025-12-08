import java.net.*;
import java.io.*;
import java.util.Scanner;

public class clientenormal {
    
    public static void main(String[] args) {
        System.out.println("CLIENTE TCP MULTIHILO");
        
        String direccionServidor = "localhost";
        int puertoServidor = 8080;
        
        try {
            Socket socket = new Socket(direccionServidor, puertoServidor);
            System.out.println("Conexión establecida");
            
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            Scanner teclado = new Scanner(System.in);
            
            Thread hiloReceptor = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String mensajeServidor;
                        while((mensajeServidor = entrada.readLine()) != null) {
                            System.out.println(mensajeServidor);
                        }
                    } catch (IOException e) {
                        System.out.println("Conexión cerrada");
                    }
                }
            });
            
            hiloReceptor.start();
            
            System.out.println("Escribe mensajes o 'salir':");
            
            String mensajeUsuario;
            do {
                System.out.print("Tú: ");
                mensajeUsuario = teclado.nextLine();
                salida.println(mensajeUsuario);
                
            } while(!mensajeUsuario.equalsIgnoreCase("salir"));
            
            teclado.close();
            entrada.close();
            salida.close();
            socket.close();
            
            System.out.println("Cliente finalizado.");
        
        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}