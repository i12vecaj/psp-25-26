// Manuel Cerezo Galisteo (7)
package pruebanivel;

import java.util.Scanner;

public class Class {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Dime tu nombre");
		String nombre = sc.nextLine();

		System.out.println("Dime tu edad");
		int edad = sc.nextInt();

		int b = 100 - edad;
		int c = 2025 + b;

		System.out.println("Te llamas " + nombre + " y tendras 100 años en el año " + c);

	}

}