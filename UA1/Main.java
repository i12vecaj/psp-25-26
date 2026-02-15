//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        LavarRopa lavarRopa = new LavarRopa();
        Cocinar cocinar = new Cocinar();
        Limpiar limpiar = new Limpiar();

        Thread lr = new Thread(lavarRopa);
        Thread cc = new Thread(cocinar);
        Thread ll = new Thread(limpiar);

        lr.start();
        cc.start();
        ll.start();
    }
}