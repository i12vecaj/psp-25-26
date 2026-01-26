import java.net.InetAddress;
import java.util.Scanner;

public class Sockets1 {
    public static void main(String [] args){
        Scanner scanercito = new Scanner(System.in);
        String textito;

        while(true){
            System.out.println("Introduce una URL o IP(localhost para salir): ");
            textito = scanercito.nextLine();
            if(textito.equals("localhost")){
                System.out.println("Adios!");
                break;
            }
            try{
                InetAddress direccion = InetAddress.getByName(textito);
                System.out.println("Host: " + direccion.getHostName());
                System.out.println("IP: " + direccion.getHostAddress());
                System.out.println("Alcanzable: " + direccion.isReachable(5000));
            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }

        }
        scanercito.close();
    }
}
