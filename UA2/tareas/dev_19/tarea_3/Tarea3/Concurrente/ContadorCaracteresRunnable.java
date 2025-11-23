package Tarea3.Concurrente;


public class ContadorCaracteresRunnable implements Runnable {
    private String fichero; // Nombre del fichero

    public ContadorCaracteresRunnable(String fichero) {
        this.fichero = fichero;
    }

    @Override
    public void run() {
        // metodo se ejecuta cuando el hilo comienza
        // metodo contar() para obtener el número de caracteres del fichero
        long numCaracteres = ContarCaracteres.contar(fichero);

        //se muestra el resultado por consola, indicando el nombre del fichero y su conteo.
        System.out.println("Fichero: " + fichero + " = " + numCaracteres + " caracteres");
    }
}
