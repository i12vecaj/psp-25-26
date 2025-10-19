public class ProcesoTrabajador3 extends Thread {

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando Trabajador 3");

            // Simular trabajo durante 2500 ms
            Thread.sleep(2500);

            System.out.println("Trabajador 3 completado");

        } catch (InterruptedException e) {
            System.out.println("Error: proceso interrumpido");
        }
    }
}