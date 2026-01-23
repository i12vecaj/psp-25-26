import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        int salida=0;

        Scanner leer = new Scanner(System.in);


        while(salida==0) {
            System.out.println("Para buscar dirección introduce 0, para salir 1");

            salida=leer.nextInt();
            leer.nextLine();

            switch(salida){
                case 0:
                    System.out.println("Introduce dirección URL o IP");
                    String direccion = leer.nextLine();

                    try{

                        InetAddress inet=InetAddress.getByName(direccion) ;

                        try{
                            if (inet.isReachable(3000)){
                                System.out.println("Dirección encontrada");
                                System.out.println("Dirección IP "+inet.getHostAddress());
                                System.out.println("Dirección Host "+inet.getHostName());
                            } else{
                                System.out.println("Error al buscar la dirección");
                            }
                        } catch (IOException e) {
                            System.out.println("Error al buscar la dirección");
                            throw new RuntimeException(e);
                        }


                    } catch (UnknownHostException e){
                        System.err.println("Error al conectar");
                    };

                    break;
                case 1:
                    System.out.println("Adios");
                    break;
                default:break;
            }

        };
    }
}