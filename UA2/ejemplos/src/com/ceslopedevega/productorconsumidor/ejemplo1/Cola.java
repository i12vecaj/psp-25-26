public class Cola {
    private int numero;
    private boolean disponible = false;//inicialmente cola vacia

    public int get() {
	    if(disponible) {      //hay n�mero en la cola
		disponible = false; //se pone cola vac�a
            return numero;      //se devuelve
	    }
          return -1;	//no hay n�mero disponible, cola vac�a	
    }

    public void put(int valor) {
        numero = valor;    //coloca valor en la cola 
        disponible = true; //disponible para consumir, cola llena	
    }
}
