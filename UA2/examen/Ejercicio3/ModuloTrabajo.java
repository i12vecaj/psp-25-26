import java.util.Random;

public class ModuloTrabajo extends Thread{
    private String nombre;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public synchronized String getNombre() {
        return nombre;
    }

    public synchronized void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "MóduloTrabajo{nombre="+nombre+" ,id="+getId()+", prioridad="+getPriority();
    }

    @Override
    public void run(){
        int contador= 0;
        System.out.println("Módulo "+nombre+" iniciado. ID: "+getId());

        for (int i = 0; i<5;i++){
            contador++;
            if(contador== 3){
              Thread.yield();
            }else {
                System.out.println("Módulo" + nombre + " – iteración "+i);

                try {

                    Thread.sleep(500);
                }
                catch (InterruptedException ie) {
                    System.out.println("Módulo: "+nombre+" interrumpido durante la ejecución");
                }catch (Exception e) {
                    System.out.println("Error con el hilo: " + e.getMessage());
                }
            }
        }

    }


}
