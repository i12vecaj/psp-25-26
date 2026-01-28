import java.io.*;
import java.net.*;

public class OdinServidorTCP {

    public static void main(String[] args) {
        int puerto = 5000;

        try {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Odín escucha juramentos en el puerto " + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Un aventurero ha llegado al Valhalla");
                new Thread(new Valkiria(cliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Valkiria implements Runnable {

    private Socket socket;

    public Valkiria(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String mensaje = entrada.readLine();

            if (mensaje == null) {
                System.out.println("Un aventurero huyó sin jurar");
            } else {
                System.out.println("Juramento recibido: " + mensaje);
                String mensajeMayus = mensaje.toUpperCase();
                salida.println(mensajeMayus);
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Error con un aventurero");
        }
    }
}