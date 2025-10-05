package Tarea2;

import java.util.Scanner;

public class taea2 {
    public static void main(String[] args) {
        String cadena ="";
        String salida="";

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una cadena de texto para que el programa la pueda leeer y cuando termines de escribirla introduce un * alfinal de la cadena de texto");
        
          try {
            cadena = sc.nextLine();

            // Verificar si está vacía
            if (cadena.isEmpty()) {
                System.out.println("Error: no se ha introducido ningún texto.");
                return;
            }

            // Verificar si contiene el asterisco
            if (!cadena.contains("*")) {
                System.out.println("Error: la cadena no contiene el carácter de terminación '*'.");
                return;
            }

            // Leer hasta el asterisco
            for (int i = 0; i < cadena.length(); i++) {
                if (cadena.charAt(i) == '*') {
                    break;
                }
                salida += cadena.charAt(i);
            }

            System.out.println("La cadena que usted ha introducido es: " + salida);

        } catch (Exception e) {
            System.out.println("Ocurrió un error al leer la entrada: " + e.getMessage());
        }
    }
}
