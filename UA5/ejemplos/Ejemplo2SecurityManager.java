import java.io.*;
public class Ejemplo2SecurityManager {
   public static void main(String[] args) {
   System.setSecurityManager(new SecurityManager());
	try {
		// Escritura en fichero
		BufferedWriter fichero = new BufferedWriter  (new FileWriter("fichero.txt"));
		fichero.write("Escritura de una linea en fichero.");
		fichero.newLine(); // escribe un salto de línea
		fichero.close();
		System.out.println("Final proceso de escritura...");

		// Lectura en fichero
		BufferedReader fichero2 = new BufferedReader (new FileReader("fichero.txt"));
		String linea = fichero2.readLine();
		System.out.println("Contenido del fichero: ");
		System.out.println("\t" + linea);
		fichero2.close();
		System.out.println("Final proceso de lectura...");

		} catch (FileNotFoundException fn) {
			System.out.println("No se encuentra el fichero");
		} catch (IOException io) {
			System.out.println("Error de E/S ");
		} catch ( Exception e) {
			System.out.println("ERROR => " + e.toString());
		}
   }
}
