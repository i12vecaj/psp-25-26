package Parte2;

import java.io.IOException;
import java.io.InputStream;

public class LanzadorConsolaError {
    public static void main(String[] args) throws IOException {
        // Ejecución de comando con un endpoint intencionadamente incorrecto
        Process tokenProceso = new ProcessBuilder("CMD", "/C", "ping direcccionInexistente.test").start();
        
        try {
            InputStream flujoBytes = tokenProceso.getInputStream();
            int bitLeido;
            
            // Extracción carácter a carácter de los datos arrojados por consola
            while (true) {
                bitLeido = flujoBytes.read();
                if (bitLeido == -1) {
                    break;
                }
                System.out.print((char) bitLeido);
            }
            flujoBytes.close();
        } catch (Exception error) {
            error.printStackTrace();
        }
        
        // Obtención y muestra por salida del código de salida devuelto por el S.O.
        int statusRetorno;
        try {
            statusRetorno = tokenProceso.waitFor();
            System.out.println("Valor de Salida: " + statusRetorno);
        } catch (InterruptedException errInt) {
            errInt.printStackTrace();
        }
    }
}
