import java.util.Scanner;

public class EjercicioRepaso {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce su nombre:");
		String nombre = sc.nextLine();
		System.out.println("¿Qué edad tienes?:");
		int edad = sc.nextInt();
		System.out.println(nombre + " cumplirás 100 años en el año " + ((100-edad)+2025));
	}
}
