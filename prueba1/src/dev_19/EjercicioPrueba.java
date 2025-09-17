package dev_19;

import java.util.Scanner;

public class EjercicioPrueba {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String nombre;
		int edad, anioParaSumar, anioActual = 2025, anioFinal;

		System.out.println("Introduce tu nombre: ");
		nombre = sc.next();

		System.out.println("Introduce tu edad: ");
		edad = sc.nextInt();

		anioParaSumar = 100 - edad;

		anioFinal = anioParaSumar + anioActual;

		System.out.println("Bienvenido " + nombre + " cumplirás los 100 en el año " + anioFinal);

	}

}
