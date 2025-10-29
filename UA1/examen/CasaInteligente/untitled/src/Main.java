
 /*
  * Nombre: [Alejandro Jonás López Serrano]
  * Fecha: [29-10-2025]
  * Descripción: [Simulación de tareas simultaneas en una casa]
  * FR implementados: [FR1, FR2, FR3, FR4, FR5]
  */




 public class Main {
    public static void main(String[] args) {

        Limpiar lim = new Limpiar();
        Cocinar coc = new Cocinar();
        Lavar_la_Ropa llr = new Lavar_la_Ropa();

        Thread limpio = new Thread(lim);
        Thread cocino = new Thread(coc);
        Thread lavo_ropa = new Thread(llr);

        limpio.start();
        cocino.start();
        lavo_ropa.start();
    }
}