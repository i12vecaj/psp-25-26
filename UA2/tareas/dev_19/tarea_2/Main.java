package Tarea2;


/*
Enunciado:
    FR4: Comprueba ahora los resultados quitando el modificador synchronized del metodo de la clase CuentaCorriente y
    del metodo que permite añadir saldo. ¿Cuál es la diferencia? ¿Por qué el resultado obtenido difiere respecto al apartado 3?

Respuesta:
    El synchronized es necesario cuando varios hilos acceden y modifican un recurso compartido, como el saldo de una cuenta.
    En el apartado 3, los hilos están sincronizados entre sí, lo que quita que dos hilos accedan al mismo tiempo al metodo que modifica el saldo.
    Esto hace que el saldo se actualice correctamente y sin problemas de otros hilos.

    Al quitar el synchronized en el apartado 4, los hilos pueden ejecutar el metodo de ingreso simultáneamente, provocando condiciones de carrera.
    Esto significa que varios hilos pueden leer el mismo saldo antes de que se actualice, y al escribir el nuevo saldo, uno puede sobrescribir el resultado del otro.
    Entonces el problema, el saldo final puede ser incorrecto o inconsistente, y variar en cada ejecución.

    la diferencia principal es que sin sincronización, el acceso concurrente no está controlado, lo que produce errores en el cálculo del saldo final.
*/

public class Main {
    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(300.00);

        System.out.println("Saldo inicial: "+cuenta.getSaldo());

        //Creo la lista de hilos
        Thread[] hilos = new Thread[5];
        //Creo las cantidades para que cada uno tenga una cantidad diferente
        double[] cantidades = {200, 150, 300, 100, 250};

        //Para cada hilo creo un objeto para añadir dinero
        for(int i = 0; i< hilos.length; i++){
            hilos[i] = new AñadirDinero(cuenta, "Hilo "+(i+1), cantidades[i]);
            //Aranco los hilos
            hilos[i].start();
        }

        //esperar a que terminen los hilos
        for (Thread hilo : hilos){
            try {
                hilo.join();
            } catch (Exception e) {
                System.out.println("Error al esperar al hilo: "+e.getMessage());
            }
        }

        //Obtengo el saldo final
        System.out.println("Saldo final: "+cuenta.getSaldo());
    }
}

