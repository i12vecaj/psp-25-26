package examenSocket;

import java.io.*;
import java.net.*;

/*
    Autora: Maria Luisa Ortega Lucena
    Fecha: 28/01/2026
    Examen Socket
 */

public class ServidorChat
{
    static final int MAXIMO = 5; //MAXIMO DE CONEXIONES PERMITIDAS

    public static void main(String args[]) throws IOException {
        // He colocado ese puerto porque es el puerto que se ha pactado con ClienteChat
        int PUERTO = 44444;

        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado...");

        Socket tabla[] = new Socket[MAXIMO]; //para controlar las conexiones
        // He instanciado el objeto comun de la clase ComunHilos
        ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla);

        while (comun.getCONEXIONES() < MAXIMO) {
            Socket socket = new Socket(); // Se cierra en el HiloServidor
            //Con esto lo que hace el servidor es que espera a que el cliente se conecte
            socket = servidor.accept()/* RELLENAR */; // esperando cliente

            comun.addTabla(socket, comun.getCONEXIONES());
            comun.setACTUALES(comun.getACTUALES() + 1);
            comun.setCONEXIONES(comun.getCONEXIONES() + 1);

            HiloServidorChat hilo = new HiloServidorChat(socket, comun);
            //He colocado el .start para arrancar el hilo y que empiece a ejecutarse
            hilo.start();
        }
        servidor.close();
    }//main
}//ServidorChat..
