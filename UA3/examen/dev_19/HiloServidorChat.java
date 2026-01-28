package examenSocket;

/*
    Autora: Maria Luisa Ortega Lucena
    Fecha: 28/01/2026
    Examen Socket
 */

import java.io.*;
import java.net.*;

public class HiloServidorChat extends Thread {
    DataInputStream fentrada;
    Socket socket = null;
    ComunHilos comun;

    public HiloServidorChat(Socket s, ComunHilos comun) {
        this.socket = s;
        this.comun = comun;
        try {
            // CREO FLUJO DE entrada para leer los mensajes
            // He puesto socket.getInputStream() para el flujo de entrada de los mensajes, porque sino no esta cogiendo ningún mensaje
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        }
    }// ..

    public void run() {
        System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());

        // NADA MAS CONECTARSE LE ENVIO TODOS LOS MENSAJES
        String texto = comun.getMensajes();
        EnviarMensajesaTodos(texto);

        while (true) {
            String cadena = "";
            try {
                cadena = fentrada.readUTF();
                if (cadena.trim().equals("*")) {// EL CLIENTE SE DESCONECTA
                    comun.setACTUALES(comun.getACTUALES() - 1);
                    System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
                }

                comun.setUltimo(cadena);
                EnviarMensajesaTodos(comun.getUltimo());

            } catch (Exception e) {// EL CLIENTE SE DESCONECTA A LO BRUTO Ctrl+C o similar
                // Con .getACTUALES(), se recoge las conexiones que ha tenido
                // Para no contar con el cliente que se ha desconectado en esse momento
                // Le resto -1, para que no cuente con el ultimo cliente que se ha conectado
                // y se ha desconectado a lo brusco
                comun.setACTUALES(comun.getACTUALES()-1);
                System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
                System.out.println("ERROR: " + e.getMessage());
                break;
            }
        } // fin while

        // se cierra el socket del cliente
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }// run

    // ENVIA LOS MENSAJES DEL CHAT A LOS CLIENTES
    // he cambiado texto para que cuando la funcion run llame a este metodo
    // le tenga que pasar obligatoriamente una cadena de caracteres
    private void EnviarMensajesaTodos(String texto) {
        int i;
        // recorremos tabla de sockets para enviarles los mensajes
        for (i = 0; i < comun.getCONEXIONES(); i++) {
            Socket s1 = comun.getElementoTabla(i);
            if (!s1.isClosed()) {
                try {
                    DataOutputStream fsalida = new DataOutputStream(s1.getOutputStream());
                    fsalida.writeUTF(texto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } // for

    }// EnviarMensajesaTodos

}// ..HiloServidorChat
