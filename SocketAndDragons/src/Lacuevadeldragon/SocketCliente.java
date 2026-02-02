package Lacuevadeldragon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketCliente {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Guerrero", "Edu", "localhost",
                6000, true, "Te voy a matar");

        Socket client1;

        {
            try {
                client1 = new Socket(cliente1.getDireccion(), cliente1.getPuerto());
                //DataOutputStream salida = new DataOutputStream(client1.getOutputStream());
                DataInputStream entrada = new DataInputStream(client1.getInputStream());

                ObjectOutputStream salida = new ObjectOutputStream(client1.getOutputStream());
                System.out.println(cliente1.getMensaje());
                salida.writeObject(cliente1);

                System.out.println("El dragon responde: " + entrada.readUTF());


            } catch (IOException e) {
                System.out.println("Error al conectarse con el servidor " + e.getMessage());
            }
        }
    }
}
