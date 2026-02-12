import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {


    int puerto = 6000;
    //Creamos un servidor que este a la escucha por el puerto 6000
    ServerSocket servidor = new ServerSocket(puerto);

   for (int i =0; i<2; i++) {
       Socket cliente = servidor.accept();
       System.out.println("Cliente conectado: "+ (i+1));
       System.out.println("Cliente enviado: "+ cliente.getInetAddress());
       System.out.println("Puerto Local: " + cliente.getLocalPort());
       System.out.println("Puerto Remoto: " + cliente.getPort());
       System.out.println("Nombre Host: " + cliente.getInetAddress().getHostName());
   }
        servidor.close();


    }
}
