// Alberto Nieto Lozano
// ServidorChat.java: clase que inicia el servidor

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

    public static void main(String[] args) throws IOException {

        int PUERTO = 44444; // Número de Puerto
        int MAXIMO = 10; // Número máximo de personas

        ServerSocket servidor = new ServerSocket(PUERTO); // Se inicia el Servidor
        ComunHilos comun = new ComunHilos(MAXIMO); // Se llama a la clase ComunHilos para obtener los atributos

        System.out.println("Servidor iniciado..."); // Mensaje de servidor iniciado

        while (true) { // Bucle para controlar el número de conexiones del servidor

            Socket cliente = servidor.accept(); // Acepta la conexión del cliente

            comun.setCONEXIONES(comun.getCONEXIONES() + 1); // Obtiene las conexiones totales y le suma 1
            comun.setACTUALES(comun.getACTUALES() + 1); // Obtiene las conexiones actuales y le suma 1

            comun.getTabla()[comun.getCONEXIONES() - 1] = cliente; // Obtiene las conexiones totales y le resta 1

            System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES()); // Muestra el número de conexiones actuales

            HiloServidorChat hilo = new HiloServidorChat(cliente, comun); // Llama a la clase HiloServidorChat.java
            hilo.start(); // Inicializa la clase HiloServidorChat.java
        }
    }
}
