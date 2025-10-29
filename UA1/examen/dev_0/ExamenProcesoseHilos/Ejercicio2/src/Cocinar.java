public class Cocinar implements Runnable {

    String tarea;
    int numPlatos; //numero de veces a usar
    long dormir = 2000; //simula 2 seg entre tarea


    public Cocinar(int numPlatos) {
        this.tarea = "Cocinar";
        this.numPlatos = numPlatos;
    }

    @Override
    public void run() {
        System.out.println(tarea+" iniciando...");
        for (int i = 0; i < numPlatos; i++) {               
            
        try {
            System.out.println("Empezando plato número "+i+1);
            Thread.sleep(dormir);
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
}
    System.out.println(tarea+" finalizada");
    }
}
