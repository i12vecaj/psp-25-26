package com.ceslopedevega.procesos;
import java.io.*;

public class EjecutaComando {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Ejecutar PING y guardar en salida.txt
        Process p = new ProcessBuilder("CMD", "/C", "PING www.google.com").start();

        try (
                InputStream is = p.getInputStream();
                FileOutputStream fos = new FileOutputStream("C:\\Users\\Jose\\Desktop\\salida.txt");
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            int c;
            while ((c = is.read()) != -1) {
                bos.write(c);
            }
            bos.flush();
        }

        int exitVal = p.waitFor();
        System.out.println("PING terminado con código: " + exitVal);
        System.out.println("Resultado guardado en salida.txt");

        //Ejecutar DIR y guardar en dir_salida.txt
        String desktopPath = "C:\\Users\\Jose\\Desktop\\dir_salida.txt";
        ProcessBuilder pb1 = new ProcessBuilder("CMD", "/C", "dir");
        pb1.redirectOutput(new File(desktopPath));
        Process p1 = pb1.start();
        p1.waitFor();
        System.out.println("Listado guardado en dir_salida.txt");

        //Mostrar el contenido de dir_salida.txt
        ProcessBuilder pb2 = new ProcessBuilder("CMD", "/C", "type", desktopPath);
        pb2.inheritIO();
        Process p2 = pb2.start();
        p2.waitFor();
    }
}
