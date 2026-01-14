package main;

import java.io.*;
import java.net.*;

public class Servidor {
	
  public static void main(String[] arg) throws IOException {
	  
	int Puerto = 6000;
	ServerSocket Servidor = new ServerSocket(Puerto);
    System.out.println("Escuchando en " + Servidor.getLocalPort());

    Socket cliente1 = Servidor.accept();

    Socket cliente2 = Servidor.accept();

    Servidor.close();
    
  }

}