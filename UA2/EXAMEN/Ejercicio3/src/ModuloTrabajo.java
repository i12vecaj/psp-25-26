public class ModuloTrabajo extends Thread {
    private String nombre;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado.  ID: " + Thread.currentThread().getId());
        
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Módulo " + nombre + " – iteración " + i);
                
                if (i == 3) {
                    yield();
                }
                
                int tiempoEspera = 300 + (int)(Math.random() * 501);
                Thread.sleep(tiempoEspera);
            }
        } catch (InterruptedException e) {
            System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
            return;
        }
    }

    @Override
    public String toString() {
        return "MóduloTrabajo{nombre='" + nombre + "', id=" + getId() + ", prioridad=" + getPriority() + "}";
    }
}