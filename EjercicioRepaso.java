import java.util.Scanner;
/* Hecho por Pablo Rodriguez Casado */

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Introduce tu nombre: ");
		String nombre = scanner.next();
		System.out.print("Introduce tu Edad: ");
		int edad = scanner.nextInt();
		scanner.nextLine();

		int fecha100 = 2025 + 100 - edad;

		System.out.println("Hola " + nombre + ", cumplirás 100 años en el año " + fecha100);

	}
}