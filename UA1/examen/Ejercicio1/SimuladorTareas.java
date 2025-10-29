import java.util.Random;
/*
 * Nombre: Pablo Rodríguez Casado
 * Fecha: 29/10/2025
 * Descripción: En este ejercicio usamos hilos para ejecutar una simulación de varias tareas de forma concurrente
 * FR implementados: [FR1, FR2, FR3]
 */
public class SimuladorTareas{
    static void main(String[] args) {
        Thread t1 = new Thread(new Tarea("Matematicas"));
        Thread t2 = new Thread(new Tarea("Ciencia"));
        Thread t3 = new Thread(new Tarea("Historia"));
        t1.start();
        t2.start();
        t3.start();

    }
}

class Tarea implements Runnable{
    Random randomNumbers = new Random();
    private String nombreTarea;

    public Tarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    @Override
    public void run() {
        System.out.println("[Tarea: "+nombreTarea+"] ejecutandose en el hilo "+Thread.currentThread().getId());
        try {
            Thread.sleep(randomNumbers.nextInt(1000) + 500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[Tarea: "+nombreTarea+"] finalizada");

    }

}

/*
Qué diferencia hay entre crear un proceso y crear un hilo.
    Hay varias diferencias entre crear un proceso y un hilo,
        - Un hilo es mucho más rápido de crear que un proceso
        - Los hilos no requieren del nucleo de SO para poder funcionar
        - Se pueden crear varios hilos dentro de un solo proceso
        - Normalmente los hilos cooperan para resolver un problema

Qué ventajas e inconvenientes tiene la programación concurrente.
Ventajas;
    Algunas de las ventajas de la programación concurrente es que se aprovecha mucho mejor la CPU
    y hay un incremento de velocidad de ejecución gracias a que son capaces de ejecutar varios procesos de forma simultánea.
    También se solucionan muchos errores por el simple hecho de ser concurrente, como problemas con sistemas de control,
    simulaciones, Gestores de Bases de datos, etc.
Desventajas:
    La programación concurrente también tiene desventajas como la exclusion mutua, que es cuando dos procesos intentan acceder a la misma vez a un
    dato para modificarlo, al hacer esto el dato se bloquea o cuando un proceso necesita de otro para poder ejecutarse entonces en ese caso se necesitaría
    coordinar ambos procesos.
 */
