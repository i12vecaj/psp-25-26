package U3.Tareas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class T1 {
    public static void main(String[] arg) throws IOException {
      Scanner sc = new Scanner(System.in);
      String url;

      do {
        System.out.println("Introduce una url para mostrar su información si quieres parar introduce localhost");
        url = sc.nextLine();
        InetAddress dir = null;
        try {
          dir = InetAddress.getByName(url);
          pruebaMetodos(dir);
        } catch (Exception e) {
          System.err.println("Error: "+e.getMessage());
        }
      } while (!url.equals("localhost"));
      
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
