package Hawkins;

public class LaboratorioDeHawkins extends Thread {

	private Buffer b;

    public LaboratorioDeHawkins(Buffer b) {
        this.b = b;
    }

    public void run() {
        while(true) {
            int valor = b.consumir();
            System.out.println("El laboratorio de Hawkins ha eliminado con exito al demogorgon, demogorgons eliminados: "+valor);
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
	
}
