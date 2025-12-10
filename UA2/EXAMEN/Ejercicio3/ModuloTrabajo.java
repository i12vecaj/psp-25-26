public class ModuloTrabajo extends Thread {
    private String nombre;
    private int id;

    public ModuloTrabajo(String nombre, int id) {
        this.setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
        
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        
    }

    @Override
    public void run() {
        try {
            System.out.println("El módulo " + getNombre() + " ha iniciado.");
            for (int i = 1; i <= 5; i++) {
                System.out.println("El módulo " + getNombre() + ". Iteración " + (i+1));
                Thread.sleep((int)(Math.random() * 500) + 300);
                if(i ==2){
                    Thread.yield();
                }
            }

        } catch (InterruptedException e) {
            System.out.println("El módulo " + getNombre() + " fue interrumpido.");
        }
    }

    @Override
    public String toString() {
        return "ModuloTrabajo{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                "Prioridad=" + getPriority() +
                '}';
    }

}
