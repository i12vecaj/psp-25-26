import java.io.FileNotFoundException;
import java.io.FileReader;

/*
Implementamos el Runnable.
Añadimos el atributo int id y creamos la estructura del cuerpo de la clase, en este caso "Main".
 */

public class Main implements Runnable {
    private int id;
    public Main(int id) {
        this.id = id;
    }
    public static void main(String[] args) {

        /*
Con este "for" creamos un Hilo por cada archivo que vayamos a leer, en mi caso 3 archivos.
         */
        for  (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new Main(i));
            t.start();

        }

    }

    public void leerFichero(String fichero) {

        /*
Este metodo recogera el titulo del archivo que le demos al runable y procedera a buscarlo, leerlo y contabilizar el numero de Caracteres.
         */

        String ruta = ("C:/Users/Dany26G/IdeaProjects/Proyecto de pruebas/src/"+fichero);

        /*
Creamos un String llamado "ruta" donde escribiremos la direccion del directorio donde se encontraran nuestros ficheros y lo acompañamos de la variable fichero.
         */

        FileReader fr = null; //Creamos el FileReader.
        try {
            fr = new FileReader(ruta);//añadimos la direccion del fichero y nombre del fichero con el String ruta.
            StringBuilder contenido = new StringBuilder(); //Creamos StringBuilder, una clase que interactuara con la variable siempre que sea un String
            int c;
            while ((c = fr.read()) != -1) {
                contenido.append((char) c);
            }

            /*
Creamos una variable lee letra por letra el fichero y añade el contenido al variable contenido.
             */

            System.out.println("Hilo " + id + " -> Total caracteres: " + contenido.length());//devuelve la longitud del contenido
            System.out.println("Hilo " + id + " -> Contenido: " + contenido);//devuelve el contenido escrito del fichero
        } catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado"); //Actua en caso de Fallo en encontrar el fichero.
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Fallo al leer el fichero"); //Actua en caso de no poder leer el fichero.
            System.out.println(e.getMessage());
        } finally {
            try {
                if(fr != null) fr.close();
            } catch (Exception e) {
                System.out.println("Error: Fallo al cerrar el fichero"); // Actua en caso de no poder cerrar el fichero.
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void run() {
        long t_comienzo, t_fin; //Creamos las variables de tiempo.
        t_comienzo = System.currentTimeMillis(); //Iniciamos el tiempo
        System.out.println("Ejecutando hilo "+id);
        leerFichero("texto"+(id)+".txt"); //ejecutamos la anterior funcion, la cual se encargara de buscar y analizar el fichero.
        t_fin = System.currentTimeMillis(); //Finaliza el Tiempo
        long t_total = t_fin - t_comienzo; //Creamos la variable que es la diferencia entre el inicio y el fin.
        System.out.println("Tiempo total empleado del hilo "+id+" -> "+t_total); //Mostramos el tiempo total del Hilo
    }
}

/*
El mayor problema que veo es tener que asegurarte de que cada vez que creas un hilo, lea el fichero que quieres,
Lo cual suele ser dificil en este caso donde queremos leer varios ficheros a la vez,
aunque cierto es que a nivel de codigo y sintaxis acorto tiempo y contenido de escritura,
el cual estoy usando para comentar este codigo :P
 */
