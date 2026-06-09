package Parte5;

public class InspectorDatos {
    public static void main(String[] args) {
        // Cortamos la ejecución si no se detectan parámetros de entrada
        if (args.length == 0) {
            System.exit(1);
        }

        String inputConsola = args[0];

        try {
            // Evaluamos si el texto introducido corresponde a un formato numérico
            int digitoConvertido = Integer.parseInt(inputConsola);

            // Si el número es mayor o igual a cero, salimos de forma exitosa (código 0)
            if (digitoConvertido >= 0) {
                System.exit(0);
            } else {
                // Si es negativo, el código de respuesta del sistema será 3
                System.exit(3);
            }

        } catch (NumberFormatException exFormato) {
            // Si salta esta excepción significa que el parámetro contiene texto alfabético
            System.exit(2);
        }
    }
}