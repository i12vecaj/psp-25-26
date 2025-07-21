import java.net.*;

// En el siguiente ejemplo se define un opbjeto InetAddress de nombre dir.  En primer lugar lo utilizamos para obtener la direccion IP de la maquina local en la que se ejecuta el programa, en el ejemplo localhost.
// A continuacion llamamos al metodo pruebaMetodos() llevando el objeti creado.  En dicho metoido se prueban los metodos de la clase InetAddress. Despues utilizamos el objeto para obtener la direccion IP de la uURL www.google.es y volvemos a invocar a pruebaMetodos().
// Por ultimo utilizamos el metodo getAllByName() para ver todas las direcciones IP asignadas a la maquina representada por www.google.es
// Se encierra todo en un bloque try-catch.

public class PSP_UA3_Ejemplo_1_TestInetAddress {
	  public static void main(String[] args) {
	   InetAddress dir = null;
	   System.out.println("========================================================");
	   System.out.println("SALIDA PARA LOCALHOST: ");
	   try {
	 	//LOCALHOST
		dir = InetAddress.getByName("localhost");
		pruebaMetodos(dir);//

		//URL	www.google.es
	    System.out.println("========================================================");
		System.out.println("SALIDA PARA UNA URL:");
		dir = InetAddress.getByName("www.google.es");
		pruebaMetodos(dir);

	     // Array de tipo InetAddress con todas las direcciones IP
	     //asignadas a google.es
	     System.out.println("\tDIRECCIONES IP PARA: " + dir.getHostName());
	     InetAddress[] direcciones = InetAddress.getAllByName(dir.getHostName());
	     for (int i = 0; i < direcciones.length; i++)
	          System.out.println("\t\t"+direcciones[i].toString());

		System.out.println("========================================================");

	  } catch (UnknownHostException e1) {e1.printStackTrace();}
	}// main

	private static void pruebaMetodos(InetAddress dir) {
	      System.out.println("\tMetodo getByName():  " + dir);
		InetAddress dir2;
		try {
			dir2 = InetAddress.getLocalHost();
			System.out.println("\tMetodo getLocalHost(): " + dir2);
		} catch (UnknownHostException e) {e.printStackTrace();}

		// USAMOS METODOS DE LA CLASE
		System.out.println("\tMetodo getHostName(): " + dir.getHostName());
		System.out.println("\tMetodo getHostAddress(): " + dir.getHostAddress());
		System.out.println("\tMetodo toString(): " + dir.toString());
		System.out.println("\tMetodo getCanonicalHostName(): " + dir.getCanonicalHostName());
		}//pruebaMetodos
}//fin