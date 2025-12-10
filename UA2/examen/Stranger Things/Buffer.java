package Hawkins;

public class Buffer {
    private int dato;
    private boolean demogorgon_detectado = false;

    public synchronized void producir(int valor) {
        while(demogorgon_detectado) {
            try { wait(); System.out.println("Esperas por buffer lleno/vacío."); } catch (InterruptedException e) {}
        }
        dato = valor;
        demogorgon_detectado = true;
        notify();
        if(valor == 20) {
        	
        	return;
        	
        }
    }

    public synchronized int consumir() {
        while(!demogorgon_detectado) {
            try { wait(); System.out.println("Esperas por buffer lleno/vacío."); } catch (InterruptedException e) {}
        }
        demogorgon_detectado = false;
        notify();
        return dato;
        
    }
}
