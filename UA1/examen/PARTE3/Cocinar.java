public class Cocinar extends Tarea{
    public Cocinar(int id) {
        super(id);
    }
    public void run() {
        System.out.println("Preparando la cocina...");
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Comida preparada!!");
    }
}
