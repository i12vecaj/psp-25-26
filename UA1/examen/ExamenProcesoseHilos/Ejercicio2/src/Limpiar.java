public class Limpiar implements Runnable {


    String tarea;
    int numHabitaciones; //numero de veces a usar
    long dormir = 3000; //simula 3 seg entre tarea


    public Limpiar(int numHabitaciones) {
        this.tarea = "Limpiar";
        this.numHabitaciones = numHabitaciones;
    }

    @Override
    public void run() {
        System.out.println(tarea+" iniciando...");
        for (int i = 0; i < numHabitaciones; i++) {               
            
        try {
            System.out.println("Empezando habitación número "+i+1);
            Thread.sleep(dormir);
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
}
    System.out.println(tarea+" finalizada");
    }
}
