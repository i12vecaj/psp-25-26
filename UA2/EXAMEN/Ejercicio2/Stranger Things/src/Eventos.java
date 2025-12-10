import java.util.Random;

public class Eventos {
    private final int id;
    private final String tipo;
    private final String productor ;
    private static int contador = 1;
    private static final String[] TIPOS_EVENTOS = {
        "Portal abierto", "Demogorgon", "Portal cerrado", "Mind Flayer", "Personaje desaparecido"
    };

    public Eventos(String productor) {
        this.id = contador++;
        this.tipo = TIPOS_EVENTOS[new Random().nextInt(TIPOS_EVENTOS.length)];
        this.productor = productor;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getProductor() {
        return productor;
    }

    @Override
    public String toString() {
        return "Evento #" + id + " - " + tipo + " (por " + productor + ")";
    }
    
}
