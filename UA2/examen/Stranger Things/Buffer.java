public class Buffer {
    private String dato;
    private boolean cerrar_portal = false;

    public synchronized void producir(String valor){
        while(cerrar_portal){
            try {
                wait();
                System.out.println("Esperas por buffer lleno/vacío.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        dato = valor;
        cerrar_portal = true;
        System.out.println("Eleven genera un evento.");

        notify();
    }

    public synchronized void consumir(){
        while (!cerrar_portal) {
            try {
                wait();
                 System.out.println("Esperas por buffer lleno/vacío.");
            } catch (Exception e) {
               System.out.println(e.getMessage());
            }
        }
        cerrar_portal = false;
        System.out.println("El Laboratorio procesa un evento.");

        notify();
    }
}
