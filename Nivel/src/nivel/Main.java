package nivel;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce tu nombre ");
		String name=sc.nextLine();
		System.out.println("Dime tu edad:");
		int age=sc.nextInt();
		int agefinal=2025+(100-age);
		System.out.println("Hola "+name+" cumpliras 100 años en "+agefinal);
	}
	
	

}
