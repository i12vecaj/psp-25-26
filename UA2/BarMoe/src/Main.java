public class Main {
    public static void main(String[] args){
        Camarero camarero = new Camarero("Mou");
        HilosClientes cliente1 = new HilosClientes(camarero,"Homer");
        HilosClientes cliente2 = new HilosClientes(camarero,"Barney");
        HilosClientes cliente3 = new HilosClientes(camarero,"Carl");
        HilosClientes cliente4 = new HilosClientes(camarero,"Lenny");
        HilosClientes cliente5 = new HilosClientes(camarero,"Lurleen");

        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();
        cliente5.start();
    }
}
