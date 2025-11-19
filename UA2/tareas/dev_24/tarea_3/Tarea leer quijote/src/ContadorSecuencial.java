import java.io.FileReader;

public class ContadorSecuencial {
    public static void main(String[] args) {
        String nombreFichero = "el_quijote.txt";
        FileReader fr = null;
        int contadorCaracteres = 0;

        try {
            fr = new FileReader(nombreFichero);
            int caract = fr.read();
            while (caract != -1) {
                System.out.print((char) caract); // Mostrar el carácter
                contadorCaracteres++;             // Contar cada carácter
                caract = fr.read();
            }
            System.out.println("\nTotal de caracteres: " + contadorCaracteres);
        } catch (Exception e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        } finally {
            try {
                if (fr != null) fr.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar el fichero: " + e.getMessage());
            }
        }
    }
}
