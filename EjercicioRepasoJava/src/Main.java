import java.util.Scanner;

// Programa que nos solicita el nombre y el año de una persona
// y le devolvemos en que año cumple 100 años.
public class Main {
    public static void main(String[] args) {

        // Clase Scanner que nos permite capturar por teclado
        Scanner teclado = new Scanner(System.in);

        // Variables
        String nombre;
        int edad;

        // Solicitud de datos por teclado

        System.out.println("Introuce tu nombre");
        nombre = teclado.nextLine();
        System.out.println("Introuce tu edad");
        edad = teclado.nextInt();

        // Algoritmo para calcular el año que cumples 100
        int edadFinal = (2025-edad)+100;
        //Imprimimos el resultado
        System.out.printf("Nombre " + nombre + " Este año cumples 100 años: " + edadFinal);



    }
}