import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws Exception {
        String Host = "localhost"; //Localhost porque estamos haciendo una conexión en nuestro equipo
        int Puerto = 6000; //Puerto en el que el socket del servidor se pone a la escucha esperando conexiones de clientes (como está clase por ejemplo)
        Socket cliente = new Socket(Host, Puerto);



        //Estos son los datos que el cliente obtiene del servidor. Por lo que los imprime cada vez que se ejecuta esta clase
        System.out.println("Puerto local: " + cliente.getLocalPort());
        System.out.println("Puerto remoto: " + cliente.getPort());
        System.out.println("IP Remota: " + cliente.getInetAddress().getHostAddress());

        // Comunicación
        DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
        flujoSalida.writeUTF("Saludos al SERVIDOR DESDE EL CLIENTE");

        DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
        System.out.println("Respuesta: " + flujoEntrada.readUTF());

        cliente.close();
    }
}