package main;

public class EjecutorLector {

    public static void main(String[] args) {
    	
        try {
        	
            // Ejecuta el programa LectorCadena, lo muestra y espera a que termine
            ProcessBuilder proceso = new ProcessBuilder("java", "LectorCadena");
            proceso.inheritIO();
            Process p = proceso.start();
            p.waitFor();

        } catch (Exception e) {
        	
            // Control de errores
            System.err.println("Error al ejecutar el programa LectorCadena: " + e.getMessage());
            
        }
        
    }
    
}
