public class Main {

    public static void main(String[] args){
        // Instanciamos un objeto de la clase Cuenta corrienta y mostramos el saldo
        CuentaCorriente cuenta = new CuentaCorriente(2000);
        System.out.println("Saldo de la cuenta " + cuenta.getSaldo());

        //Creamos 3 hilos que compartiran la cuenta e ingresaran una cantidad de dinero
        Account cuenta1 = new Account(cuenta,100,"Edu");
        Account cuenta2 = new Account(cuenta,300,"Jose");
        Account cuenta3 = new Account(cuenta, 150, "Laura");

        //Ejecutamos los hilos
            cuenta1.start();
            cuenta2.start();
            cuenta3.start();
            
    }
}

/*
FR4: La diferencia entre que el metodo sea synchronized y que no lo sea es que cuando no lo es
es que cuando no esta sincronizado, los datos que manejan los hilos no suelen ser acertados, porque como
cada hilo se ejecuta en un orden aleatorio e incluso se pueden ejecutar a la vez, sucede que si un hilo ha
cambiado el dato y otro hilo se ha ejecutado antes, este ultimo ha cogido el dato sin la modificacion anterior
por lo que ya los resultados obtenidos no son correctos. Sin embargo cuando esta sincronizado si cogen el dato
cuando se ha modificado y entonces si se obtendran los resultados esperados.
 */