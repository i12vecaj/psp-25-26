import java.util.Scanner;

public class RepasoJava {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.print("¿Cómo te llamas? ");
        String usuario = entrada.nextLine();

        System.out.print("¿Qué edad tienes? ");
        int edadActual = entrada.nextInt();

        int anioActual = 2025;
        int anioCien = anioActual + (100 - edadActual);

        System.out.println("Saludos, " + usuario + ". Alcanzarás los 100 años en el año " + anioCien + ".");

        entrada.close();
    }
}
