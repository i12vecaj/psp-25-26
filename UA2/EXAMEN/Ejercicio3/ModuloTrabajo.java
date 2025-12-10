package Ejercicio3;

public class ModuloTrabajo extends Thread {
    private  String nombre;

    @Override
    public String toString() {
        return "MóduloTrabajo{nombre='"+nombre+"', id="+getId()+", prioridad="+getPriority()+"}";
    }

    public ModuloTrabajo(String name){
        nombre = name;
    }

    @Override
    public void run() {
        System.out.println("Módulo "+nombre+" iniciado. ID:"+getId());
        for(int i=1; i<=5;i++){
            System.out.println("Módulo "+nombre+" - iteracción "+i);
            try {
                sleep(500);
                
            } catch (InterruptedException e) {
                System.out.println("Módulo "+nombre+" interrumpido durante la ejecución");
            }
            if (i==3) {
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        ModuloTrabajo ModuloA = new ModuloTrabajo("MóduloA");
        ModuloTrabajo ModuloB = new ModuloTrabajo("MóduloB");
        ModuloTrabajo ModuloC = new ModuloTrabajo("MóduloC");

        ModuloA.setPriority(1);
        ModuloB.setPriority(5);
        ModuloC.setPriority(10);


        ModuloA.start();
        ModuloB.start();
        ModuloC.start();
        try {
            sleep(1500);
            ModuloB.interrupt();
        } catch (InterruptedException e) {
           
            e.printStackTrace();
        }

        System.out.println("Modulo A "+ModuloA.isAlive());
        System.out.println("Modulo B "+ModuloB.isAlive());
        System.out.println("Modulo C "+ModuloC.isAlive());

        try {
            ModuloA.join();
            ModuloB.join();
            ModuloC.join();
            System.out.println("Módulo "+ModuloA.nombre+" finalizado. Estado vivo: "+ModuloA.isAlive());
            System.out.println("Módulo "+ModuloB.nombre+" finalizado. Estado vivo: "+ModuloB.isAlive());
            System.out.println("Módulo "+ModuloC.nombre+" finalizado. Estado vivo: "+ModuloC.isAlive());
        } catch (InterruptedException e) {
           
            e.printStackTrace();
        }

        ModuloA.toString();
        ModuloB.toString();
        ModuloC.toString();
    }
}

/* La priorización de hilos es buena para tener más o menos una intuición de como es el orden en el que se va a ejecutar */

/* Yield le cede ese turno al siguiente hilo que hay etras de el  */

/* La interrupción es una buena idea si quieres hacer algo con respecto a las excepciones etc.. */

/* La planificación del sistema operativo es que tu puedes poner el orden que tu quieras pero luego al final de todo la CPU es la que manda en el orden */
