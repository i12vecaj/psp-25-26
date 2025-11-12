public class Limpiar extends Tarea{
    public Limpiar(int id) {
        super(id);
    }
    public void run() {
        System.out.println("Comenzando a limpiar...");
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Todo limpio por fin!!");
    }
}
