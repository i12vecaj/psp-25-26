//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Tarea tarea1 = new Tarea();
        Tarea tarea2 = new Tarea();
        Tarea tarea3 = new Tarea();

        Thread t1 = new Thread(tarea1){
            @Override
            public void run() {System.out.println("Tarea 1 ejecutándose en el hilo 1");}
        };

        Thread t2 = new Thread(tarea2){
            @Override
            public void run() {System.out.println("Tarea 2 ejecutándose en el hilo 2");}
        };

        Thread t3 = new Thread(tarea3){
            @Override
            public void run() {System.out.println("Tarea 3 ejecutándose en el hilo 3");}
        };


       t1.start();
       t2.start();
       t3.start();

        try {
            t1.sleep(500);
            System.out.println("Tarea 1 finalizada.");
            t2.sleep(500);
            System.out.println("Tarea 2 finalizada.");
            t3.sleep(500);
            System.out.println("Tarea 3 finalizada.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //El proceso es la ejecucion de las tareas y funciones del codigo siguiendo un orden establecido.
        //El hilo es es un ayudante que nos permite ejecutar el proceso cuando se lo pidamos, con lo que le pidamos y reduciendo nuestra carga de trabajo total.

    }
}