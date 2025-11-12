import java.util.ArrayList;
import java.util.List;

public class Main {
    // Clase auxiliar para definir los datos de ingreso de cada hilo
    static class Ingreso {
        String nombre;
        double cantidad;

        Ingreso(String nombre, double cantidad) {
            this.nombre = nombre;
            this.cantidad = cantidad;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- INICIO TAREA 2: SINCRONIZACIÓN DE HILOS ---");
        System.out.println("\n--- ESCENARIO 1: CON SINCRONIZACIÓN ---");

        // Crear objeto de tipo CuentaCorriente con valor inicial
        double saldoInicial = 500.0;
        CuentaCorriente cuentaCompartida = new CuentaCorriente(saldoInicial);
        System.out.printf("Saldo Inicial de la cuenta: %.2f%n", cuentaCompartida.getSaldo());

        // Definición de hilos y sus ingresos
        List<Ingreso> ingresos = List.of(
                new Ingreso("Hilo Cliente A", 100.0),
                new Ingreso("Hilo Cliente B", 200.0),
                new Ingreso("Hilo Cliente C", 50.0),
                new Ingreso("Hilo Cliente D", 150.0),
                new Ingreso("Hilo Cliente E", 10.0)
        );

        List<Thread> hilos = new ArrayList<>();

        // Crear varios hilos que compartan dicho objeto
        for (Ingreso ingreso : ingresos) {
            Funcion hilo = new Funcion(cuentaCompartida, ingreso.nombre, ingreso.cantidad);
            hilos.add(hilo);
        }

        // Lanzar los hilos
        for (Thread hilo : hilos) {
            hilo.start();
        }

        // Esperar a que finalicen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                // Control de errores básico
                System.err.println("Error al esperar la finalización de los hilos.");
                Thread.currentThread().interrupt();
            }
        }

        // Visualizar el saldo final de la cuenta
        double totalIngresos = ingresos.stream().mapToDouble(i -> i.cantidad).sum();
        double saldoFinalEsperado = saldoInicial + totalIngresos;
        double saldoFinalObtenido = cuentaCompartida.getSaldo();

        System.out.println("\n--- RESULTADOS ESCENARIO 1 (SINCRONIZADO) ---");
        System.out.printf("Total Ingresado: %.2f%n", totalIngresos);
        System.out.printf("Saldo Final Esperado: %.2f%n", saldoFinalEsperado);
        System.out.printf("Saldo Final Obtenido: %.2f%n", saldoFinalObtenido);
        System.out.println("La diferencia es: " + (saldoFinalEsperado - saldoFinalObtenido));
        System.out.println("El resultado es CORRECTO (Diferencia = 0.00) gracias a 'synchronized' en el método ingresar.");


        // FR4: Análisis del Escenario 2 - Sin el modificador 'synchronized'.

        // PREGUNTA FR4: ¿Cuál es la diferencia al quitar 'synchronized' y por qué difiere el resultado?

        // DIFERENCIA:
        // Si se quita 'synchronized' del metodo 'ingresar' en CuentaCorriente.java, el saldo final sería
        // MENOR al esperado (1010.00) y el resultado sería INCORRECTO e IMPREDECIBLE en cada ejecución.
        // Esto contrasta con el Escenario 1, donde el saldo siempre es CORRECTO.

        // RAZÓN DE LA DIFERENCIA:
        // La operación de ingreso es Lectura-Modificación-Escritura.
        // Sin 'synchronized', dos o más hilos pueden llamar a 'getSaldo()' al mismo tiempo y leer el mismo valor antiguo.
        // Cuando ambos hilos calculan y luego intentan escribir su resultado ('setSaldo()'),
        // el ingreso del primer hilo en terminar se PIERDE, siendo sobrescrito por el segundo hilo.
        // El uso de Thread.sleep() en los métodos get/set aumenta la probabilidad de que esta condición ocurra.


    }
}