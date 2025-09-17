package nivel; //24//
import java.util.Scanner;

public class EjercicioRepaso {
	public static void main(String[] args) {
		
	Scanner sc = new Scanner(System.in);
		
	String nombre;
	int edad;
	int anoActual = 2025;
	int anio;
	
	System.out.println("Ponga su nombre");
	nombre = sc.nextLine();
	
	System.out.println("Ponga su edad");
	edad = sc.nextInt();
	
	anio = (anoActual + 100) -edad;
	System.out.println("Hola"+nombre+" tienes:"+edad+" Ana, cumplirás 100 años en el año "+anio);
	
	
	
	
	}

}
