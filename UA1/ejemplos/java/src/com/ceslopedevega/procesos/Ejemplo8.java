package com.ceslopedevega.procesos;
import java.io.IOException;

public class Ejemplo8 {
	public static void main(String args[]) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");

		// Salida a consola
		// Redirect.INHERIT indica que la entrada y salida del proceso
		// será la misma que el proceso actual (padre)
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);	    
		Process p = pb.start();		
	}
}// Ejemplo9
