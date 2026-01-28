import java.io.*;
import java.net.*;


public class ServidorChat
{
    static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

    public static void main(String args[]) throws IOException {
        int PUERTO = 44444; // Es puerto que abrimos para que nuestro sevidor pueda recibir y emitir

        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado...");

        Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
        ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); // Instaciamos la clase ComunHilos con el array de 

        while (comun.getCONEXIONES() < MAXIMO) {
            Socket socket; // Se cierra en el HiloServidor
            socket =  servidor.accept(); // esperando cliente

            comun.addTabla(socket, comun.getCONEXIONES());
            comun.setACTUALES(comun.getACTUALES() + 1);
            comun.setCONEXIONES(comun.getCONEXIONES() + 1);

            HiloServidorChat hilo = new HiloServidorChat(socket, comun);
            hilo.start(); //Arranchamos el hilo para que se ejecute
        }
        servidor.close();
    }//main
}//ServidorChat..