// package com.ceslopedevega.procesos;
import java.io.*;

public class Parte3_RedireccionAFichero {
    public static void main(String[] args) {

        File archivoSalida = new File("C:\\Users\\alejo\\Desktop\\ProcesosYServicios\\Procesos_Act1\\salida.txt");


        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "ping google.com");
        pb.redirectOutput(archivoSalida);

        try {

            Process p = pb.start();


            int exitVal = p.waitFor();
            System.out.println("Valor de salida: " + exitVal);


            try (BufferedReader brer = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
                String lineaError;
                while ((lineaError = brer.readLine()) != null) {
                    System.out.println("ERROR > " + lineaError);
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
