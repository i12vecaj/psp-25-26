import java.io.FileReader;

public class ContadorSecuencial {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("Uso: java ContadorSecuencial <archivo1> <archivo2> ...");
            return;
        }

        long start = System.currentTimeMillis();

        for (String nombre : args) {
            int contador = 0;
            try {
                FileReader fr = new FileReader(nombre);
                int c = fr.read();
                while (c != -1) {
                    contador++;
                    c = fr.read();
                }
                fr.close();
                System.out.println("El fichero " + nombre + " tiene " + contador + " caracteres.");
            } catch (Exception e) {
                System.out.println("Error con el fichero " + nombre);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Tiempo total secuencial: " + (end - start) + " ms");
    }
}
