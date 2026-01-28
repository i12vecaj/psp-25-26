package main;

import java.net.*;
import java.util.Scanner;
import java. io.*;

public class cliente {
	
    public static void main(String[] args) {
    	
    	
    	Scanner scan = new Scanner(System.in); // Escaner para escribir el mensaje
    	
        final String HOST = "10.2.0.121"; // La IP del servidor
        final int PUERTO = 9876;	// El puerto que esta usando
        
        final int TIMEOUT = 5000;	// Tiempo limite de espera
        
        try (DatagramSocket socket = new DatagramSocket()) {
            
            socket.setSoTimeout(TIMEOUT);									// Se establece el tiempo limite
            System.out.println("Timeout configurado:  " + TIMEOUT + "ms");
            
            System.out.print("Escribe el mensaje a mandar: ");
            String mensaje = scan.nextLine();							// Se escribe el mensaje que se quiere mandar al servidor
            System.out.println("");
            
            byte[] envio = mensaje.getBytes();    // El mensaje se transforma en bytes (UDP funciona usando bytes).
            
            InetAddress ip = InetAddress.getByName(HOST);	//Se obtiene la IP del servidor
            
            DatagramPacket paqueteEnvio = new DatagramPacket(
                envio, envio.length, ip, PUERTO);		//Se crea el paquete UDP con los datos (datos, longitud de los datos, Ip y puerto)
            
            socket.send(paqueteEnvio);					// Se envia el paquete
            System.out.println("Paquete enviado");
            
            byte[] recepcion = new byte[1024];
            DatagramPacket paqueteRecepcion = new DatagramPacket(	//Espera a que reciba los datos		
                recepcion, recepcion.length);
            
            try {
            	
                socket.receive(paqueteRecepcion);		// Espera a recibir una respuesta
                String respuesta = new String(paqueteRecepcion.getData(), 		
                    0, paqueteRecepcion.getLength());
                System.out.println("Respuesta recibida: " + respuesta);		/* Si se recibe una respuesta antes del tiempo limite te devuelve el
                																el mensaje mandado en mayusculas */
            } catch (SocketTimeoutException e) {
            	
                System.out.println("TIMEOUT: No se recibió respuesta");	// Si se pasa del tiempo establecido mandara un mensaje de error.
                System.out.println("El paquete puede haberse perdido");
                
            }
            
        } catch (IOException e) {
        	
            e.printStackTrace();
            
        }
        
    }
    
}