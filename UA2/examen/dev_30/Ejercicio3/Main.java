import java.util.List;

public class Main {
    public static void main(String[] args) {

        Thread moduloA = new Thread(new ModuloTrabajo("Modulo-A"));
        Thread moduloB = new Thread(new ModuloTrabajo("Modulo-B"));
        Thread moduloC = new Thread(new ModuloTrabajo("Modulo-C"));

        moduloA.setPriority(Thread.MAX_PRIORITY);
        moduloB.setPriority(Thread.NORM_PRIORITY);
        moduloC.setPriority(Thread.MIN_PRIORITY);

        moduloA.start();
        moduloB.start();
        moduloC.start();

        System.out.println("Modulo " + moduloA.getName() + " " + moduloA.isAlive());
        System.out.println("Modulo " + moduloB.getName() + " " + moduloB.isAlive());
        System.out.println("Modulo " + moduloC.getName() + " " + moduloC.isAlive());

        List<ModuloTrabajo> hilos = List.of(
                new ModuloTrabajo("Modulo-A"),
                new ModuloTrabajo("Modulo-B"),
                new ModuloTrabajo("Modulo-C")
        );

        for (ModuloTrabajo h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Modulo " + moduloA.getName() + " Estado Vivo:  " + moduloA.isAlive());
        System.out.println("Modulo " + moduloB.getName() + " Estado Vivo:  " + moduloB.isAlive());
        System.out.println("Modulo " + moduloC.getName() + " Estado Vivo:  " + moduloC.isAlive());

        //Interrumpir el Módulo B después de 1.5 segundos.
        // no e logrado saber como hacerlo

        System.out.println("ModuloTrabajo " + moduloA.toString());
        System.out.println("ModuloTrabajo " + moduloB.toString());
        System.out.println("ModuloTrabajo " + moduloC.toString());

//        Explica brevemente (4–5 líneas, dentro del código como comentario) qué has observado respecto a:
//
//        priorización de hilos
//          e obeservado que al establecer cada prioridad los tiempos de cada ejecucion,
        // correspondiente a su prioridad varian mas
//
//                yield(),
//                  cede la ejecucion al hilo de despues pero no siempre porque no asegura que se acabe cediendo
//
//                interrupción,
        //          pues como no e logrado hacer ese apartado no se exactamente pero doy por echo que ese hilo se detendria y solo segurian ejecutanose los demas
//
//                planificación del sistema operativo.
            //      se ve claramente que el SO le da mas importancia a unos hilos prioritarios por lo cual los que gozan de menos prioridad pueden tardar un poco mas
    }
}