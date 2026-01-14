package socketstarea1;

import java.net.*;
import java.util.Scanner;


public class TAREA1 {
	  public static void main(String[] args) {
	   InetAddress dir = null;
	   String url = "";
	   do {
		System.out.println("Introduce una URL para ver sus datos, para salir introduce localhost:");
		try {
		Scanner sc = new Scanner(System.in);
		url = sc.nextLine();
		dir = InetAddress.getByName(url);
		pruebaMetodos(dir);
		}catch(UnknownHostException e1) {}
		   }while(!url.equals("localhost"));

	}

	private static void pruebaMetodos(InetAddress dir) {
	      System.out.println("\tMetodo getByName():  " + dir);
		InetAddress dir2;
		try {
			dir2 = InetAddress.getLocalHost();
			System.out.println("\tMetodo getLocalHost(): " + dir2);
		} catch (UnknownHostException e) {e.printStackTrace();}

		System.out.println("\tMetodo getHostName(): " + dir.getHostName());
		System.out.println("\tMetodo getHostAddress(): " + dir.getHostAddress());
		System.out.println("\tMetodo toString(): " + dir.toString());
		System.out.println("\tMetodo getCanonicalHostName(): " + dir.getCanonicalHostName());
		}
}