public class Main {
    public static void main(String[] args) {
        Buffer b = new Buffer();

        Eleven eleven = new Eleven(b);
        Laboratorio_Hawkins laboratorio_Hawkins = new Laboratorio_Hawkins(b);

        eleven.start();
        laboratorio_Hawkins.start();
    }
}
