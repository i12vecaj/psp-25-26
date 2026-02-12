public class CasaInteligente {
    /*
     * Nombre: [Alejandro Córdoba Pérez]
     * Fecha: [29/10/2025]
     * Descripción: [Breve descripción del ejercicio]
     * FR implementados: [FR1, FR2...]
     */
    public static void main(String[] args) {
    LavarRopa lavarRopa= new LavarRopa("Lavar la ropa",2);
    Cocinar cocinar= new Cocinar("Cocinar",3);
    Limpiar limpiar= new Limpiar("Limpiar",2);

    Thread h1= new Thread(lavarRopa);
    Thread h2= new Thread(cocinar);
    Thread h3= new Thread(limpiar);

    h1.start();
    h2.start();
    h3.start();

    }


    public static class LavarRopa implements Runnable{
         private String nombre;
         private int iterador;

         public LavarRopa(String nombre, int iterador) {
             this.nombre = nombre;
             this.iterador = iterador;
         }

        @Override
        public void run() {
            for (int i = 0; i < iterador; i++)
            {
                System.out.println(nombre+" Comenzando tarea ");
                try
                {
                    Thread.sleep(4000);
                    System.out.println(nombre+" finalizada");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            System.out.println(nombre+" Tarea FINALIZADA");

        }
    }
    public static class Cocinar implements Runnable{
        private String nombre;
        private int iterador;

        public Cocinar(String nombre, int iterador) {
            this.nombre = nombre;
            this.iterador = iterador;
        }

        @Override
        public void run() {
            for (int i = 0; i < iterador; i++)
            {
                System.out.println(nombre+" Comenzando tarea ");
                try
                {
                    Thread.sleep(5000);
                    System.out.println(nombre+" finalizada");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            System.out.println(nombre+" Tarea FINALIZADA");

        }
    }
    public static class Limpiar implements Runnable{
        private String nombre;
        private int iterador;

        public Limpiar(String nombre, int iterador) {
            this.nombre = nombre;
            this.iterador = iterador;
        }

        @Override
        public void run() {
            for (int i = 0; i < iterador; i++)
            {
                System.out.println(nombre+" Comenzando tarea: ");
                try
                {
                    Thread.sleep(5000);
                    System.out.println(nombre+" finalizada");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            System.out.println(nombre+" Tarea FINALIZADA");

        }
    }

}
