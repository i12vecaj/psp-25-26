package Parte1;

import java.io.IOException;
import java.io.InputStream;

public class LanzadorConsola {
    public static void main(String[] args) throws IOException {
        // Lanzamiento del subproceso del sistema
        Process subProceso = new ProcessBuilder("CMD", "/C", "ping google.com").start();
        
        try {
            InputStream canalEntrada = subProceso.getInputStream();
            int asciiChar;
            
            // Volcado por pantalla de la respuesta secuencial del flujo
            while (true) {
                asciiChar = canalEntrada.read();
                if (asciiChar == -1) {
                    break;
                }
                System.out.print((char) asciiChar);
            }
            canalEntrada.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Captura del código de estado final
        int codigoCierre;
        try {
            codigoCierre = subProceso.waitFor();
            System.out.println("Valor de Salida: " + codigoCierre);
        } catch (InterruptedException exInt) {
            exInt.printStackTrace();
        }
    }
}
