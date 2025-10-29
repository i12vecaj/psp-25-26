
public class LavarRopa implements Runnable{

    String tarea;
    int numLavadoras; //numero de veces a usar
    long dormir = 1000; //simula 1 seg entre tarea


    public LavarRopa(int numLavadoras) {
        this.tarea = "LavarRopa";
        this.numLavadoras = numLavadoras;
    }

    @Override
    public void run() {
        System.out.println(tarea+" iniciando...");
        for (int i = 0; i < numLavadoras; i++) {               
            
        try {
            System.out.println("Empezando lavadora número "+i+1);
            Thread.sleep(dormir);
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
}
    System.out.println(tarea+" finalizada");
    }

}
