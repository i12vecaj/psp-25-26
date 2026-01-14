/*Crea un programa en Java que admita desde la línea de comandos una URL y visualice información sobre esta. 
Modifica el programa para que admita continuamente nuevas IP o URL y muestre la información hasta que el usuario inserte 
"localhost". */

package tareas.dev_07.Tarea1;

public class Main {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Introduce una URL o IP (\"localhost\" para salir): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("localhost")) {
                System.out.println("Saliendo del programa.");
                break;
            }

            try {
                java.net.InetAddress address = java.net.InetAddress.getByName(input);
                System.out.println("Nombre del Host: " + address.getHostName());
                System.out.println("Dirección del Host: " + address.getHostAddress());
                System.out.println("El tiempo es de: " + address.isReachable(5000));
            } catch (Exception e) {
                System.out.println("No se pudo resolver la dirección: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

}
