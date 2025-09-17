import java.util.Scanner;

public class EjercicioRepaso {
    public static void main(String[] args) throws Exception {


        Scanner sc = new Scanner(System.in);        
        System.out.println("Dime tu nombre");
        String nombre = sc.nextLine();
        
        System.out.println("Dime tu edad");
        int edad = sc.nextInt();

        System.out.println("Hola " + nombre + " cumples 100 años en " + (2025 + (100 - edad)));

    }
}
