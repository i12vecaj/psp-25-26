package com.ceslopedevega.procesos;

import java.io.*;
import java.util.*;

public class Ejemplo5 {
	public static void main(String args[]) throws IOException {
		
		File directorio = new File(".\\bin");
		
		ProcessBuilder test = new ProcessBuilder();
		Map entorno = test.environment();
		System.out.println("Variables de entorno:");
		System.out.println(entorno);

		//test = new ProcessBuilder("java", "LeerNombre", "Luis");		
		test = new ProcessBuilder("java", "com.ceslopedevega.procesos.LeerNombre", "Luis");		
		
		// devuelve el nombre del proceso y sus argumentos
		List l = test.command();
		Iterator iter = l.iterator();
		System.out.println("\nArgumentos del comando:");
		while (iter.hasNext())
			System.out.println(iter.next());
		
		
		// Se lanza el proceso
		ProcessBuilder pb = new ProcessBuilder("java", "com.ceslopedevega.procesos.LeerNombre", "Luis");
		pb.directory(directorio);
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

		// se ejecuta el proceso
		Process p = pb.start();	
		
	}
}// Ejemplo6
