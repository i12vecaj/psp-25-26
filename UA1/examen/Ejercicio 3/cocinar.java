package examenprocesos;

public class cocinar extends Thread implements Runnable{

	
	
	
	
	public void run() {
		// TODO Auto-generated method stub
	
		System.out.println("["+getClass().getName()+"] Iniciando tarea...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("["+getClass().getName()+"] Tarea finalizada");
	
	}
}
