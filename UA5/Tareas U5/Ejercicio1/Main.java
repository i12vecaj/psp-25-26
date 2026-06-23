package Ejercicio1;

public class Main {
    public static void main(String[] args) {

        String texto = "Generando un MD5 de un texto";

        String respuesta = HashAPI.generarHashMD5(texto);

        System.out.println(respuesta);
    }
}