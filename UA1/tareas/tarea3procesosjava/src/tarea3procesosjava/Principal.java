package tarea3procesosjava;

public class Principal {
    public static void main(String[] args) {
        // Si no hay argumentos
        if (args.length < 1) {
            System.exit(1);
        }

        try {
            // Intentar convertir el primer argumento a entero
            int numero = Integer.parseInt(args[0]);

            // Si es menor que 0
            if (numero < 0) {
                System.exit(3);
            }

            // En cualquier otro caso (número válido y >= 0)
            System.exit(0);

        } catch (NumberFormatException e) {
            // Si no se puede convertir a número (es una cadena)
            System.exit(2);
        }
    }
}
