
/*
 * Nombre: Rafa Moreno Moreno
 * Fecha: 29/10/2025
 * Descripción: Uso de hilos para crear tareas
 * FR implementados: FR1, FR2
 */
public class Tarea implements Runnable {
    private int id;
    private int iter;
    private String nombre;

    public Tarea (int id, int iter, String nombre)
    {
        this.id = id;
        this.iter = iter;
        this.nombre = nombre;
    }

    public void run()
    {
        System.out.println(nombre + " ejecutándose en el hilo " + id);

        for (int i = 0; i < iter; i++)
        {
            System.out.println("Iteración " + i + " dentro del hilo " + id);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }





        System.out.println(nombre + " finalizada");
    }
}

