public class ModuloTrabajo extends Thread{
    private String modulo;

    public ModuloTrabajo(String modulo){
        this.modulo = modulo;
    }

    @Override
    public void run() {
        System.out.println("Modulo " + this.modulo + " iniciado. ID: " + this.getId());

        for (int i = 0; i < 5; i++) {
            if(i == 3){
                this.yield();
            }
            System.out.println("Modulo " + this.modulo + " Iteracion : " + (i+1));
            int dormir = (int)(Math.random() * (800 - 300 + 1)) + 300;
            try {
                Thread.sleep(dormir);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    @Override
    public String toString() {
        return "Nombre = " + this.getModulo() + " Id: " + this.getId() + " prioridad = "+ this.getPriority();
    }
}
