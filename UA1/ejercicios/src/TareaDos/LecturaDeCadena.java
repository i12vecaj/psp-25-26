package TareaDos;

import java.util.Scanner;

public class LecturaDeCadena {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //En esta variable es donde voy a guardar la cadena de texto
        String texto = "";
        //Esta variable me va a servir para saber cuando terminar el bucle
        boolean encontrado = false;
        System.out.println("Introduce el texto, para acabar introduce '*' : ");

        while (!encontrado) {
            String linea;
            //se lee una linea completa
            linea = sc.nextLine();

            for (int i = 0; i < linea.length(); i++) {
                //metodo de string y sirve para obtener un solo caracter de la cadena de texto
                char c = linea.charAt(i);

                if (c == '*') {
                    encontrado = true; //Se cambia para saber que se ha encontrado el *
                    break; // Me salgo del bucle
                } else {
                    texto += c;
                }


            }
        }
        System.out.println("Texto introducido: \n" + texto);

    }
}
