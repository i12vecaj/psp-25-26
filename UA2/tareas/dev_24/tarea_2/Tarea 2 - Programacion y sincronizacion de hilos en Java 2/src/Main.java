public class Main {
    public static void main(String[] args) {

        CuentaCorriente cuenta = new CuentaCorriente(1000.0);

        System.out.println("<<< SALDO INICIAL DE LA CUENTA >>>"+ cuenta.getSaldo()+ "€");

        //Inicializar los hilos
        HiloIngreso hilo1 = new HiloIngreso(cuenta, 500.0,"Juan Pérez");
        HiloIngreso hilo2 = new HiloIngreso(cuenta, 300.0,"María García");
        HiloIngreso hilo3 = new HiloIngreso(cuenta, 750.0,"Pedro López");
        HiloIngreso hilo4 = new HiloIngreso(cuenta, 200.0,"Ana Martínez");
        HiloIngreso hilo5 = new HiloIngreso(cuenta, 450.0,"Carlos Rodríguez");


        //Lanzar los hilos
        System.out.println("Lanzando hilos para realizar los ingresos...\n");
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        //Esperar a que los hilos terminen
        try{
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo5.join();
        }catch (InterruptedException e){
            System.err.println("Error esperando a los hilos: " + e.getMessage());
        }

        System.out.println("<<< SALDO FINAL DE LA CUENTA: "+ cuenta.getSaldo()+"€");

    }
}
