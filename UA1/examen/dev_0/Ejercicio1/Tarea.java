public class Tarea implements Runnable{

    private String nombre;

    public Tarea(String nombre){
        this.nombre = nombre;
    }
    @Override
    public void run() {
        System.out.println(nombre+ " iniciada");
        try {
            Thread.sleep(500);
            System.out.println(nombre + " finalizada");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
