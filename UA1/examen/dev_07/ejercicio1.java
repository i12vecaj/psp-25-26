/*
 * Nombre:Manuel Cerezo Galisteo
 * Fecha: 29/10/2025
 * Descripción: Simulación de ejecución de procesos en paralelo y secuencial.
 * FR implementados: FR1, FR2, FR3
 */

import java.io.File;
import java.io.FileWriter;

public class ejercicio1 {
    public static void main(String[] args) {
    try{
        new File("logs").mkdirs();


        hilosimple hilo1 = new hilosimple(1, 10);
		hilosimple hilo2 = new hilosimple(2, 4);
		hilosimple hilo3 = new hilosimple(3, 5);
		
		// Se crean los hilos (no extienden Thread directamente)
		Thread t1 = new Thread(hilo1);
		Thread t2 = new Thread(hilo2);
		Thread t3 = new Thread(hilo3);

        t1.start();
		t2.start();
		t3.start();

            FileWriter log = new FileWriter("resultados_multiproceso.txt", false);
            
            log.write("Simulación de Procesos");

        long startPar = System.currentTimeMillis();
            Process tarea1 = new ProcessBuilder("java", "ScriptA").start();
            Process tarea2 = new ProcessBuilder("java", "ScriptB").start();
            Process tarea3 = new ProcessBuilder("java", "ScriptC").start();

            tarea1.waitFor();
            tarea2.waitFor();   
            tarea3.waitFor();



             long endPar = System.currentTimeMillis();
            long tiempoParalelo = endPar - startPar;
            log.write("Tiempo paralelo: " + tiempoParalelo + " ms\n");


            log.close();
            System.out.println("Simulación completada. Revisa logs/resultados_multiproceso.txt");
} catch(Exception e){
    System.out.println("Se ha producido una excepción: " + e.getMessage());
    
}
    }
}

/*
La diferencia entre proceso y hilo es que un proceso es una instancia (palabra clave) de un programa en ejecución que tiene su propio
 espacio de memoria, mientras que un hilo pertenece a dentro de la ejecución de un proceso que comparte el mismo espacio 
 de memoria con otros hilos del mismo proceso. Los procesos son más pesados en términos de recursos del sistema y tienen 
 una mayor sobrecarga para la comunicación entre ellos, mientras que los hilos son más ligeros y permiten una comunicación 
 más eficiente al compartir el mismo espacio de memoria. Pero los hilos no pueden tener errores ya que esto lo arrastraria a 
 los demás hilos que se implementen dentro del mismo proceso.
 */
