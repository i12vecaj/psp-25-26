

class Cuenta {
    private int saldo = 100;

    public void retirarDinero(String nombre, int cantidad) {
        if (saldo >= cantidad) {
            System.out.println(nombre + " va a retirar " + cantidad);
            saldo -= cantidad;
            System.out.println(nombre + " ha retirado. Saldo restante: " + saldo);
        } else {
            System.out.println(nombre + " no puede retirar, saldo insuficiente.");
        }
    }
}

class Cliente implements Runnable {
    private Cuenta cuenta;
    private String nombre;

    public Cliente(Cuenta cuenta, String nombre) {
        this.cuenta = cuenta;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            cuenta.retirarDinero(nombre, 50);
            try {
                Thread.sleep(100); // para simular tiempo de operación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BancoSinSincronizar {
    public static void main(String[] args) {
        Cuenta cuenta = new Cuenta();

        Thread t1 = new Thread(new Cliente(cuenta, "Cliente 1"));
        Thread t2 = new Thread(new Cliente(cuenta, "Cliente 2"));

        t1.start();
        t2.start();
    }
}