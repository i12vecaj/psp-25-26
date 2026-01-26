import java.net.Socket; 

public class EjemploCliente {
    public static void main(String[] args) {
        try(Socket cliente = new Socket("localhost", 6666)) {
            System.out.println("Conectado al servidor");
            System.out.println("Puerto local: " + cliente.getLocalPort());
            System.out.println("Puerto remoto: " + cliente.getPort());
            System.out.println("Ip Servidor: " + cliente.getInetAddress());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
