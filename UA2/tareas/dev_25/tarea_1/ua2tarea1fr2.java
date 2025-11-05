//DESTRIPANDO MI CÓDIGO

/*
*
A diferencia del anterior ejercicio, al estar sincronizados esperan a poder acceder a los recursos
* de esta manera controlamos el acceso (es por esto posiblmente que decíamos en clase que
* los procesos compiten entre ellos) para que cada hilo espera a que otro termine para así imprimir el contenido
*
* RESULTADO DE LA PRIMERA EJECUCIÓN REALIZADA
*
Hilo: 31
Contador: 1000
Hilo: 34
Contador: 2000
Hilo: 33
Contador: 3000
Hilo: 35
Contador: 4000
Hilo: 32
Contador: 5000

 */

public class ua2tarea1fr2 {
    static int cont=0;

    public static void main(String[] args) {
        Thread thread1=new Thread(new ua2tarea1fr2runnable());
        Thread thread2=new Thread(new ua2tarea1fr2runnable());
        Thread thread3=new Thread(new ua2tarea1fr2runnable());
        Thread thread4=new Thread(new ua2tarea1fr2runnable());
        Thread thread5=new Thread(new ua2tarea1fr2runnable());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }

}
