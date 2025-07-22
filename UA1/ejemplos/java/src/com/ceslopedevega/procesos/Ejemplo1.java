package com.ceslopedevega.procesos;
import java.io.IOException;

public class Ejemplo1 {
   public static void main(String[] args) throws IOException  {	   
	   ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");
      for(int i=0; i<1;i++) 
      {
         Process p = pb.start();
      }
   }
}//Ejemplo1

