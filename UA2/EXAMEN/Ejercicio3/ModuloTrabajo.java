package Ejercicio3;

public class ModuloTrabajo extends Thread {
    private String nombre;
    private int id;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
        this.id = (int) this.getId();
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado. ID: " + id + ". Prioridad: " + getPriority());
        for (int i = 1; i <= 5; i++) {
            System.out.println("Módulo " + nombre + " iteración " + i); // Mostrar el progreso
            if (i == 3) {
                Thread.yield(); // Ceder el paso a otros hilos en la iteración 
            }
            try {
                Thread.sleep((long)(300 + Math.random() * 500)); // Simula trabajo con sleep aleatorio entre 300 y 800 ms
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución"); //prevenir interrupcion
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Módulo Trabajo: nombre=" + nombre + ", id=" + id + ", prioridad=" + getPriority(); // datos pedidos
    }
}

/*  Observaciones:
1. La prioridad de los hilos en Java es una sugerencia para el plan por si alguno se interrumpe.
2. El método yield permite que otros hilos de igual prioridad puedan ejecutarse pero no tiene porque ser en el mismo momento.
3. El interrupt es una forma efectiva de detener su ejecución, sobre todo si está dormido como es el caso.
4. La planificación del sistema operativo puede influir en el comportamiento de los hilos, ya que la JVM(Java Virtual Machine) 
depende del sistema operativo para la gestión de estos hilos.
*/
