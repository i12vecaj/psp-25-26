package com.ceslopedevega.procesos;
import java.io.*;
public class EjemploLectura{
 public static void main (String [] args)
 {
   InputStreamReader in = new InputStreamReader(System.in);
   BufferedReader br = new BufferedReader (in);
   String texto;
   try {
    System.out.println("Introduce una cadena....");
    texto= br.readLine();
    System.out.println("Cadena escrita: "+texto); 
    in.close();	 
   }catch (Exception e) { e.printStackTrace();}	
 }
}//EjemploLectura
