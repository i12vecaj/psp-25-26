
//Mi clase HilosClientes ahora implementa Runnable
class HilosClientes implements Runnable {
    private Camarero camarero;
    private double litrosBebidos = 0;
    private String nombre;

    public HilosClientes(String nombre, Camarero camarero) {
        this.nombre = nombre;
        this.camarero = camarero;
    }

    @Override
    public void run() {

        while (true) {
            try {
                VasoCerveza vaso = camarero.servirCerveza(nombre);

                double litros = (vaso.getTipo() == 0 ? 0.284 : 0.568);
                litrosBebidos += litros;

                System.out.println(nombre + " bebe " + litros + " L  | Total: " + litrosBebidos + " L");

                camarero.devolverCerveza(vaso, nombre);

                int min = 250;
                int max = 1000;

                int espera = min + (int)(Math.random() * (max - min));
                Thread.sleep(espera);

            } catch (InterruptedException e) {
                System.out.println(nombre + " ha sido interrumpido.");
                break;
            }
        }
    }
}