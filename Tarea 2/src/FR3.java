/**
 * Programa que ejecuta FR1FR2.java
 * y muestra su salida en consola.
 */
public class FR3 {

    public static void main(String[] args) {
        // Llamamos directamente al método main del otro programa


        //Al llamar al método main hay que pasarle argumentos obligatoriamente public static void main(String[] args) <-
        String[] vacio = {}; // array vacío para pasar a main sin argumentos
        FR1FR2.main(vacio);
    }
}
