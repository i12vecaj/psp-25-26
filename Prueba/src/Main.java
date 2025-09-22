import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingresa tu edad: ");
        int edad = sc.nextInt();

        int anoActual = 2025;
        int anoCien = anoActual + (100 - edad);

        System.out.println(nombre + ", cumplirás 100 años en el año " + anoCien);

        sc.close();
    }
}
