import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class GestionHilo implements Runnable {
    private Socket clientSocket; //socket del cliente

    //recibe el socket del cliente
    public GestionHilo(Socket socket) {
        this.clientSocket = socket;
    }

    //metodo run, que es lo que va a realizar el hilo
    @Override
    public void run() {
        try (
                //Flujo de entradapara leer lo que envía el cliente
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                //Flujo de salida para enviar mensajes al cliente
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
        ) {
            // Mensaje inicial al cliente
            out.println("Bienvenido al servidor multicliente 🧵");

            String mensaje;
            //mientras el cliente envíe mensajes
            while ((mensaje = in.readLine()) != null) {
                System.out.println("Cliente dice: " + mensaje);

                //Si el cliente escribe bye se cierra la conexión
                if (mensaje.equalsIgnoreCase("bye")) {
                    out.println("Hasta luego");
                    break;
                } else {
                    //Si no se responde con un eco
                    out.println("Eco: " + mensaje);
                }
            }

            //cierro el socket del cliente
            clientSocket.close();
            System.out.println("Cliente desconectado.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}