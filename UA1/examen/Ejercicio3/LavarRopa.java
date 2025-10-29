public class LavarRopa implements Runnable {
    private String nombre;
    

	public LavarRopa(String nombre) {
		this.nombre = nombre;
    }

	public void run() {
		System.out.println(nombre + " Comenzando tarea...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
        System.out.println(nombre + " Terminando tarea...");
		System.out.println("=========================");
    }

}