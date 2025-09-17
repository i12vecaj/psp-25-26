import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String nombre = "";
        int edad = 0;
        int aniosRestantes = 0;

        System.out.println("Introduce tu nombre:");
        nombre = sc.nextLine();

        System.out.println("Introduce tu edad:");
        edad = sc.nextInt();

        aniosRestantes = 100 - edad; // Calcular cuantos quedan para cumplir 100 años

        System.out.println("Hola " + nombre + ", cumplirás 100 años en " + (2025 + aniosRestantes));

        }
    }
