public class ProcesoTrabajador2 extends Thread {

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando Trabajador 2");

            // Simular trabajo durante 2000 ms
            Thread.sleep(2000);

            System.out.println("Trabajador 2 completado");

        } catch (InterruptedException e) {
            System.out.println("Error: proceso interrumpido");
        }
    }
}