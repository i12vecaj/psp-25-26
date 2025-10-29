package examenprocesos;

public class lavarRopa extends Thread implements Runnable{

	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("["+getClass().getName()+"] Iniciando tarea...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("["+getClass().getName()+"] Tarea finalizada");
		
		
	}

}
