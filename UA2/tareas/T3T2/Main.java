import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Carpeta donde buscar los ficheros
        File carpeta = new File("carpeta");

        // Si no existe, la creamos
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        // 1. Buscamos los ficheros .txt dentro de la carpeta
        String[] ficherosTxt = buscarFicherosTxt("carpeta");

        // 2. Contamos los caracteres de cada fichero encontrado
        Map<String, Integer> conteos = contarCaracteres("carpeta", ficherosTxt);

        // 3. Mostramos resultados
        for (Map.Entry<String, Integer> entry : conteos.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " caracteres");
        }
    }


    public static String[] buscarFicherosTxt(String carpetaRuta) {

        File carpeta = new File(carpetaRuta);

        FilenameFilter filtroTxt = (dir, nombre) -> nombre.toLowerCase().endsWith(".txt");

        File[] archivosTxt = carpeta.listFiles(filtroTxt);

        if (archivosTxt == null) {
            System.out.println("La carpeta no existe o no es accesible.");
            return new String[0];
        }

        // Convertimos los File a String (solo nombres)
        String[] nombres = new String[archivosTxt.length];
        for (int i = 0; i < archivosTxt.length; i++) {
            nombres[i] = archivosTxt[i].getName();
        }

        return nombres;
    }

    public static Map<String, Integer> contarCaracteres(String carpeta, String[] nombresFicheros) {
        Map<String, Integer> resultado = new HashMap<>();

        for (String nombre : nombresFicheros) {
            Path ruta = Path.of(carpeta, nombre);

            try {
                String contenido = Files.readString(ruta);
                resultado.put(nombre, contenido.length());
            } catch (IOException e) {
                // Si no se puede leer, marcamos con -1
                resultado.put(nombre, -1);
            }
        }

        return resultado;
    }
}
