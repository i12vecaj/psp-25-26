 /*
  * Nombre: Eduardo Ruz López
  * Fecha: 29/10/2025
  * Descripción: Creacion de un simulador de tareas que ejecuta varias tareas a la vez
  * FR implementados: a,b,c,d,e,f
  */

public class SimuladorTareas implements Runnable{

    private int id;
    private String nombre;

    public SimuladorTareas(int id,String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println(this.nombre + " ejecutandose en el hilo" + this.id);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.nombre + " finalizada");
    }
}
