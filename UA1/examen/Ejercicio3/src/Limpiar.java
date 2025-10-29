public class Limpiar implements Runnable{
    @Override
    public void run(){
        System.out.println("Comenzando la tarea de limpiar");

        for (int i = 0; i<1;i++) {

            try {
                long espera = 1000+(int)(Math.random()*1500);
                Thread.sleep(espera);
            } catch (InterruptedException e) {
                System.out.println("Error al interrumpir el proceso");
                e.printStackTrace();
            }
            System.out.println("Terminada la tarea de limpiar");

        }

    }
}
