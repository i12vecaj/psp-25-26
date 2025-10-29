//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CasaInteligente {
    public static void main(String[] args) {



        /*
        a) Explica cómo se comportaría un sistema operativo al ejecutar dos procesos que acceden al mismo archivo de manera simultánea. Indica qué problemas podrían surgir y cómo podrían evitarse.
        (2 puntos)


        Al querer dos procesos acceder al mismo recurso el sistema operativo utilizaría un mecanismo de sincronización.
        En este caso el sistema operativo implementaría un mecanismo pensado para cuando dos o más procesos compitan por el mismo recurso.
        En este caso el sistema operativo le daría el recurso al primer proceso que lo solicitó y después a segundo que lo solicitó.
        Si es un archivo muy pesado y el acceso puede ralentizar el sistema hay sistemas que controlan cuantos procesos pueden acceder al mismo recurso
        controlando tiempo, espacio del archivo, acciones...
        Al querer dos procesos acceder al mismo recurso estaríamos antes una exclusión mutua, por lo que los problemas que podemos tener es que un proceso quiera cambiar
        algo del archivo a la vez del otro proceso, es decir, que compitan por la modificación de un dato.
        También dependiendo del archivo se podría dar una condición de sincronización, donde por ejemplo el segundo proceso que quiere el archivo
        tenga que esperar a que el primer proceso acabe de usarlo para poder acceder


        b) Define qué es un hilo daemon y describe un caso práctico en el que sería útil utilizarlo.

        Indica también qué ocurriría si el hilo principal termina antes que los hilos daemon. (2 puntos)

        Creo que no he escuchado hablar de esto, pero me suena esto:
        Es un hilo que controla el resto de hilos.
        Ocurriría que al haber terminado el hilo principal los hilos daemon se quedan sin nada que controlar.


         c) En un programa Java, se crean tres hilos que comparten una misma variable global.
        Explica:
        Qué tipo de problemas podrían aparecer.
        Cómo podrías solucionarlos empleando mecanismos de sincronización.
        Si existe alguna alternativa más eficiente en ciertos casos.
        (3 puntos)












         */



    }
}