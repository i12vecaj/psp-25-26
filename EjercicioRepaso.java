import java.util.Scanner;

public class EjercicioRepaso {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean repetidor = false;
        while (repetidor) {
            
            System.out.print("ingresa tu nombre:"); String nombre = sc.nextLine();
            System.out.print("ingresa la edad que tienes:"); int edad = sc.nextInt();
            System.out.print("Año Actual: "); int añoActual = sc.nextInt();

            int Años = añoActual + (100- edad);



            if (edad!=0) {
                System.out.println("hola "+ nombre + "tienes "+ edad + "años"+ "y dentro de 100 años tendras: "+ Años);            
                repetidor = true;
            }else{
                System.out.println("ingresa una edad valida");
                repetidor = false;
            }

        sc.close();
        }
    }
}