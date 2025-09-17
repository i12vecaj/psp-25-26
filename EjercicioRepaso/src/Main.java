import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String nombre;
        int edad;
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce tu nombre: ");
        nombre = sc.nextLine();

        System.out.print("Introduce tu edad: ");
        edad = sc.nextInt();

        int anios = ((2025 - edad) + 100);
        System.out.println("Hola " + nombre + ", cumplirás 100 años en " + anios);
    }
}