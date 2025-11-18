package tarea2procesos;

public class Main {

    public static void main(String[] args) {

        try {

            CuentaCorriente cuenta = new CuentaCorriente(1000, "CuentaPrincipal");

            System.out.println("Saldo inicial: " + cuenta.getSaldo());


            ejec h1 = new ejec("Hilo-1", cuenta, 300);
            ejec h2 = new ejec("Hilo-2", cuenta, 500);
            ejec h3 = new ejec("Hilo-3", cuenta, 150);

            h1.start();
            h2.start();
            h3.start();

            h1.join();
            h2.join();
            h3.join();

            System.out.println("Saldo final (con synchronized): " + cuenta.getSaldo());

        } catch(Exception e) {
            System.err.println("Error en main: " + e.getMessage());
        }
    }
}
//APARTADO FR4:
/*La principal diferencia al quitar el synchronized, es que los hilos pueden entrar a la vez, lo que crea errores a la hora de calcular,
 *basicamente esto quiere decir que la mayoria de las veces te va a dar un resultado que no toca, por ejemplo si el resultado fuese 5000 pues te va a dar
 *4995 o cosas así  */

