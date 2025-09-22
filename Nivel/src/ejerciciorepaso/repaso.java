package ejerciciorepaso;

import java.util.Scanner;

public class repaso {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
			System.out.println("como te llamas");
		String nombre = sc.next();
			System.out.println("que edad tienes");
		int edad = sc.nextInt();
			System.out.println("bienvenido "+ nombre +"cumpliras 100 años en el año "+ ((100-edad)+2025));
	}

}
