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
            fentrada = new DataInputStream(socket.getInputStream());//OBTIENE LA SALIDA ESTANDAR DEL PROCESO//esto lo que hace es crear un canal de entrada que se conecta al flujo de datos para saber lo que nos dice un cliente especicamente
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
                    comun.setACTUALES(comun.getACTUALES() - 1);//esto lo he hecho de la siguiente forma, para que cada vez se desconecte un cliente se reste uno a los clientes conectados.
                    System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
                }

                comun.setUltimo(cadena);
                EnviarMensajesaTodos(comun.getUltimo());

            } catch (Exception e) {// EL CLIENTE SE DESCONECTA A LO BRUTO Ctrl+C o similar
                comun.setACTUALES(comun.getACTUALES() - 1);
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
    private void EnviarMensajesaTodos(String texto) {//usamos una variable llamada texto para enviar mensajes de tipo string, igual que tenemos en el cliente chat, string texto
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