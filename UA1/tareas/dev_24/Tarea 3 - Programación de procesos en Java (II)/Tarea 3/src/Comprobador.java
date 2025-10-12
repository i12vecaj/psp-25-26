/**Autor: Antonio Rodríguez Cortés

                                //** Instrucciones/
 //**# Compilar ambos programas
javac Comprobador.java
javac Ejecutor.java

# Probar diferentes casos
java Ejecutor 5        # Salida: ✓ Todo correcto
java Ejecutor -3       # Salida: ✗ Error: número menor que 0
java Ejecutor hola     # Salida: ✗ Error: no es un número entero
java Ejecutor          # Salida: Uso correcto del programa*/

public class Comprobador {
    public static void main(String[] args) {
        try {
            // Si no hay argumentos
            if (args.length < 1) {
                System.exit(1);
            }

            // Intentamos convertir el argumento a entero
            int numero = Integer.parseInt(args[0]);

            // Si el número es menor que 0
            if (numero < 0) {
                System.exit(3);
            }

            // Si todo es correcto
            System.exit(0);

        } catch (NumberFormatException e) {
            // Si el argumento no es un número entero
            System.exit(2);
        }
    }
}
