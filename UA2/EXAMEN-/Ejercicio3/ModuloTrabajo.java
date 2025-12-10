public class ModuloTrabajo implements Runnable{

    final String nombre;
    final int idHilo;
    final int prioridad;

    public ModuloTrabajo(String nombre, int prioridad) {
        this.nombre = nombre;
        this.idHilo = (int) Thread.currentThread().getId();
        this.prioridad = prioridad;
    }

    public void run(){
        System.out.println("Módulo "+ nombre + "iniciado con ID -> " + idHilo);
        for (int i = 1; i <= 5; i++){
            System.out.println("Módulo "+nombre+ " Iteración ->"+i);
            try{
                if ( i == 3){
                    Thread.yield();
                }
                Thread.sleep((long)(Math.random() * (800 - 300) + 300));
            } catch (InterruptedException e){
                System.out.println("Módulo "+ nombre + " interrumpido durante la ejecución");
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "MóduloTrabajo{" +
                "nombre='" + nombre + '\'' +
                ", id=" + idHilo +
                ", prioridad=" + prioridad +
                '}';
    }


}


