/**
 * -----------------------------------------------------------
 * Ejercicio de Examen: Programación Multihilo en Java
 * -----------------------------------------------------------
 * La empresa TechFactory quiere simular el comportamiento de varios módulos de producción que trabajan simultáneamente. Cada módulo es un hilo que realiza tareas simples, pero deben coordinarse y mostrar información relevante de su ejecución.
 * -----------------------------------------------------------
 * FR1 (2 puntos) – Crear la clase MóduloTrabajo
 * -----------------------------------------------------------
 * Crea una clase llamada ModuloTrabajo que extienda de Thread.
 * Debe incluir:
 * - Un nombre del módulo (recibido por el constructor).
 * - Un método run() que:
 * Muestre el mensaje:
 * "Módulo <nombre> iniciado. ID: <id del hilo>"
 * Realice un bucle de 5 iteraciones donde:
 * - Imprima "Módulo <nombre> – iteración X"
 * - Haga un sleep() entre 300 y 800 ms (aleatorio).
 * - En la tercera iteración llame a yield().
 * -----------------------------------------------------------
 * FR2 (2 puntos) – Prioridades y arranque de hilos
 * -----------------------------------------------------------
 * En el main:
 * Crea tres objetos de tipo ModuloTrabajo.
 * Asigna prioridades distintas con setPriority():
 * - Módulo A → prioridad alta
 * - Módulo B → prioridad media
 * - Módulo C → prioridad baja
 * Arranca los hilos usando start().
 * -----------------------------------------------------------
 * FR3 (2 puntos) – Comprobación del estado de los hilos
 * -----------------------------------------------------------
 * Tras iniciar los hilos, en main:
 * - Muestra por pantalla si cada hilo está vivo con isAlive().
 * - Espera a que todos terminen con join().
 * Cuando cada hilo finalice, muestra:
 * "Módulo <nombre> finalizado. Estado vivo: <isAlive()>".
 * -----------------------------------------------------------
 * FR4 (2 puntos) – Interrupción controlada
 * -----------------------------------------------------------
 * Modifica el main para:
 * - Interrumpir el Módulo B después de 1.5 segundos.
 * - En el run(), controla la interrupción:
 * Si el hilo es interrumpido durante el sleep(), captura InterruptedException y muestra:
 * "Módulo <nombre> interrumpido durante la ejecución"
 * - Finaliza el hilo inmediatamente tras la interrupción.
 * -----------------------------------------------------------
 * FR5 (2 puntos) – Información final y método toString()
 * -----------------------------------------------------------
 * Implementa en ModuloTrabajo el método toString() para que devuelva:
 * "MóduloTrabajo{nombre='X', id=ID_DEL_HILO, prioridad=PRIORIDAD}"
 * En el main, al finalizar todo el proceso:
 * Muestra toString() de los tres módulos.
 * Explica brevemente (4–5 líneas, dentro del código como comentario) qué has observado respecto a:
 * - priorización de hilos,
 * - yield(),
 * - interrupción,
 * - planificación del sistema operativo.
 **/

/**
 * Autor David Alberto Cruz Barranco
 * Fecha 10/12/2025
 * **/

public class Ejercicio3 {
    public static void main(String[] args) {

        // FR2: Crear módulos y asignar prioridades
        ModuloTrabajo moduloA = new ModuloTrabajo("A");
        ModuloTrabajo moduloB = new ModuloTrabajo("B");
        ModuloTrabajo moduloC = new ModuloTrabajo("C");

        moduloA.setPriority(Thread.MAX_PRIORITY);     // alta
        moduloB.setPriority(Thread.NORM_PRIORITY);    // media
        moduloC.setPriority(Thread.MIN_PRIORITY);     // baja

        // Arrancar hilos
        moduloA.start();
        moduloB.start();
        moduloC.start();

        // FR3: Comprobación de estado vivo
        System.out.println("¿Módulo A vivo? " + moduloA.isAlive());
        System.out.println("¿Módulo B vivo? " + moduloB.isAlive());
        System.out.println("¿Módulo C vivo? " + moduloC.isAlive());

        // FR4: Interrumpir módulo B después de 1.5 segundos
        try {
            Thread.sleep(1500);
            moduloB.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Esperar finalización
        try {
            moduloA.join();
            System.out.println("Módulo A finalizado. Estado vivo: " + moduloA.isAlive());

            moduloB.join();
            System.out.println("Módulo B finalizado. Estado vivo: " + moduloB.isAlive());

            moduloC.join();
            System.out.println("Módulo C finalizado. Estado vivo: " + moduloC.isAlive());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // FR5: Mostrar información final
        System.out.println(moduloA);
        System.out.println(moduloB);
        System.out.println(moduloC);

        /*
         Observaciones:
         - La priorización de hilos no garantiza el orden exacto, pero el hilo A suele ejecutarse más seguido.
         - El yield() provoca que el hilo ceda voluntariamente la CPU, pero no siempre vemos que funcione.
         - La interrupción detiene el módulo B cuando está durmiendo, mostrando control al interrumpirlo.
         - La planificación real depende del sistema operativo, por lo que los resultados pueden variar en cada ejecución.
        */
    }
}


// ============================================================
// FR1 – Clase ModuloTrabajo
// ============================================================
class ModuloTrabajo extends Thread {
    private final String nombre;

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {

        System.out.println("Módulo " + nombre + " iniciado. ID: " + this.getId());

        for (int i = 1; i <= 5; i++) {

            System.out.println("Módulo " + nombre + " – iteración " + i);

            // En la tercera iteración, yield()
            if (i == 3) {
                Thread.yield();
            }

            try {
                // Sleep aleatorio entre 300 y 800 ms
                int tiempo = (int) (300 + Math.random() * 500);
                Thread.sleep(tiempo);

            } catch (InterruptedException e) {
                // FR4: Interrupción controlada
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
                return; // Finalizamos inmediatamente
            }
        }
    }

    // FR5: toString()
    @Override
    public String toString() {
        return "ModuloTrabajo{nombre='" + nombre + "', id=" + this.getId()
                + ", prioridad=" + this.getPriority() + "}";
    }
}