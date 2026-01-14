/**
 * Clase principal para demostrar el acceso concurrente a una CuentaCorriente.
 * Implementa FR3 (Sincronizado) y provee la reflexión para FR4 (No
 * Sincronizado).
 */
public class Main {

    public static void main(String[] args) {

        // =======================================================
        // FR3: Prueba con sincronización (Resultado correcto esperado)
        // =======================================================

        double saldoInicial = 100.00;
        CuentaCorriente cuentaCompartida = new CuentaCorriente(saldoInicial);
        System.out.println("====================================================");
        System.out.printf("SALDO INICIAL DE LA CUENTA: %.2f€\n", cuentaCompartida.getSaldo());
        System.out.println("INICIANDO INVERSIONES CON SINCRONIZACIÓN (FR3)...");
        System.out.println("====================================================");

        // Datos de los hilos (Nombre, Cantidad)
        String[] nombres = { "Inversor A", "Caja B", "Freelance C", "Nómina D", "Cliente E" };
        double[] cantidades = { 500.00, 20.00, 150.50, 2000.00, 12.30 };
        double totalIngresos = 0.0;

        // Calcular el total esperado
        for (double cantidad : cantidades) {
            totalIngresos += cantidad;
        }

        Thread[] hilos = new Thread[nombres.length];

        // 1. Crear y lanzar los hilos
        for (int i = 0; i < nombres.length; i++) {
            hilos[i] = new HiloIngreso(cuentaCompartida, nombres[i], cantidades[i]);
            hilos[i].start();
        }

        // 2. Esperar a que todos los hilos terminen (Control de Errores Básico: join())
        for (int i = 0; i < hilos.length; i++) {
            try {
                // join() asegura que el hilo principal espere a la finalización de los hilos
                // hijos
                hilos[i].join();
            } catch (InterruptedException e) {
                System.err.println("Error: El hilo principal fue interrumpido mientras esperaba. " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        // 3. Visualizar el saldo final
        System.out.println("====================================================");
        double saldoFinal = cuentaCompartida.getSaldo();
        double saldoEsperado = saldoInicial + totalIngresos;

        System.out.printf("Total Ingresos Esperado: %.2f€\n", totalIngresos);
        System.out.printf("Saldo FINAL Esperado: %.2f€\n", saldoEsperado);
        System.out.printf("Saldo FINAL OBTENIDO (FR3): %.2f€\n", saldoFinal);

        if (saldoFinal == saldoEsperado) {
            System.out.println("RESULTADO FR3: ¡CORRECTO! La sincronización funcionó.");
        } else {
            System.out.println(
                    "RESULTADO FR3: ¡INCORRECTO! Error en la lógica (esto no debería pasar con 'synchronized').");
        }
        System.out.println("====================================================");

        // =======================================================
        // FR4: Documentación y Reflexión
        // =======================================================

        System.out.println("\n--- DOCUMENTACIÓN Y REFLEXIÓN (FR4) ---");
        System.out.println("1. Funcionamiento del programa:");
        System.out.println(
                "El programa simula el ingreso concurrente de dinero a una única CuentaCorriente compartida por varios hilos.");
        System.out.println("La clase CuentaCorriente tiene un método 'ingresarCantidad' que es la sección crítica.");
        System.out.println(
                "Se añaden sleeps aleatorios en los métodos get/set para simular latencia de red/disco, aumentando la probabilidad de fallos de concurrencia.");

        System.out.println("\n2. Comprobación de FR4 (Sin 'synchronized'):");
        System.out.println(
                "Para comprobar FR4, el usuario debe ir a 'CuentaCorriente.java' y COMENTAR la palabra 'synchronized' del método 'ingresarCantidad'.");

        System.out.println("\n3. Diferencia y Causa de la Discrepancia (FR4):");
        System.out.printf(
                "Cuando se QUITA el modificador 'synchronized', el Saldo FINAL OBTENIDO (FR4) será casi siempre MENOR que el Saldo Esperado (%.2f€).\n",
                saldoEsperado);
        System.out.println("Esto sucede por la **Condición de Carrera**.");
        System.out.println("La operación de ingreso no es atómica. Involucra 3 pasos:");
        System.out.println("  1. Leer (getSaldo)");
        System.out.println("  2. Modificar (saldoPrevio + cantidad)");
        System.out.println("  3. Escribir (setSaldo)");
        System.out.println(
                "Cuando el 'Inversor A' está en el paso 1 (Leer) y luego es dormido (sleep), el 'Cliente E' puede hacer lo mismo (Leer el mismo saldo antiguo).");
        System.out.println(
                "Ambos hilos hacen el cálculo sobre el mismo saldo antiguo, y el último en Escribir el nuevo valor sobrescribe la modificación del primero, lo que resulta en la pérdida de un ingreso (o más).");

        System.out.println("\n4. Conclusión:");
        System.out.println(
                "El modificador 'synchronized' (FR3) es esencial. Garantiza la 'exclusión mutua', asegurando que un hilo complete las tres fases (Leer, Modificar, Escribir) antes de que otro hilo pueda siquiera Leer el saldo, manteniendo la integridad del dato compartido.");
    }
}
