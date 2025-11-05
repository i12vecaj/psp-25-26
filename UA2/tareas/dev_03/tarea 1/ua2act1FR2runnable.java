package hastaratillo;
/*mientras hacia los ejercicios he investigado sobre  todo esto y por lo que he visto más lo que yo he entendido, se supone que el primer código
 * al estar lanzados los hilos así sin más deberian "pisarse" entre ellos y dar un resultado incorrecto, básicamente no debería dar 5000,
 * mientras que tanto este como el anterior, al usar synchronized para sincronizarlos no ocurre esto y si debería dar siempre 5000, el caso es
 * que a mí me da 5000 en los tres, no he descubierto el porque pasa esto, ya que como he dicho al menos en el primero no debería ser así.
 * El caso es que no me da tiempo a ver el porqué ya que tengo que entregar ya la tarea pero si me acuerdo un día de estos miraré porque el primero
 * me da 5000 y no el resultado que debería
 */
public class ua2act1FR2runnable {
	 static int contador = 0;

	    public static void main(String[] args) {

	        Runnable tarea = () -> {
	            synchronized (ua2act1FR2runnable.class) {
	                try {
	                    contador = contador + 1000;
	                } catch (Exception e) {
	                    System.err.println("Error en Runnable: " + e.getMessage());
	                }
	            }
	        };

	        Thread r1 = new Thread(tarea, "Runnable 1");
	        Thread r2 = new Thread(tarea, "Runnable 2");
	        Thread r3 = new Thread(tarea, "Runnable 3");
	        Thread r4 = new Thread(tarea, "Runnable 4");
	        Thread r5 = new Thread(tarea, "Runnable 5");

	        r1.start();
	        r2.start();
	        r3.start();
	        r4.start();
	        r5.start();

	        try {
	            r1.join();
	            r2.join();
	            r3.join();
	            r4.join();
	            r5.join();
	        } catch (InterruptedException e) {
	            System.err.println("Error al esperar los hilos (Runnable): " + e.getMessage());
	        }

	        System.out.println("Valor contador (Runnable sincronizado): " + contador);
}
}
