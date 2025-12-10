public class main {
    public static void main(String[] args) throws InterruptedException {
        ModuloTrabajo modulo1 = new ModuloTrabajo("Módulo 1", 1);
        ModuloTrabajo modulo2 = new ModuloTrabajo("Módulo 2", 2);
        ModuloTrabajo modulo3 = new ModuloTrabajo("Módulo 3", 3);
        
        modulo1.setPriority(10);
        modulo2.setPriority(5);
        modulo3.setPriority(1);

        modulo1.start();
        modulo2.start();
        modulo3.start();

        Thread.sleep(1500);
        modulo3.interrupt();// tras 1.5 segundos interrumpo el modulo3

        modulo1.isAlive();
        modulo2.isAlive();
        modulo3.isAlive();

        

        modulo1.join();
        System.out.println("El " + modulo1.getNombre() + " Estado de vida: " + modulo1.isAlive());
        modulo2.join();
        System.out.println("El " + modulo2.getNombre()+ " Estado de vida: " + modulo2.isAlive());
        modulo3.join();
        System.out.println("El " + modulo3.getNombre() + " Estado de vida: " + modulo3.isAlive());

        modulo1.toString();
        modulo2.toString();
        modulo3.toString();
    }
}

// Debido a la prioridad asignada a cada hilo, lo más probable es que el "Módulo 1" (con prioridad 10) termine primero, seguido por el "Módulo 2" (con prioridad 5) y finalmente el "Módulo 3" (con prioridad 1).
// Gracias a la llamada a Thread.yield(), el hilo que se está ejecutando cede el control permitiendo que hilos de mayor prioridad se ejecuten, infliuyendo en el orden de finalización de los módulos.
//El metodo interrupt() interrumpe el hilo "Módulo 3", lo que provoca que lance una excepción InterruptedException si está en estado de espera o suspensión, permitiendo que el hilo maneje la interrupción adecuadamente.
//En cuando a la planificacion del S.O, tiene en cuenta la prioridad de la CPU para asignar tiempo de ejecución a los hilos, por lo que los hilos con mayor prioridad suelen recibir más tiempo de CPU en comparación con los hilos de menor prioridad.
