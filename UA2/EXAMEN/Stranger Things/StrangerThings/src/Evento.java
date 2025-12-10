import java.util.Random;

public class Evento {
    private final int id;
    private final String tipo;
    private final String eleven;
    private static int contador = 1;
    private static final String[] TIPOS_EVENTO = {
            "Derrotando Demogorgon", "Entrada a Portal", "Haciendo Poder"};

    public Evento(String eleven) {
        this.id = contador++;
        this.tipo = TIPOS_EVENTO[new Random().nextInt(TIPOS_EVENTO.length)];
        this.eleven = eleven;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEleven() {
        return eleven;
    }

    @Override
    public String toString() {
        return "Evento #" + id + " - " + tipo + " (por " + eleven + ")";
    }
}

