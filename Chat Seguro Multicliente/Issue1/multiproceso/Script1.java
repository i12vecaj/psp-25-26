public class Script1 {
    public static void main(String[] args) {
        System.out.println("Inicio Script 1");
        try {
            Thread.sleep(3000); // Simula tarea de 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin Script 1");
    }
}
