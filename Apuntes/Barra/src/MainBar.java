public class MainBar {

    public static void main(String[] args) {

        // 1. CRÍTICO: Creamos UNA SOLA instancia de la barra
        Barra barraDeMoe = new Barra();

        // 2. Creamos los actores y les pasamos LA MISMA barra
        HiloCamarero moe = new HiloCamarero("Moe", barraDeMoe);

        HiloCliente homer = new HiloCliente("Homer", barraDeMoe);
        HiloCliente barney = new HiloCliente("Barney", barraDeMoe);

        // 3. Creamos los Hilos (Threads)
        Thread tMoe = new Thread(moe);
        Thread tHomer = new Thread(homer);
        Thread tBarney = new Thread(barney);

        // 4. ¡Acción!
        tMoe.start();
        tHomer.start();
        tBarney.start();

        // 5. Dejamos que el bar funcione durante 10 segundos
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 6. Cerramos el bar (Interrumpimos los hilos infinitos)
        System.out.println("\n--- ¡HORA DE CERRAR! ---");
        tMoe.interrupt();
        tHomer.interrupt();
        tBarney.interrupt();
    }
}