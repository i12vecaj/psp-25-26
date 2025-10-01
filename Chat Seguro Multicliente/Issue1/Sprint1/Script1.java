package Sprint1;


public class Script1 {
    public static void main(String[] args) {
        try {
            System.out.println("Script1 iniciado...");
            Thread.sleep(2000); // Simula trabajo
            System.out.println("Script1 terminado.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

