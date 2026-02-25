public class SimuladorTarea {
    /*
* Nombre: [Alejandro Cordoba Pérez]
* Fecha: [29/10/2025]
* Descripción: En esta tarea vamos a simular la creacion de 3 TAREAS y le vamos a pasar por parametros
* El nombre y el número de iteraciones que va a tener, a continuacion vamos a crear 3 hilos que se encarguen de dichas
* ejecuciones.
* FR implementados: [FR1, FR2...]
*/
    public static void main(String[] args) {
        //Creacion de las tareas con el numero de iteraciones que van a tener
        Tarea tarea1= new Tarea("Tarea 1",3);
        Tarea tarea2= new Tarea("Tarea 2",7);
        Tarea tarea3= new Tarea("Tarea 3",5);
        //Creacion de los hilos para cada tarea
        Thread t1 = new Thread(tarea1);
        Thread t2 = new Thread(tarea2);
        Thread t3 = new Thread(tarea3);
        //Iniciamos los hilos de cada tarea
        t1.start();
        t2.start();
        t3.start();




    }

    public static class Tarea implements Runnable{

        private String nombre;
        private int iterador;

        public Tarea(String nombre, int iterador ) {
            this.nombre=nombre;
            this.iterador=iterador;


        }
        @Override
        public void run() {
            for (int i = 0; i < iterador; i++)
            {
                System.out.println(nombre+" ejecutandose en el hilo "+iterador);
                try
                {
                    Thread.sleep(500,1500);
                    System.out.println(nombre+" finalizada");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            System.out.println("La " + nombre + " ha terminado su ejecución");
        }
                /*
        Diferencia entre crear un hilo y un proceso:
        Basicamente los hilos son listas de ejecuciones ligeras que y forman parte de un proceso,
        además de que compartan memoria por el mismo motivo, también como los hilos como forman parte del proceso
        si el proceso se detiene los hilos mueren.

        Ventajas y Desventajas de la programación concurrente:
        Ventaja: Hace que el programa gestione mejor la ejecución de varios procesos porque hace que se
        ejecuten de forma simúltanea, por lo que no espera que un proceso termine que en el caso de que sea
        un proceso tipo "Entrada/Salida" osea que tenga que esperar una respuesta, lo que haria que se pueda demorar mucho
        la ejecución de todo el programa.
        Desventaja: No es lo más optimo para aplicaciones pequeñas ya que puede que el hecho de que no aplique una
        programación secuencial afecte de forma negativa.

         */ 


    }
}
