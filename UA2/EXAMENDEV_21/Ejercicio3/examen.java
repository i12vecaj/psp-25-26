/*-----------------------------------------------------------
Ejercicio de Examen: Programacion Multihilo en Java
-----------------------------------------------------------
La empresa TechFactory quiere simular el comportamiento de varios modulos de produccion que trabajan simultaneamente. Cada modulo es un hilo que realiza tareas simples, pero deben coordinarse y mostrar informacion relevante de su ejecucion.
-----------------------------------------------------------
FR1 (2 puntos) – Crear la clase ModuloTrabajo
-----------------------------------------------------------
Crea una clase llamada ModuloTrabajo que extienda de Thread.
Debe incluir:
Un nombre del modulo (recibido por el constructor).
Un metodo run() que:
Muestre el mensaje:
"Modulo <nombre> iniciado. ID: <id del hilo>"
Realice un bucle de 5 iteraciones donde:
Imprima "Modulo <nombre> – iteracion X"
Haga un sleep() entre 300 y 800 ms (aleatorio).
En la tercera iteracion llame a yield().
-----------------------------------------------------------
FR2 (2 puntos) – Prioridades y arranque de hilos
-----------------------------------------------------------
En el main:
Crea tres objetos de tipo ModuloTrabajo.
Asigna prioridades distintas con setPriority():
Modulo A → prioridad alta
Modulo B → prioridad media
Modulo C → prioridad baja
Arranca los hilos usando start().
-----------------------------------------------------------
FR3 (2 puntos) – Comprobacion del estado de los hilos
-----------------------------------------------------------
Tras iniciar los hilos, en main:
Muestra por pantalla si cada hilo esta vivo con isAlive().
Espera a que todos terminen con join().
Cuando cada hilo finalice, muestra:
"Modulo <nombre> finalizado. Estado vivo: <isAlive()>".
-----------------------------------------------------------
FR4 (2 puntos) – Interrupcion controlada
-----------------------------------------------------------
Modifica el main para:
Interrumpir el Modulo B despues de 1.5 segundos.
En el run(), controla la interrupcion:
Si el hilo es interrumpido durante el sleep(), captura InterruptedException y muestra:
"Modulo <nombre> interrumpido durante la ejecucion"
Finaliza el hilo inmediatamente tras la interrupcion.
-----------------------------------------------------------
FR5 (2 puntos) – Informacion final y metodo toString()
-----------------------------------------------------------
Implementa en ModuloTrabajo el metodo toString() para que devuelva:
"ModuloTrabajo{nombre='X', id=ID_DEL_HILO, prioridad=PRIORIDAD}"
En el main, al finalizar todo el proceso:
Muestra toString() de los tres modulos.
Explica brevemente (4–5 lineas, dentro del codigo como comentario) que has observado respecto a:
priorizacion de hilos,
yield(),
interrupcion,
planificacion del sistema operativo.*/

import java.util.concurrent.ThreadLocalRandom;

public class examen {
	public static void main(String[] args) {
		ModuloTrabajo moduloA = new ModuloTrabajo("A");
		ModuloTrabajo moduloB = new ModuloTrabajo("B");
		ModuloTrabajo moduloC = new ModuloTrabajo("C");

		moduloA.setPriority(Thread.MAX_PRIORITY);
		moduloB.setPriority(Thread.NORM_PRIORITY);
		moduloC.setPriority(Thread.MIN_PRIORITY);

		moduloA.start();
		moduloB.start();
		moduloC.start();

		System.out.println("Modulo A vivo tras start: " + moduloA.isAlive());
		System.out.println("Modulo B vivo tras start: " + moduloB.isAlive());
		System.out.println("Modulo C vivo tras start: " + moduloC.isAlive());

		try {
			Thread.sleep(1500);
			moduloB.interrupt();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		try {
			moduloA.join();
			System.out.println("Modulo A finalizado. Estado vivo: " + moduloA.isAlive());
			moduloB.join();
			System.out.println("Modulo B finalizado. Estado vivo: " + moduloB.isAlive());
			moduloC.join();
			System.out.println("Modulo C finalizado. Estado vivo: " + moduloC.isAlive());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		System.out.println(moduloA.toString());
		System.out.println(moduloB.toString());
		System.out.println(moduloC.toString());
	}
}

class ModuloTrabajo extends Thread {
	private final String nombreModulo;

	public ModuloTrabajo(String nombre) {
		this.nombreModulo = nombre;
	}

	@Override
	public void run() {
		System.out.println("Modulo " + nombreModulo + " iniciado. ID: " + getId());
		for (int i = 1; i <= 5; i++) {
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Modulo " + nombreModulo + " interrumpido durante la ejecucion");
				return;
			}
			System.out.println("Modulo " + nombreModulo + " - iteracion " + i);
			if (i == 3) {
				Thread.yield();
			}
			try {
				int espera = ThreadLocalRandom.current().nextInt(300, 801);
				Thread.sleep(espera);
			} catch (InterruptedException e) {
				System.out.println("Modulo " + nombreModulo + " interrumpido durante la ejecucion");
				return;
			}
		}
	}

	@Override
	public String toString() {
		return "ModuloTrabajo{nombre='" + nombreModulo + "', id=" + getId() + ", prioridad=" + getPriority() + "}";
	}
}

/*
La prioridad alta suele ejecutarse antes, pero depende del sistema operativo.
yield() no garantiza orden, solo sugiere ceder la CPU.
La interrupcion detiene el hilo en sleep() y permite finalizarlo antes.
La planificacion real depende del sistema operativo y puede variar en cada ejecucion.
Los hilos pueden terminar en distinto orden aunque tengan prioridades distintas. */
