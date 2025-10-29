public class Cocinar implements Runnable {
    private String nombre;
    

	public Cocinar(String nombre) {
		this.nombre = nombre;
	}


	public void run() {
					System.out.println(nombre + " Comenzando tarea...");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
        System.out.println(nombre + " Terminando tarea...");
		System.out.println("=========================");
    }

}
