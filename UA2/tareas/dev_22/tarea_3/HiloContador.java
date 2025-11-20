/**
 * * Autor: Jose Antonio Roda Donoso
 *  * Curso: 2º DAM
 *  * Unidad: UA2 - Tarea 3 HiloContador
 *
 * El encargado de contar los caracteres de un fichero.
 */

public class HiloContador extends Thread {

    private final String fichero;

    public HiloContador(String fichero) {
        this.fichero = fichero;
    }

    @Override
    public void run() {
        try {
            int total = ContadorFichero.contarCaracteres(fichero);
            System.out.println("El fichero " + fichero + " contiene " + total + " caracteres.");
        } catch (Exception e) {
            System.err.println("Error en el fichero: " + fichero);
        }
    }
}
