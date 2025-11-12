import java.util.Random;

/**
Clase CuentaCorriente
      
Simula una cuenta corriente con un saldo. Permite consultar y modificar el saldo con un retardo aleatorio, e ingresar dinero de forma sincronizada.
*/

public class CuentaCorriente {
    private double saldo;       
    private Random random = new Random(); 

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    public void setSaldo(double nuevoSaldo) {
        dormirAleatorio();
        this.saldo = nuevoSaldo;
    }

    /**
     Método synchronized que ingresa dinero a la cuenta.
     
     Muestra por pantalla el saldo anterior, el ingreso y el saldo final.
     */
    public synchronized void ingresar(double cantidad, String nombrePersona) {
        try {
            System.out.println(nombrePersona + " va a ingresar " + cantidad + "€...");
            System.out.println("Saldo anterior: " + saldo + "€");

            dormirAleatorio(); 

            saldo += cantidad;

            System.out.println("Saldo final tras ingreso de " + nombrePersona + ": " + saldo + "€\n");

        } catch (Exception e) {
            System.err.println("Error al ingresar dinero: " + e.getMessage());
        }
    }

    /**
     * Método privado que duerme el hilo un tiempo aleatorio entre 250 y 2000 ms.
     */
    private void dormirAleatorio() {
        try {
            int tiempo = 250 + random.nextInt(1750); 
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El hilo fue interrumpido durante el sleep.");
        }
    }
}



/**
 Clase IngresoThread

 Representa un hilo que realiza un ingreso en una cuenta corriente compartida.
 */
public class IngresoThread extends Thread {
    private CuentaCorriente cuenta;
    private double cantidad;
    private String nombrePersona;

/**
 Constructor del hilo.
*/
    public IngresoThread(CuentaCorriente cuenta, double cantidad, String nombrePersona) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.nombrePersona = nombrePersona;
    }

/**
  Método run() que realiza el ingreso cuando el hilo se ejecuta.
*/
    @Override
    public void run() {
        cuenta.ingresar(cantidad, nombrePersona);
    }
}



/**
 Clase principal MainCuentaCorriente

Crea una cuenta corriente compartida entre varios hilos. Cada hilo realiza un ingreso sobre la misma cuenta.
*/
public class MainCuentaCorriente {
    public static void main(String[] args) {
        // Crear cuenta con saldo inicial
        CuentaCorriente cuenta = new CuentaCorriente(1000.0);
        System.out.println("Saldo inicial de la cuenta: " + cuenta.getSaldo() + "€\n");

        // Crear varios hilos que comparten la misma cuenta
        IngresoThread t1 = new IngresoThread(cuenta, 500, "Ana");
        IngresoThread t2 = new IngresoThread(cuenta, 300, "Luis");
        IngresoThread t3 = new IngresoThread(cuenta, 200, "Marta");
        IngresoThread t4 = new IngresoThread(cuenta, 150, "Carlos");

        // Iniciar los hilos
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Esperar a que todos los hilos terminen
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido: " + e.getMessage());
        }

        // Mostrar saldo final
        System.out.println("Saldo final de la cuenta: " + cuenta.getSaldo() + "€");
    }
}


/**
Con synchronized

·El método ingresar() está marcado como synchronized, lo que significa que solo un hilo puede acceder a él a la vez.
·Esto garantiza que el saldo no se modifique simultáneamente por varios hilos.
·Resultado final: el saldo es correcto (1000 + 500 + 300 + 200 + 150 = 2150 €).*/

/**
Sin synchronized

Si quitas el modificador synchronized del método ingresar():

·Varios hilos pueden modificar el saldo al mismo tiempo.
·Algunos ingresos se pierden.
·Resultado final: el saldo será incorrecto o inconsistente, menor del esperado.*/

/**
Control de errores

·Se maneja InterruptedException en el sleep().
·Se usa try-catch en el método ingresar() y en el join().
·Se evita la interrupción abrupta de los hilos.
*/
