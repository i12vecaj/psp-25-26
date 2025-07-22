package com.ceslopedevega.procesos;
import java.io.File;
import java.io.IOException;
 
public class Ejemplo6 { 
  public static void main(String args[]) throws IOException {
    ProcessBuilder pb = new ProcessBuilder("CMD","/C" ,"DIRR");
    
    File fOut = new File("salida.txt");
    File fErr = new File("error.txt");
 
    pb.redirectOutput(fOut);
    pb.redirectError(fErr); 
    pb.start(); 
  }
}// Ejemplo7
