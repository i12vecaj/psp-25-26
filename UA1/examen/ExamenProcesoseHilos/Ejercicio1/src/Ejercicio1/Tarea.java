package Ejercicio1;

import java.util.Random;

public class Tarea implements Runnable{
    String nombre;
    int id; 
    int numHilo=0;
    Random random = new Random();
    int iter = 6; //numero de iteraciones

    public Tarea(String nombre) {        
        this.nombre = nombre;        
    }
    
    public void run() {
            for (int i = 0; i < iter; i++) {
			numHilo++; //sumo el hilo para simular que entra en otro hilo dentro de la tarea
            System.out.println(nombre+": ");
            long dormir = random.nextLong(500,1500); //intervalo de Sleep
            try {
                System.out.println(nombre+" ejecutándose en el hilo "+numHilo);
                Thread.sleep(dormir);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println(nombre+" finalizada");        
    }

    

    
}
