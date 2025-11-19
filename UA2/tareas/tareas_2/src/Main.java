//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        CuentaCorriente cuentaCorriente = new CuentaCorriente(1000);


        //Creacion de hilos
        Thread h1 = new Cuentas("Cliente1",  cuentaCorriente, 100);
        Thread h2 = new Cuentas("Cliente2",  cuentaCorriente, 200);
        Thread h3 = new Cuentas("Cliente3", cuentaCorriente, 300);
        Thread h4 = new Cuentas("Cliente4", cuentaCorriente, 400);

        //Ejecuación de hilos
        h1.start();
        h2.start();
        h3.start();
        h4.start();


        }
    }
