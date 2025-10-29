package ejercicio3;

public class casa {
    public static void main(String[] args) {
        

        hilolavar hilo1 = new hilolavar(1);
		hilococinar hilo2 = new hilococinar(2);
		hilolimpiar hilo3 = new hilolimpiar(3);


        Thread lavar = new Thread(hilo1);
		Thread cocinar = new Thread(hilo2);
		Thread limpiar = new Thread(hilo3);


        lavar.start();
		cocinar.start();
		limpiar.start();
    
}
  }

