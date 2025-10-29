 /*
  * Nombre: [Miguel Ángel VAro Rabadan]
  * Fecha: [29/10/2025]
  * Descripción: [programacion concurrente para una casa inteligente]
  * FR implementados: [FR1, FR2...]
  */

public class Main {
    public static void main(String[] args) {
        Thread lavarRopaThread1 = new Thread(new LavarRopa());
        lavarRopaThread1.start();

        Thread cocinarThread2 = new Thread(new Cocinar());
        cocinarThread2.start();

        Thread limpiarThread3 = new Thread(new Limpiar());
        limpiarThread3.start();
    }
}