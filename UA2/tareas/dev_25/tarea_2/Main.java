
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //creo cuentacorreinte para que todos los hilos tengana cceso a la cuenta
        CuentaCorriente account = new CuentaCorriente(5000);

        //creo los hilos que extienden
        myAccount hilo1 = new myAccount("1",100,account);
        myAccount hilo2 = new myAccount("2",400,account);
        myAccount hilo3 = new myAccount("3",600,account);

        Thread t1 = new Thread(hilo1);
        Thread t2 = new Thread(hilo2);
        Thread t3 = new Thread(hilo3);

        t1.start();
        t2.start();
        t3.start();


    }


}