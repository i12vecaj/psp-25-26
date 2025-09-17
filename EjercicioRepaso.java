import java.util.Scanner;

public class EjercicioRepaso {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("Hola, ¿cómo te llamas?");
		
		String nombre = sc.next();
		
		System.out.println("¿Cúal es tu edad?");
		
		int edad = sc.nextInt();
		
		int anio = 100 - edad + 2025;
		
		System.out.println("Bienvenido " + nombre + " tienes " + edad + " años y cumples 100 en " + anio);
	
	
	}
	
}
