package Hawkins;

public class Eleven extends Thread {

	private Buffer b;

    public Eleven(Buffer b) {
        this.b = b;
    }
    
    public void run() {
        int i = 0;
        while(true) {
            b.producir(i);
            System.out.println("Un demogorgon ha sido detectado, demogorgons actuales: "+i);
            i++;
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            if(i >= 21) {
            	
            	return;
            	
            }
        }
    
}
}