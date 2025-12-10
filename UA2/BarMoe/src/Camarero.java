import java.util.ArrayList;
import java.util.List;

public class Camarero {
    String nombre;
    List<VasoCerveza> vasoCervezas = new ArrayList<>();

    public Camarero(String nombre) {
        this.nombre = nombre;
        for (int i = 1; i <= 3; i++) {
            int tipo = (int) (Math.random() * 2);
            vasoCervezas.add(new VasoCerveza(tipo, i));
        }
    }

    public synchronized VasoCerveza servirCerveza() {
        while (vasoCervezas.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        int vasoElegido = (int)(Math.random() * vasoCervezas.size());
        VasoCerveza vaso = vasoCervezas.remove(vasoElegido);
        notifyAll();
        return vaso;
    }

    public synchronized void devolverCerveza(VasoCerveza vaso) {
        vasoCervezas.add(vaso);
        notifyAll();
    }

    public synchronized int contarVasos() {
        return vasoCervezas.size();
    }
}
