import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
         String Nombre;
        String edadEntrada;
        int edad;
        int resta= 0;
        int anyo=2025;

        Scanner sc = new Scanner(System.in);

        System.out.println("Intorduce tu nombre: ");
        Nombre = sc.nextLine();
        
        System.out.println("Intorduce tu edad: ");
        edadEntrada = sc.nextLine();

        edad = Integer.parseInt(edadEntrada);
        resta = 100 - edad;
        anyo+=resta;

        System.out.println("Hola "+Nombre+" , cumplirás 100 años en el año "+anyo);
    }
}
