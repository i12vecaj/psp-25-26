//FR1 y FR2 el 3 esta en el README
package tarea_3;

public class ua2tarea3 {
    public static void main(String[] args) {

        //FR1:

        //declaramos lo ficheros como String para que lo podamos leer
        String libro1 = "tarea_3/el_quijote.txt";
        String libro2 = "tarea_3/calisto_y_melibea.txt";
        String libro3 = "tarea_3/wigetta.txt";

        //creamos los hilos con las variables del constructor
        hilolectura h1 = new hilolectura("El quijote",libro1);
        hilolectura h2 = new hilolectura("Calisto y Melibea", libro2);
        hilolectura h3 = new hilolectura("Wigeta", libro3);

        //iniciamos los hilos
        h1.start();
        h2.start();
        h3.start();

    }

}