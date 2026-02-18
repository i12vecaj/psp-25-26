import java.util.Scanner;

public class NombreEdad {

	public String nombre;
	public int edad;
	
	public NombreEdad(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public static void main(String[] args) {
		
		Scanner s =  new Scanner(System.in);
		NombreEdad nuevo = new NombreEdad("q",0);
		
		System.out.println("Introduzca su nombre: ");
		String Nomb = s.nextLine();
		nuevo.setNombre(Nomb);
		int ed;
		while(true) {
		System.out.println("Introduzca su edad: ");
		if (s.hasNextInt()) {
			ed = s.nextInt();
			nuevo.setEdad(ed);
			break;
		}
		else {
			System.out.println("Se requiere un numero exacto de años.");
        s.next();
		}}

		
		
		System.out.println("Hola "+ nuevo.getNombre()+ ", tu edad es "+ nuevo.getEdad() + " años.");

	}

}
