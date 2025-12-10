public class Main {
    public static void main(String[] args) {

        Camarero camarero = new Camarero("Mou");

        Thread c1 = new Thread(new HilosClientes("Homer", camarero));
        Thread c2 = new Thread(new HilosClientes("Barney", camarero));
        Thread c3 = new Thread(new HilosClientes("Carl", camarero));
        Thread c4 = new Thread(new HilosClientes("Lenny", camarero));
        Thread c5 = new Thread(new HilosClientes("Lurleen", camarero));

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
    }
}