public class Main{

    public static void main(String[] args){
        //Creamos tantos lectores como ficheros vayamos a leer
        LectorFicheros lector = new LectorFicheros("quijote.txt");
        LectorFicheros lector2 = new LectorFicheros("fichero2.txt");
        LectorFicheros lector3 = new LectorFicheros("fichero3.txt");

        //Asignamos cada lector a un hilo
        Thread hilo1 = new Thread(lector);
        Thread hilo2 = new Thread(lector2);
        Thread hilo3 = new Thread(lector3);

        //FR3
        long t_comienzo, t_fin;
        t_comienzo = System.currentTimeMillis();
        //Ejecutamos los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        t_fin = System.currentTimeMillis();
        long t_total = t_fin - t_comienzo;

    }
}

//Evidentemente al leer mas de un fichero el tiempo de ejecucion es algo mayor ya que tiene que
// contar mas caracteres pero la diferencia realmente es poco notable.