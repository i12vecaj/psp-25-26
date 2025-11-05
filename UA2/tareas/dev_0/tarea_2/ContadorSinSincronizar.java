public class ContadorSinSincronizar {

    private static int contador = 0;

    static class HiloContador extends Thread{
        private final String nombre ;

        HiloContador(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run(){
            try{
                System.out.println(nombre + "iniciando...");

                for(int i = 0; i < 1000; i++){
                    contador++;
                }
                System.out.println(nombre + " finalizado. Contador: "+contador);
            }catch (Exception e){
                System.err.println("Error en el hilo "+nombre+":"+e.getMessage());
            }
        }
    }

}

public static void main(String[] args) {
    System.out.println("<< PROGRAMA SIN SINCRONIZAR >>");
    System.out.println("Valor inicial del contador: "+ ContadorSinSincronizar.contador);

    Thread[] hilos = new Thread[5];

    try{
        for (int i =0;i < 5; i++){
            hilos[i] = new ContadorSinSincronizar.HiloContador("Hilo - "+ (i+1));
            hilos[i].start();
        }
        for (Thread hilo : hilos) {
            hilo.join();
        }

    } catch (InterruptedException e) {
        System.err.println("Error al esperar por los hilos: " + e.getMessage());
        Thread.currentThread().interrupt();
    }catch (Exception e) {
        System.err.println("Error inesperado: " + e.getMessage());
    }


    System.out.println("Valor final del contador: " + ContadorSinSincronizar.contador);
    System.out.println("Valor esperado: 5000");
}
