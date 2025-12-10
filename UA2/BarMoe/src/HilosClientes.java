    public class HilosClientes extends Thread{
        private Camarero camarero;
        private String nombre;
        double litrosBebidos = 0;

        public HilosClientes(Camarero c,String  nombre) {
            camarero = c;
            this.nombre = nombre;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                VasoCerveza vaso = camarero.servirCerveza();

                System.out.println(nombre + " está bebiendo con el vaso " + vaso.id);
                int dormir = (int)(Math.random() * (2000 - 550 + 1)) + 550;

                camarero.devolverCerveza(vaso);
                System.out.println(nombre + " ha devuelto el vaso" + vaso.id);
                try {
                    Thread.sleep(dormir);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
       }
