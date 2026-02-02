package Oráculo;



import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) {

        Numeros objetoNumeros = new Numeros(12,"localhost",6000);

        Socket client;

        {
            try {
                client = new Socket(objetoNumeros.getDireccion(), objetoNumeros.getPuerto());
                //DataOutputStream salida = new DataOutputStream(client1.getOutputStream());

                ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());
                salida.writeObject(objetoNumeros);

                ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());


                Numeros respuesta = (Numeros) entrada.readObject();

                System.out.println("El cuadrado de " + respuesta.getNum() + " es " + respuesta.getCuadrado());
                System.out.println("El cubo de " + respuesta.getNum() + " es " + respuesta.getCubo());






            } catch (IOException e) {
                System.out.println("Error al conectarse con el servidor " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
