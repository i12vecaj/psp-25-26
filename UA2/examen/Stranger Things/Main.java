package Hawkins;

public class Main {
    public static void main(String[] args) {
        Buffer b = new Buffer();

        Eleven e = new Eleven(b);
        LaboratorioDeHawkins lab = new LaboratorioDeHawkins(b);

        e.start();
        lab.start();
    }
}
