import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        String nombreFichero = "DONQUIJOTEDELAMANCHA.txt";
        //Declarar una variable FileReader
        FileReader fr = null;

        long t_comienzo, t_fin;
        t_comienzo = System.currentTimeMillis();
        try {
            //Abrir el fichero indicado en la variable nombreFichero
            fr = new FileReader(nombreFichero);
            //Leer el primer carácter
            //Se debe almacenar en una variable de tipo int
            int caract = fr.read();
            //Se recorre el fichero hasta encontrar el carácter -1
            //   que marca el final del fichero
            while(caract != -1) {
                //Mostrar en pantalla el carácter leído convertido a char
                System.out.print((char)caract);
                //Leer el siguiente carácter
                caract = fr.read();
            }
        }
        catch (FileNotFoundException e) {
            //Operaciones en caso de no encontrar el fichero
            System.out.println("Error: Fichero no encontrado");
            //Mostrar el error producido por la excepción
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            //Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                if(fr != null)
                    fr.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }

            t_fin = System.currentTimeMillis();
            long t_total = t_fin - t_comienzo;

            System.out.println("\nProcesamiento completado en " + (t_total) + " ms");
        }
    }
}
