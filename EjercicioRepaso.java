import java.util.Scanner;

public class Practicauno {

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("introduce tu nombre: ");
	        String nombre = sc.nextLine();

	        System.out.print("Introduce tu edad: ");
	        int edad = sc.nextInt();

	        int añoActual = java.time.Year.now().getValue();
	        int añoCien = añoActual + (100 - edad);

	        System.out.println("bienvenido " + nombre);
	        System.out.println("cumpliras 100 años en " + añoCien + ".");
	    }
}
