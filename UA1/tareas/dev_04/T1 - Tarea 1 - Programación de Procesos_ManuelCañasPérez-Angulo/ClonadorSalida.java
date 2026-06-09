package Parte3;

import java.io.*;

public class ClonadorSalida {
    public static void main(String[] args) throws IOException {
        Process runner = new ProcessBuilder("CMD", "/C", "ping google.com").start();
        
        // Uso de bloques Try-With-Resources para asegurar la liberación de los descriptores de archivo
        try (
            InputStream readStream = runner.getInputStream();
            FileWriter archivoEscritor = new FileWriter("Salida.txt");
            PrintWriter escritorTexto = new PrintWriter(archivoEscritor)
        ) {
            int byteDatos;
            // Lectura repetitiva e inserción simultánea en terminal y en fichero de texto plano
            while ((byteDatos = readStream.read()) != -1) {
                char simbolo = (char) byteDatos;
                System.out.print(simbolo);
                escritorTexto.print(simbolo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}