package Tarea3.Secuencial;

/*
FR1: Crea un programa que reciba a través de sus argumentos una lista de ficheros de texto
y cuente el número de caracteres que hay en cada fichero (ejecución secuencial).
*/

public class Main {
    public static void main(String[] args) {

        // Verifico que se hayan pasado al menos dos ficheros como argumentos
        if (args.length < 2) {
            System.err.println("Error: Debes proporcionar al menos dos ficheros de texto como argumentos.");
            return;
        }

        //Guardo el tiempo de inicio para medir cuánto tarda el procesamiento secuencial
        long t_comienzo = System.currentTimeMillis();

        //Recorre cada fichero recibido como argumento
        for (String nombreFichero : args) {
            //metodo contar() de la clase ContarCaracteres para obtener el número de caracteres
            long numCaracteres = ContarCaracteres.contar(nombreFichero);

            // Mostramos el resultado por consola, indicando el nombre del fichero y su conteo
            System.out.println("Fichero: " + nombreFichero + " → " + numCaracteres + " caracteres");
        }

        // se calcula el tiempo total de ejecución secuencial
        long t_fin = System.currentTimeMillis();
        System.out.println("Tiempo total (secuencial): " + (t_fin - t_comienzo) + " ms");
    }
}
