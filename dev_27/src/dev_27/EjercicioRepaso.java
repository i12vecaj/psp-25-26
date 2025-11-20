package dev_27;

import java.util.Scanner;

public class EjercicioRepaso {

	public static void main(String[] args) {
		
		String nombre;
		int edad;
		
		Scanner sc = new Scanner (System.in);
		System.out.println("Introduce tu nombre");
		
		nombre = sc.nextLine();
		
		System.out.println("Introduce tu edad");
		
		edad = sc.nextInt();
		
		int anioen100 = 2025 - edad + 100;
		System.out.println("Hola, " + nombre + ". Cumplirás 100 años en " + anioen100);
		
		sc.close();
	}

}
