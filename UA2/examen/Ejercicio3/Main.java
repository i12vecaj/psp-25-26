/*
Explica brevemente (4–5 líneas, dentro del código como comentario) qué has observado respecto a:

priorización de hilos,
He observado que cuando se le coloca la prioridad, el hilo que tiene más prioridad es le primero que entra y además
es el primero en terminar, mientras que el modulo c es el que menos prioridad tiene, entra mas tarde y termina el último

yield(),
cuando ese hilo llega al contador de tres, le cede su turno y entra otro hilo

interrupción,
no he sabido implementarlo para cuando pase 1.5 seg, solo lo he puesto asi: moduloTrabajo2.interrupt(); sé que ha interrumpido el sueño
que tenía de los milisegundos

planificación del sistema operativo.
el sistema operativo planifica los recursos según los hilos que vayan entrando, si mientras más priodadad el sistema operativo le da los recursos(cpu) para realizar esa acción

 */
public class Main {
    public static void main(String[] args) {
        //Creo los tres modulos de trabajo
        ModuloTrabajo moduloTrabajo1 = new ModuloTrabajo("Módulo A");
        ModuloTrabajo moduloTrabajo2 = new ModuloTrabajo("Módulo B");
        ModuloTrabajo moduloTrabajo3 = new ModuloTrabajo("Módulo C");

        //Cambio las prioridades a alta, media y baja
        moduloTrabajo1.setPriority(Thread.MAX_PRIORITY);
        moduloTrabajo2.setPriority(Thread.NORM_PRIORITY);
        moduloTrabajo3.setPriority(Thread.MIN_PRIORITY);

        //Lanzo los hilos
        moduloTrabajo1.start();
        moduloTrabajo2.start();
        moduloTrabajo3.start();

        moduloTrabajo2.interrupt();

        System.out.println("Comprobando si los hilos se estan ejecutando: ");
        System.out.println("Modulo A: "+moduloTrabajo1.isAlive());
        System.out.println("Modulo B: "+moduloTrabajo2.isAlive());
        System.out.println("Modulo C: "+moduloTrabajo3.isAlive());

       try {
           moduloTrabajo1.join();
           moduloTrabajo2.join();
           moduloTrabajo3.join();
       } catch (Exception e) {
           System.out.println("Error a la espera de los hilos: "+e.getMessage());
       }
        System.out.println("Módulo: "+moduloTrabajo1.getNombre()+" finalizado. Estado vivo: "+moduloTrabajo1.isAlive());
        System.out.println("Módulo: "+moduloTrabajo2.getNombre()+" finalizado. Estado vivo: "+moduloTrabajo1.isAlive());
        System.out.println("Módulo: "+moduloTrabajo3.getNombre()+" finalizado. Estado vivo: "+moduloTrabajo1.isAlive());
    }
}