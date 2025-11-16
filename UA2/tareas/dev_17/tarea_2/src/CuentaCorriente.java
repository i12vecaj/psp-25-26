import java.util.Random;
public class CuentaCorriente {




    // Atributo: saldo de la cuenta
    private double saldo;
    // Generador de números aleatorios para el retardo
    private Random random = new Random();
    // Constructor: asigna un saldo inicial
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }
    // Getter del saldo (añadir sleep aleatorio)
    public double getSaldo() {
        // TODO: Añadir un sleep aleatorio entre 250 y 2000 ms



        int numeroAleatorio = random.nextInt(250,2000); // Genero un número aleatorio



        try {
            Thread.sleep(numeroAleatorio); // Lo englobo en un try cath ya que si no no es posible usarlo directamente a no ser que extienda de InterruptedException
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        return saldo;
    }
    // Setter del saldo (añadir sleep aleatorio)
    public void setSaldo(double nuevoSaldo) {
        // TODO: Añadir un sleep aleatorio entre 250 y 2000 ms
        int numeroAleatorio = random.nextInt(250, 2000);
        try {
            Thread.sleep(numeroAleatorio); // Lo englobo en un try cath ya que si no no es posible usarlo directamente a no ser que extienda de InterruptedException
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.saldo = nuevoSaldo;



    }
    // Método synchronized para ingresar una cantidad
    public synchronized void ingresar(String nombreHilo, double cantidad) {
        int numeroAleatorio = random.nextInt(250, 2000);
        try {
            Thread.sleep(numeroAleatorio);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // TODO:
        // 1. Mostrar por pantalla quién realiza el ingreso y el saldo previo.
        System.out.println("Nombre: " +  nombreHilo);
        System.out.println("Saldo previo = " + saldo);


        // 2. Sumar la cantidad al saldo.
        saldo = saldo + cantidad;

        // 3. Mostrar el nuevo saldo final.
        System.out.println("El saldo final es " + saldo);




    }
}




