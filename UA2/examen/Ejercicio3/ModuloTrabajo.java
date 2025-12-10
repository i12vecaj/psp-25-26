package Ex3;

import java.math.*;

public class ModuloTrabajo extends Thread {

	private String nombre;

	public ModuloTrabajo(String nombre) {
		super();
		this.nombre = nombre;
	}
	public void run() {
		
		System.out.println("El modulo <"+nombre+"> ha sido iniciado");
		int tiempoespera = (int)(Math.random()* 800);
		for(int i = 1; i < 6; i++) {
			
			System.out.println("modulo <"+nombre+"> iteracion: "+i);
			try {
				
				Thread.sleep(tiempoespera);
				
			}catch(InterruptedException e) {
				System.out.println("El modulo<"+nombre+"> ha sido interrumpido durante la ejecucion");
				e.printStackTrace();
				
			}
			if(i == 3) {
				
				Thread.yield();
				
			}
			
		}
		
	}
	public String getNombre() {
		return nombre;
	}
	@Override
	public String toString() {
		return "MóduloTrabajo{nombre="+nombre+", id="+threadId()+", prioridad="+Thread.NORM_PRIORITY+"}";
	}
}
