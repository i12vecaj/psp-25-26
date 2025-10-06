import java.util.Scanner;

public class Ejercicio_BAse {
	
 public static void main(String[] args) {
	 
	 Scanner sc = new Scanner (System.in);
	 
	 String nombre;
	 int edad;
	 int anio=2025;
	 int anio100;
	 
	 System.out.println("Hola como te llamas??");
	 nombre = sc.nextLine();
	 
	 System.out.println("Buenas, "+nombre+ " cuantos años tienes?");
	 edad= sc.nextInt();
	 
	 anio100= (anio+100) - edad;
	 
	 System.out.println("Tendras 100 años en el año "+anio100);
	 
	 
}

}
