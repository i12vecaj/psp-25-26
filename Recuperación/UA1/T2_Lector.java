import java.io.IOException;

public class T2_Lector {
    public static void main(String[] args) {
        StringBuilder informacionLeida = new StringBuilder();

        System.out.println("Introduce texto. Para terminar, escribe un asterisco (*):");

        /*
         * Leemos caracter a caracter desde la entrada estandar.
         * El asterisco actua como caracter de terminacion: al encontrarlo,
         * dejamos de leer y no lo guardamos dentro del texto final.
         */
        try {
            int caracter;

            while ((caracter = System.in.read()) != -1) {
                if ((char) caracter == '*') {
                    break;
                }

                informacionLeida.append((char) caracter);
            }

            System.out.println("Informacion leida: " + informacionLeida);
        } catch (IOException e) {
            System.err.println("Error al leer desde la entrada estandar: " + e.getMessage());
            System.exit(1);
        }
    }
}
