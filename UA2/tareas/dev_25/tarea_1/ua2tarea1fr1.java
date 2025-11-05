//DESTRIPANDO MI CÓDIGO

/*
*
* Tenemos el siguiente problema, los hilos se ejecutan sin seguir nuestro orden (concurrencia)
* y todos tienden a ejecutarse al mismo tiempo es por eso que, cuando intenta alguno leer,
* posiblmeente el valor ya se encontrase incrementado accediendo todos al mismo tiempo.
*
* Es por eso que podemos ver abajo que el ID es diferente y el contador se encuentra al máximo,
* cuando leemos el resultado ya está a 5000, para notar diferencia deberíamos dormir durante X
* segundos o utilizar funciones vistas en clase como join() que en teoría espera a otro proceso
* o sincronizar los procesos (cosa que necesito investigar)
*
* RESULTADO DE LA PRIMERA EJECUCIÓN REALIZADA
*
Hilo: 34
Hilo: 33
Hilo: 31
Hilo: 35
Hilo: 32
Contador: 5000
Contador: 5000
Contador: 5000
Contador: 5000
Contador: 5000
 */


public class ua2tarea1fr1 {

    static int cont=0;

    public static void main(String[] args) {

        Thread thread1=new ContThread();
        Thread thread2=new ContThread();
        Thread thread3=new ContThread();
        Thread thread4=new ContThread();
        Thread thread5=new ContThread();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

    }

    static class ContThread extends Thread{
        public void run(){
            cont=cont+1000;
            System.out.println("Hilo: "+getId());

            System.out.println("Contador: "+cont);
        }
    }
}

/*
Hilo: 34
Hilo: 33
Hilo: 31
Hilo: 35
Hilo: 32
Contador: 5000
Contador: 5000
Contador: 5000
Contador: 5000
Contador: 5000
 */
