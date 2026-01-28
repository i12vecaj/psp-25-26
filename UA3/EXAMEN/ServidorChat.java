import java.io.*;
import java.net.*;


public class ServidorChat
{
    static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

    public static void main(String args[]) throws IOException {
        int PUERTO = 44444; // puerto de conexion para establecer la conexion del chat con el cliente

        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado...");

        Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
        ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); // objeto comun para los hilos se crea en el servidor

        while (comun.getCONEXIONES() < MAXIMO) {
            Socket socket = new Socket(); // Se cierra en el HiloServidor
            socket = servidor.accept(); // esperando cliente y aceptando conexion para chat 

            comun.addTabla(socket, comun.getCONEXIONES());
            comun.setACTUALES(comun.getACTUALES() + 1);
            comun.setCONEXIONES(comun.getCONEXIONES() + 1);

            HiloServidorChat hilo = new HiloServidorChat(socket, comun);
            hilo.start(); // arrancamos el hilo por eso se pone start()
        }
        servidor.close();
    }//main
}//ServidorChat..
