
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final String FICHERO = "libro.data";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opc;

        do {

            System.out.println("Ingresar una opcion");
            System.out.println("1. Añadir Datos");
            System.out.println("2. Mostrar todos los libros");
            System.out.println("3. Buscar por titulo");
            System.out.println("4. Eliminar libro");
            System.out.println("0. Salir");
            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    anadirDatos();
                    break;
                case 2:
                    mostrarLibro();
                    break;
                case 3:
                    String titulo;
                    titulo = sc.next();
                    buscarLibroTitulo();
                    break;
                case 4:
                    eliminarLibro();
                    break;
                default:
                    System.out.println("Vuelve a introducir una opcion");
                    opc = sc.nextInt();
                    break;
                case 0:
                    System.out.printf("Saliendo");
                    break;
            }

        } while (opc != 0);

    }

    private static void anadirDatos() {
        Libro libro = new Libro(String titulo, String autor, Integer anio);
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO, true));
            bw.write(libro.getTitulo() + ", " + libro.getAutor() + ", " + libro.getAnio()
                    + " " + libro.getEjemplares());
            bw.newLine();

        } catch (IOException e) {

            System.err.println("Error al cargar archivo");
        }
    }

    private static void mostrarLibro() {
        try {
            List<Libro> bibloteca = new ArrayList<Libro>();
            BufferedReader br = new BufferedReader(new FileReader(FICHERO));


            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(", ");

                Libro libro = new Libro(datos[0], datos[1], Integer.parseInt(datos[2]));

                bibloteca.add(libro);

            }

        } catch (IOException e) {
            System.err.println("Error al cargar archivo");
        }
    }

    private Libro buscarLibroTitulo() {
        Libro libro;
        boolean check;
        try {

            BufferedReader br = new BufferedReader(new FileReader(FICHERO));{
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(", ");


        }
    }catch e) {
        System.err.println("Error al cargar archivo");


    }
    return libro;
} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        private static void eliminarLibro()  {}
}