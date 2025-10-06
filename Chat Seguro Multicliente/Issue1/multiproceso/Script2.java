public class Script2 {
    public static void main(String[] args) {
        System.out.println("Inicio Script 2");
        try {
            Thread.sleep(2000); // Simula tarea de 2 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin Script 2");
    }
}
