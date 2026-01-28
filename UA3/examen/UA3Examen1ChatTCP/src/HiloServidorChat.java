// Alberto Nieto Lozano
// HiloServidorChat.java: clase que permite el intercambio de mensajes entre usuarios

import java.io.*;
import java.net.Socket;

public class HiloServidorChat extends Thread {

    private Socket socket;
    private ComunHilos comun;
    private DataInputStream entrada;

    // -- Declaración de la clase --
    public HiloServidorChat(Socket s, ComunHilos c) throws IOException {
        socket = s;
        comun = c;
        entrada = new DataInputStream(socket.getInputStream());
    }

    // -- Método run --
    public void run() {
        try {

            EnviarMensajesaTodos();

            String texto = ""; // Almacena el mensaje recibido

            while (!texto.trim().equals("*")) { // Mientras el texto no sea un * se sigue leyendo

                texto = entrada.readUTF(); // Lee el mensaje enviado por el cliente

                if (!texto.trim().equals("*")) { // Si el mensaje no es un * se envía a todos los clientes conectados
                    comun.setMensajes(comun.getMensajes() + texto + "\n"); // Añade el mensaje a la variable mensajes
                    EnviarMensajesaTodos();
                }
            }

            comun.setACTUALES(comun.getACTUALES() - 1); // Resta 1 a las conexiones actuales al salir un cliente
            socket.close();

        } catch (IOException e) {
        }
    }

    // -- Función para enviar el mensaje a todos los clientes --
    private void EnviarMensajesaTodos() {

        Socket[] tabla = comun.getTabla(); // Obtiene la tabla de sockets

        for (int i = 0; i < comun.getCONEXIONES(); i++) { // Recorre la tabla de sockets
            Socket s = tabla[i];

            if (s != null && !s.isClosed()) { // Si el socket no es nulo y no está cerrado
                try {
                    DataOutputStream salida = new DataOutputStream(s.getOutputStream());
                    salida.writeUTF(comun.getMensajes());
                } catch (IOException e) {
                }
            }
        }
    }
}
