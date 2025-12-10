
public class ModuloTrabajo extends Thread {
    private int id;
    private int iter;
    private String nombre;

    public static int randomEntre(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }

    public ModuloTrabajo (int id, int iter, String nombre)
    {
        this.id = id;
        this.iter = iter;
        this.nombre = nombre;
    }

    public void run()
    {

        System.out.println("Módulo" + nombre + " iniciado. ID: " + id);
        for (int i = 0; i < iter; i++)
        {
            System.out.println("Módulo " + nombre +" - iteración " + i);



            if (i == 3) {
                Thread.yield(); //Llamo al método yield() en la tercera iteracción
            }


            try
            {

                Thread.sleep(randomEntre(300,800)); //Esperar un número aleatorio entre 300ms y 800ms
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                System.out.println("Módulo " + nombre + "interrumpido durante la ejecución");
            }


        }


        System.out.println("Módulo " + nombre + "finalizado. Estado vivo: " + isAlive());
    }


    @Override
    public String toString() {
        return "{nombre= "+ nombre +
                " id=" + id +
                ", prioridad=" + getPriority() + '\'' +
                '}';
    }
}