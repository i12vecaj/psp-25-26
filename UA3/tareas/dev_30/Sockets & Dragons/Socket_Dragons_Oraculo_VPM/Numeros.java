import java.io.Serializable;

/**
 * Numeros
 *
 * Qué es:
 * - Objeto simple que viaja por el socket (por eso implementa Serializable).
 *
 * Qué lleva:
 * - El número que envía el cliente.
 * - El cuadrado y el cubo que calcula el servidor y devuelve.
 */
public class Numeros implements Serializable {
    private static final long serialVersionUID = 1L;

    // Datos que viajan por la red
    private int numero;
    private long cuadrado;
    private long cubo;

    // El cliente crea el objeto con el número
    public Numeros(int numero) {
        this.numero = numero;
    }

    // Getters y setters
    public int getNumero() {
        return numero;
    }

    public long getCuadrado() {
        return cuadrado;
    }

    public long getCubo() {
        return cubo;
    }

    public void setCuadrado(long cuadrado) {
        this.cuadrado = cuadrado;
    }

    public void setCubo(long cubo) {
        this.cubo = cubo;
    }

    @Override
    public String toString() {
        return "Numeros{" +
                "numero=" + numero +
                ", cuadrado=" + cuadrado +
                ", cubo=" + cubo +
                '}';
    }
}