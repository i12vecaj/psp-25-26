/*
 * Nombre: [Alejandro Prieto Mellado]
 * Fecha: [29/10/2025]
 * Descripción: [Simulador de tareas
 * FR implementados: [FR1, FR2...]
 */

/*
Imagina que en una casa inteligente se realizan varias tareas al mismo tiempo:
Lavar la ropa
Cocinar
Limpiar

Cada una de estas tareas debe ejecutarse de forma concurrente, como si cada tarea se hiciera por separado pero al mismo tiempo.
Tu objetivo es simular este comportamiento usando hilos en Java.
Requisitos funcionales (FR):

FR1 (2 puntos):
Crear tres clases que implementen Runnable, una para cada tarea: LavarRopa, Cocinar y Limpiar.

FR2 (2 puntos):
En el método run() de cada clase, mostrar un mensaje al iniciar y otro al finalizar la tarea.
Por ejemplo:
[Lavar ropa] Comenzando tarea...
[Lavar ropa] Tarea finalizada.

FR3 (2 puntos):
Simular que cada tarea tarda un tiempo distinto en completarse usando Thread.sleep() (por ejemplo, 1, 2 y 3 segundos).

FR4 (2 puntos):
Desde el método main, crear y arrancar los tres hilos de forma concurrente, mostrando también un mensaje al inicio y al final del programa.

FR5 (2 puntos):
Comprobar que el orden de ejecución de los mensajes puede variar entre ejecuciones, debido a la concurrencia.

*/

package ejercicio3;

public class casainteligente {
    public static void main(String[] args) {

        Thread Lavar = new Thread(new lavar("Lavar Ropa"));
        Thread Cocinar = new Thread(new cocinar("Cocinar"));
        Thread Limpiar = new Thread(new limpiar("Limpiar la casa"));
        
        Lavar.start();
        Cocinar.start();
        Limpiar.start();

    }
    
}

class lavar implements Runnable{
    private String nombre;

    public lavar(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for(int i = 1; i <=10; i++){
            if(i == 1)
                System.out.println("["+nombre+"] iniciando Tarea...");
            if(i > 1 && i < 10)
                System.out.println("["+nombre+"] Lavando");
            if(i == 10)
                System.out.println("["+nombre+"] Tarea finalizada");

            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.getStackTrace();
            }
        }  
    }
}

class cocinar implements Runnable{
    private String nombre;

    public cocinar(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for(int i = 1; i <=15; i++){
            if(i == 1)
                System.out.println("["+nombre+"] iniciando Tarea...");
            if(i > 1 && i < 15)
                System.out.println("["+nombre+"] Cocinando");
            if(i == 15)
                System.out.println("["+nombre+"] Tarea finalizada");

            try{
                Thread.sleep(3000);
            }catch(InterruptedException e){
                e.getStackTrace();
            }
        } 
    }
}

class limpiar implements Runnable{
    private String nombre;

    public limpiar(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for(int i = 1; i <=5; i++){
            if(i == 1)
                System.out.println("["+nombre+"] iniciando Tarea...");
            if(i > 1 && i < 5)
                System.out.println("["+nombre+"] Limpiando");
            if(i == 5)
                System.out.println("["+nombre+"] Tarea finalizada");

            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){
                e.getStackTrace();
            }
        } 
    } 
}
