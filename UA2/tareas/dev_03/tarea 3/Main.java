package tarea3hilos;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LectorFR2 h1 = new LectorFR2("fichero1.txt");
		LectorFR2 h2 = new LectorFR2("fichero2.txt");
		
		h1.start();
		h2.start();
		
		
	}

}
