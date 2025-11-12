public class LavarRopa extends Tarea{
    public LavarRopa(int id) {
        super(id);
    }

    @Override
    public void run() {
        System.out.println("Lavando ropa...");
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Ropa lavada completamente!!");
    }
}
