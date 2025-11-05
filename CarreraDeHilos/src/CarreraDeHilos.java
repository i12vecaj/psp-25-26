/*
Ejecuta el programa y observa el orden de ejecución de los hilos.
¿Siempre es el mismo? ¿Influye la prioridad?
No, la prioridad influye en el orden de ejecucción

Explica la diferencia entre start() y run().
La diferencia es que cuando se usa start() hay que esperar hasta que el hilo termine de ejecutarse hasta que pueda ejecutarse
el siguiente, en cambio, cuando usas start() las instrucciones de los hilos se van intercalando, es decir se ejecutan simultáneamente,
pero si que hay que esperar a que el programa llegue a la línea donde se ejecuta.

¿Qué pasa si en lugar de start() llamas directamente a run()?
Se ejecutará el hilo pero habrá que esperar a que termine para que se pueda ejecutar el siguiente en programación secuencial

Experimenta con setPriority():
Prueba a darle al Hilo-1 prioridad máxima y al Hilo-3 mínima.
¿Qué cambia?
La diferencia es que ahora el Hilo-1 termina el primero y el Hilo-3 el último debido a que al
cambiar la prioridad el Hilo-1 es el primero en ejecutarse, y por lo tanto en acabar.


Comenta la línea donde se llama a interrupt() y vuelve a ejecutar.
¿Qué ocurre ahora con el hilo 2?
Ahora el hilo 2 no se interrumpe y por lo tanto sigue el flujo hasta finalizarse con el resto.

Añade una línea al final del main que muestre el estado final de los hilos con isAlive().

¿Cuándo pasa a false?
Cuando el hilo no está en ejecucción
Prueba a imprimir h1.toString() en distintos momentos para ver cómo cambia su estado.
 */
public class CarreraDeHilos {
    public static void main(String[] args) {
        // Creamos tres hilos con distintas prioridades
        MiHilo h1 = new MiHilo("Hilo-1");
        MiHilo h2 = new MiHilo("Hilo-2");
        MiHilo h3 = new MiHilo("Hilo-3");

        System.out.println(h1.toString());

        h1.setPriority(Thread.MAX_PRIORITY);   // Prioridad más baja
        h2.setPriority(Thread.NORM_PRIORITY);  // Prioridad normal
        h3.setPriority(Thread.MIN_PRIORITY);   // Prioridad más alta

        System.out.println("🔹 Estado inicial de los hilos:");
        System.out.println(h1.toString());
        System.out.println(h2.toString());
        System.out.println(h3.toString());

        // Iniciamos los hilos
        h1.start();
        System.out.println(h1.toString());
        h2.start();
        h3.start();

        // Interrumpimos el hilo 2 tras un pequeño retraso
        try {
            Thread.sleep(10);
//            h2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Comprobamos si los hilos siguen vivos
        System.out.println("\n🔹 ¿Siguen vivos?");
        System.out.println(h1.getName() + ": " + h1.isAlive());
        System.out.println(h2.getName() + ": " + h2.isAlive());
        System.out.println(h3.getName() + ": " + h3.isAlive());

        // Comprobamos si los hilos siguen vivos
        System.out.println("\n🔹 ¿Siguen vivos?");
        System.out.println(h1.getName() + ": " + h1.isAlive());
        System.out.println(h2.getName() + ": " + h2.isAlive());
        System.out.println(h3.getName() + ": " + h3.isAlive());

        System.out.println(h1.toString());

    }
}

class MiHilo extends Thread {
    public MiHilo(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        System.out.println("▶️ Iniciando " + getName() + " (ID: " + getId() + ", prioridad: " + getPriority() + ")");
        for (int i = 1; i <= 5; i++) {
            System.out.println(getName() + " -> " + i);
            try {
                // Simulamos un pequeño descanso y uso de yield()
                if (i == 3) {
                    System.out.println(getName() + " cede el turno (yield)");
                    Thread.yield();
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("❌ " + getName() + " fue interrumpido.");
                return;
            }
        }
        System.out.println("🏁 " + getName() + " ha terminado.");

    }
}