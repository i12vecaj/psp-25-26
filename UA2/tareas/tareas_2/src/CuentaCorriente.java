import java.util.Random;
public class CuentaCorriente {

    // Atributo: saldo de la cuenta
    private double saldo;
    // Generador de números aleatorios para el retardo
    private Random random = new Random();
    // Constructor: asigna un saldo inicial

    private String nombreHilo;
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }
    // Getter del saldo (añadir sleep aleatorio)
    public double getSaldo() {
        parar();

        return saldo;
    }
    // Setter del saldo (añadir sleep aleatorio)
    public void setSaldo(double nuevoSaldo) {
        parar();

        this.saldo = nuevoSaldo;
    }
    // Método synchronized para ingresar una cantidad
    public synchronized void ingresar(double cantidad) {
        try{
            if(cantidad <= 0){
                System.out.println("Saldo insuficiente");
            }
            double antes = this.saldo;

            parar();

            this.saldo -= this.saldo +cantidad;

            System.out.println("Ingreso realizdo" + saldo);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Para crear una parada aleatoria entre 250 y 2000 milisegndos
    public void parar(){
        int parada = random.nextInt(250)+2000;
        try {
            Thread.sleep(parada);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        }
}