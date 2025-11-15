package U2;
/* Aqui lo unico que hacemos es crear un Cuenta Corriente con los datos que queramos luego crear el metodo run el cual sera lo que ejecute el hilo
 que es el metodo ingresarSaldo y por ultimo crear los hilos y lanzarlos */
public class Main extends Thread {
    private CuentaCorriente cuenta;
    private String nombre;
    private double cantidad;

    public Main(CuentaCorriente cuenta, String nombre, double cantidad) {
        // Validación básica
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser null");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del hilo no es válido");
        }

        this.cuenta = cuenta;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        try {
            cuenta.ingresar(nombre, cantidad);
        } catch (Exception e) {
            System.err.println("Error en el hilo " + nombre + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        CuentaCorriente c1 = null;

        try {
            c1 = new CuentaCorriente(1000);
        } catch (Exception e) {
            System.err.println("Error al crear la cuenta: " + e.getMessage());
            return; // No seguir si la cuenta está mal creada
        }

        try {
            System.out.println("Saldo inicial: " + c1.getSaldo());
        } catch (Exception e) {
            System.err.println("Error obteniendo el saldo inicial: " + e.getMessage());
        }

        try {
            Thread t1 = new Main(c1, "Hilo 1", 100);
            Thread t2 = new Main(c1, "Hilo 2", 200);
            Thread t3 = new Main(c1, "Hilo 3", 300);

            t1.start();
            t2.start();
            t3.start();
        } catch (IllegalArgumentException e) {
            System.err.println("Error creando hilos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al iniciar los hilos: " + e.getMessage());
        }
    }
}

/* Diferencias:
    Con el Metodo synchronized lo que pasa esque solo un hilo puede acceder al saldo a la vez y que el saldo Final si es el esperado
    Mientras que sin el metodo synchronized lo que pasa esque varios hilos puede acceder al saldo a la vez y que el saldo Final no es el esperado
 */