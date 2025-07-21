package com.ceslopedevega.hilos;

public class EjemploHiloPrioridad2 extends Thread {
    EjemploHiloPrioridad2(String nom) {
        this.setName(nom);
    }
    
    public void run() {
        System.out.println("Ejecutando [" + getName() + "]");
        for (int i = 1; i <= 5; i++) 
            System.out.println("\t("+getName()+": " + i+")");   
    } 
    
    public static void main(String[] args) {
       EjemploHiloPrioridad2 h1 = new EjemploHiloPrioridad2("Uno");
       EjemploHiloPrioridad2 h2 = new EjemploHiloPrioridad2("Dos");
       EjemploHiloPrioridad2 h3 = new EjemploHiloPrioridad2("Tres");
       EjemploHiloPrioridad2 h4 = new EjemploHiloPrioridad2("Cuatro");
       EjemploHiloPrioridad2 h5 = new EjemploHiloPrioridad2("Cinco");    
       //asignamos prioridad
       h1.setPriority(Thread.MIN_PRIORITY);
       h2.setPriority(3); 
       h3.setPriority(Thread.NORM_PRIORITY);
       h4.setPriority(7); 
       h5.setPriority(Thread.MAX_PRIORITY);
       //se ejecutan los hilos
       h1.start();
       h2.start();
       h3.start();
       h4.start();
       h5.start();
    }
}//EjemploHiloPrioridad2
