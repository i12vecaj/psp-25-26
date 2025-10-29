 /*
  * Nombre: [Alberto Nieto Lozano]
  * Fecha: [29/10/2025]
  * Descripción: [Breve descripción del ejercicio]
  * FR implementados: [FR1, FR2...]
  */

 import java.util.Random;

 public class CasaInteligente {
    public static void main(String[] args) {

Thread t1 = new Thread(new LavarRopa("Lavar Ropa"));
Thread t2 = new Thread(new Cocinar("Cocinar"));
Thread t3 = new Thread(new Limpiar("Limpiar"));

System.out.println("Comenzando Programa");
t1.start();
t2.start();
t3.start();

    }
}

class LavarRopa implements Runnable {
    private String nombre;
    Random random = new Random();

    public LavarRopa(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++){

            if (i == 1){
                System.out.println(nombre + " Comenzando Tarea....");
            }
            System.out.println(nombre + " Lectura: " + i);

            if (i == 10) {
                System.out.println(nombre + " Finalizando Tarea...");
            }


            try {
                Thread.sleep(random.nextInt((3)+1)*1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}

class Cocinar implements Runnable {
    private String nombre;
    Random random = new Random();

    public Cocinar(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++){
            if (i == 1){
                System.out.println(nombre + " Comenzando Tarea....");
            }
            System.out.println(nombre + " Lectura: " + i);

            if (i == 10) {
                System.out.println(nombre + " Finalizando Tarea...");
            }

            try {
                Thread.sleep(random.nextInt((2)+1)*1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Limpiar implements Runnable {
    Random random = new Random();
    String nombre;

    public Limpiar(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++){

            if (i == 1){
                System.out.println(nombre + " Comenzando Tarea....");
            }

            System.out.println(nombre + " Lectura: " + i);

            if (i == 10) {
                System.out.println(nombre + " Finalizando Tarea...");
            }


            try {
                Thread.sleep(random.nextInt((2)+1)*1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
