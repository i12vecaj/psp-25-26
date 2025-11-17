class cuentabanco {
    private double saldo;

    public cuentabanco(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    private void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 1750 + 250));
        } catch (InterruptedException e) {
            System.out.println("Error en sleep: " + e.getMessage());
        }
    }

    public synchronized double getSaldo() {
        sleep();
        return saldo;
    }

    public synchronized void setSaldo(double saldo) {
        sleep();
        this.saldo = saldo;
    }

    public synchronized void ingresarDinero(String nombreHilo, double cantidad) {
        if (cantidad > 0) {
            double saldoPrevio = this.saldo;
            sleep();
            this.saldo += cantidad;
            System.out.println(nombreHilo + "-> Saldo previo: " + saldoPrevio +
                    " -> Ingreso: " + cantidad +
                    " -> Saldo final: " + this.saldo);
        }
    }
}