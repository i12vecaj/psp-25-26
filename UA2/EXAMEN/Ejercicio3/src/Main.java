//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {

    public static void main(String[] args) {

        ModuloTrabajo A = new ModuloTrabajo("A");
        ModuloTrabajo B = new ModuloTrabajo("B");
        ModuloTrabajo C = new ModuloTrabajo("C");

        A.setPriority(Thread.MAX_PRIORITY);
        B.setPriority(Thread.NORM_PRIORITY);
        C.setPriority(Thread.MIN_PRIORITY);

        A.start();
        B.start();
        C.start();

        System.out.println("A vivo: " + A.isAlive());
        System.out.println("B vivo: " + B.isAlive());
        System.out.println("C vivo: " + C.isAlive());


        try {
            Thread.sleep(1500);
            B.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            A.join();
            System.out.println("A:" + A.isAlive());
            B.join();
            System.out.println("B:" + B.isAlive());
            C.join();
            System.out.println("C:" + C.isAlive());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(A.toString());
        System.out.println(B.toString());
        System.out.println(C.toString());

    }
}
        /*
        Pregunta Teórica:
        - La prioridad influye pero no garantiza el orden porque depende del  SO.
        - yield() puede ceder la CPU pero no obliga al sistema a cambiar de hilo.
        - La interrupción detiene el hilo en el siguiente sleep controlado.
        - Cada ejecución puede mostrar variación en el orden.
        */