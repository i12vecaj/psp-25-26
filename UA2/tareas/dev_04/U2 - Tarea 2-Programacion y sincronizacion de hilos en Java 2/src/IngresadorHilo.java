/**
 * FR2: Clase que extiende de Thread, para realizar ingresos a la CuentaCorriente.
 */
class IngresadorHilo extends Thread {

    private final CuentaCorriente cuenta;
    private final String nombreHilo;
    private final double cantidad;
    private final boolean esSincronizado;

    /**
     * Constructor para inicializar el hilo con la cuenta compartida,
     * el nombre, la cantidad a ingresar y el modo de prueba.
     * @param cuenta Objeto CuentaCorriente compartido.
     * @param nombre Nombre del hilo.
     * @param cantidad Cantidad de dinero a ingresar.
     * @param esSincronizado Indica si debe llamar al método sincronizado.
     */
    public IngresadorHilo(CuentaCorriente cuenta, String nombre, double cantidad, boolean esSincronizado) {
        // Llama al constructor de Thread para establecer el nombre.
        super(nombre);
        this.cuenta = cuenta;
        this.nombreHilo = nombre;
        this.cantidad = cantidad;
        this.esSincronizado = esSincronizado;
    }

    /**
     * FR2: Desde el método run() usará el método de la clase CuentaCorriente 
     * que permite añadir saldo a la cuenta, dependiendo del modo de prueba.
     */
    @Override
    public void run() {
        if (esSincronizado) {
            // Prueba 1 (FR3): Usa el método sincronizado.
            cuenta.ingresarSaldoSincronizado(cantidad, nombreHilo);
        } else {
            // Prueba 2 (FR4): Usa el método NO sincronizado.
            cuenta.ingresarSaldoNoSincronizado(cantidad, nombreHilo);
        }
    }
}

/*
 * =========================================================================
 * DOCUMENTACIÓN Y ANÁLISIS (FR6)
 * =========================================================================
 * * Funcionamiento del Programa:
 * * 1. Definición de Clases: Se define la clase CuentaCorriente que simula una cuenta bancaria con
 * un saldo. Se añaden métodos para simular latencia (Thread.sleep) de 0.25s a 2s en las
 * operaciones de lectura y escritura (`getSaldo`, `setSaldo`, `ingresarSaldo...`). Esta latencia
 * es crucial para forzar una condición de carrera.
 * * 2. Métodos de Ingreso: Se implementan dos versiones del método de ingreso:
 * - `ingresarSaldoSincronizado`: Marcado con la palabra clave `synchronized`. Esto garantiza
 * que solo un hilo pueda acceder y modificar el objeto CuentaCorriente a la vez, actuando
 * como un "candado".
 * - `ingresarSaldoNoSincronizado`: Sin la palabra clave `synchronized`. Esto permite que
 * múltiples hilos realicen las operaciones de lectura (saldo previo) y escritura (saldo final)
 * simultáneamente.
 * * 3. Hilo de Ingreso: La clase `IngresadorHilo` extiende `Thread` y encapsula la lógica para
 * ejecutar la operación de ingreso en la cuenta compartida.
 * * 4. Orquestación (Main): El método `main` ejecuta dos pruebas idénticas:
 * - Prueba Sincronizada (FR3): Crea 5 hilos que llaman a `ingresarSaldoSincronizado`.
 * - Prueba No Sincronizada (FR4): Crea 5 hilos que llaman a `ingresarSaldoNoSincronizado`.
 * En ambas pruebas, el hilo principal espera a que todos los hilos finalicen usando `join()` y
 * luego compara el saldo final con el saldo esperado.
 * * * Diferencias Observadas entre el Tercer (Sincronizado) y el Cuarto (No Sincronizado) Apartado (FR4):
 * * 1. Resultado del Saldo Final (Diferencia Fundamental):
 * - **Sincronizado (FR3):** El saldo final **siempre** será el correcto y coincidirá con el saldo esperado. La operación se ejecuta de manera atómica (como una sola unidad), asegurando que el resultado de un hilo esté completamente visible antes de que otro hilo comience.
 * - **No Sincronizado (FR4):** El saldo final será **incorrecto** la mayoría de las veces y será inferior al saldo esperado. El resultado es indeterminado y varía en cada ejecución.
 * * 2. Explicación del Fenómeno (Condición de Carrera):
 * - El problema en el apartado 4 (no sincronizado) se llama **Condición de Carrera** (Race Condition). Ocurre porque la operación de ingreso (`saldo = saldo + cantidad;`) no es atómica. Se descompone en varios pasos:
 * 1. **Leer** el saldo actual (`saldoPrevio = this.saldo`).
 * 2. **Calcular** el nuevo saldo (`saldoFinal = saldoPrevio + cantidad`).
 * 3. **Escribir** el nuevo saldo (`this.saldo = saldoFinal`).
 * - Con la simulación de `sleep()` (latencia), es muy probable que dos hilos lean el mismo `saldoPrevio` antes de que el primer hilo haya terminado de *escribir* su resultado. Por ejemplo, si el saldo es 500€ y dos hilos ingresan 100€:
 * - Hilo A **LEE** 500€. Duerme.
 * - Hilo B **LEE** 500€. Duerme.
 * - Hilo A **ESCRIBE** 600€ (500 + 100).
 * - Hilo B **ESCRIBE** 600€ (500 + 100, **basado en la lectura de 500€**).
 * - El resultado final debería ser 700€, pero es 600€, lo que resulta en una **pérdida de actualización** de 100€.
 * * 3. Solución (Monitor o Bloqueo Mutuo):
 * - El modificador `synchronized` (utilizado en FR3) implementa un **bloqueo mutuo** (monitor) en el objeto `CuentaCorriente`. Cuando un hilo entra en un método `synchronized` de un objeto, adquiere el bloqueo de ese objeto. Ningún otro hilo puede entrar a otro método sincronizado del mismo objeto hasta que el hilo original libere el bloqueo (al salir del método). Esto garantiza la integridad de los datos en entornos multihilo.
 * */