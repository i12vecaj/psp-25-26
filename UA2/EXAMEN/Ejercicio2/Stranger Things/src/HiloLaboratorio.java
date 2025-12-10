
public class HiloLaboratorio extends Thread {
    private final String nombre;
    private final Pueblo pueblo;
    private int eventosInvestigados = 0;

    public HiloLaboratorio(String nombre, Pueblo pueblo) {
        this.nombre = nombre;
        this.pueblo = pueblo;
    }

    @Override
    public void run() {
        System.out.println(nombre + " ha comenzado a investigar los eventos");
        System.out.println("==================================================\n");

        while (true) {
            try {
                // Simular tiempo de investigación de un evento
                int tiempoInvestigacion = new java.util.Random().nextInt(2000) + 1000;
                System.out.println(nombre + " investigando evento... (" + (tiempoInvestigacion/1000) + "s)");
                System.out.println("==================================================\n");
                Thread.sleep(tiempoInvestigacion);

                // Consumir el evento del pueblo
                Eventos evento = pueblo.consumirEvento(nombre);
                eventosInvestigados++;

                System.out.println( nombre + " lleva " + eventosInvestigados + " evento(s) investigados");
                System.out.println("==================================================\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
    }

}
