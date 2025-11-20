package actProcessBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Parte5 {

	//Es peligroso hacer que el usuario introduzca cualquier comando, puesto que puede acceder a nuestro sistema y cargárselo por completo
	public static void main(String[] args) throws IOException   {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe un comando para ejecutar el proceso");
		String comando;
		comando = sc.nextLine();
		
		
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", comando);
		Process p = pb.start();
		try (Scanner salida = new Scanner(p.getInputStream());
	             Scanner error = new Scanner(p.getErrorStream())) {

	            while (salida.hasNextLine()) {
	                System.out.println(salida.nextLine()); // stdout
	            }

	            while (error.hasNextLine()) {
	                System.err.println(error.nextLine()); // stderr
	            }

	            int exitVal = p.waitFor();
	            System.out.println("Valor de salida: " + exitVal);

	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	}
}
