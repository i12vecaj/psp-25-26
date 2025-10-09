import java.io.IOException;

public class Lanzador {
    public static void main(String[] args) {
        String argumento = "-5"; // Se puede modificar el argumento para probar

        int resultado = ejecutarValidador(argumento);

        switch (resultado) {
            case 1:
                System.out.println("Error: No se ha proporcionado ningún argumento");
                break;
            case 2:
                System.out.println("Error: El argumento no es un número entero");
                break;
            case 3:
                System.out.println("Error: El número proporcionado es negativo");
                break;
            case 0:
                System.out.println("El argumento es correcto");
                break;
            default:
                System.out.println("Error inesperado. Código: " + resultado);
                break;
        }

    }

    public static int ejecutarValidador(String argumento) {
        try {
            String classpath = System.getProperty("java.class.path"); // Para el classpath actual

            ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, "ValidadorArgumentos", argumento);
            pb.inheritIO(); // Permite que el proceso hijo use la misma entrada/salida

            Process proceso = pb.start();
            proceso.waitFor(); //Espera a que termine

            return proceso.exitValue(); // Devuelve el código de salida
        } catch (IOException | InterruptedException e) {
            System.err.println("Ha ocurrido un error al ejecutar el proceso: " + e.getMessage());

            return -1; // Código de error inesperado
        }
    }
}

