public class Tarea implements Runnable{
    private String nombre;

    public Tarea(String nombre){
        this.nombre = nombre;
    }
    public void run(){
        System.out.println(nombre+ " ejecutandose en el hilo " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("La tarea " + nombre + " ha sido ejecutada del hilo " + Thread.currentThread().getName());
    }
}
