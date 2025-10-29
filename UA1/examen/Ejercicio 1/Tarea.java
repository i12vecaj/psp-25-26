package examenprocesos;
import java.math.*;

public class Tarea extends Thread implements Runnable {

	 /*
	 * Nombre: [Samuel Calero Ruiz]
	 * Fecha: [29/10/2025]
	 * Descripción: [Simulador de tareas]
	 * FR implementados: []
	 */
	String nombre;
	
	
	public Tarea(String nombre) {
		super();
		this.nombre = nombre;
	}


	public String getNombre() {
		return nombre;
	}

	int tiemposleep = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Este es el hilo "+Thread.currentThread());
		tiemposleep = (int) (Math.random() * 1500);
		System.out.println("["+this.getNombre()+"] Ejecutandose en el hilo "+Thread.activeCount());
		try {
			Thread.sleep(tiemposleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("["+this.getNombre()+"] Finalizada");
		System.out.println("Este es el hilo "+Thread.currentThread());
	}

}
/*Diferencia entre crear un hilo y un proceso:
 * a la hora de crear un proceso, que por ejemplo en java se podría hacer usando proccess builder, estás iniciando un programa que va a usar
 * recursos de tu equipo, y en caso de estar en una programación concurrente se va a intercalar con el resto de procesos, a su vez en algunos
 * casos los procesos van a colaborar entre ellos y en otros van a competir por los recursos del sistema.
 * Por otro lado, a la hora de crear un hilo, dentro de este pueden ocurrir varios procesos a la misma vez, pero dentro de un proceso solo puede
 * ejecutarse un hilo a la misma vez.
 * 
 * Ventajas programación concurrente: se aprovecha mejor la CPU y permite que los procesos se ejecuten mas rápido
 * Desventajas programación concurrente: pueden ocurrir errores como la exclusión mutua o la condición de sincronización 
 * 
 */


