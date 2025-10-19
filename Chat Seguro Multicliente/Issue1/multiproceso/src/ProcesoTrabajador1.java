public class ProcesoTrabajador1 extends Thread {

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando Trabajador 1");

            // Simular trabajo durante 1500 ms
            Thread.sleep(1500);

            System.out.println("Trabajador 1 completado");

        } catch (InterruptedException e) {
            System.out.println("Error: proceso interrumpido");
        }
    }
}