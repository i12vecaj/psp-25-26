/**
Feedback JD - 11/12/2025
Fortalezas: Buen esfuerzo explicando los conceptos en tus palabras; identificas correctamente la condición de carrera y el propósito general de synchronized; comprensión básica de isAlive(), yield() e interrupt().
Áreas de mejora: Diferencia start()/run() completamente invertida; confusión importante en Productor–Consumidor; imprecisiones graves en sleep()/interrupt(); explicaciones de sincronización poco técnicas; ejemplos incorrectos de ejecución concurrente. Formato de entrega .md / carpeta examen no "EXAMEN".
Recomendaciones: Revisar a fondo la semántica real de start() y run(), practicar ejemplos sencillos de concurrencia en Java, y reforzar el uso correcto de terminología técnica para evitar errores conceptuales en examen.
**/


###### 1\. Explica la diferencia entre los métodos start() y run() en un hilo.

###### Pon un ejemplo breve donde se vea la diferencia.



Cuando se usa start no se esta utilizando la llamada programación concurrente, es decir se ejecutaría el hilo, y cuando termine de ejecutarse, sigue el programa ejecutándose secuencialmente. Esto cambia cuando se usa el run, ya que nos permite ejecutarlo a la par que mas hilos y el resto del programa.



hilo1.start();  //cuenta del 1 al 10

hilo2.start();  //lo mismo



//primero se ejecutara el hilo 1 y cuando termine el hilo 2.

//salida: 1,2,3,4,5,6,7,8,9,10,1,2,3...



hilo1.run(); //cuenta del 1 al 10

hilo2.run(); //cuenta del 1 al 10



//se ejecutan ambos al mismo tiempo, permitiendo que interactúen entre ellos o esperen respuestas.

//salida 1,1,2,2,3,3,4,4...



###### 2\. ¿Qué es una condición de carrera?

###### Describe qué la provoca y menciona una técnica para evitarla.

Esto es que un resultado depende del orden impredecible de ejecución, es decir, un dato de nuestro programa depende completamente de lo que le apetezca ya que se ve comprometido por varios hilos. La solución a esto es utilizar synchronized para asegurar la integridad de los datos.





###### 3\. ¿Qué es el modelo Productor–Consumidor?

###### Indica qué problema resuelve y en qué casos se utiliza.

Es un modelo en el que hay un hilo que produce un recurso y un hilo que lo consume, estando estos recursos en una cola compartida. Viene de la arquitectura cliente servidor. 





###### 4\. Observa el siguiente código y responde:



Thread t = new Thread(() -> {

&nbsp;   for (int i = 0; i < 5; i++) {

&nbsp;       System.out.println("A");

&nbsp;   }

});



t.start();



System.out.println(t.isAlive());



###### a) ¿Qué puede imprimir isAlive()?

Un booleano true o false.

###### b) ¿Por qué el resultado puede variar de una ejecución a otra?

Porque puede que el tiempo de ejecución varie y cuando llegue a ejecutar el isAlive algunas veces estará vivo y otras muerto.



###### 5\. ¿Para qué sirve el método yield()?

###### Explica por qué no garantiza orden de ejecución entre hilos.

Es para ceder uso del procesador a otros hilos más importantes para el programa o para otros hilos que necesiten recursos. 

No garantiza el orden porque no asegura que otro hilo se ejecute, este lo que hace es decirle al planificador que puede hacer.





###### 6\. ¿Qué hace el método interrupt() en un hilo?

###### Indica cómo debería reaccionar un hilo bien programado cuando recibe una interrupción.

Interrumpe un hilo que este esperando, durmiendo o bloqueado por cualquier razón. No lo detiene como tal, manda una excepción al programa.

Si esta bien programado debe recibir y manejar la excepción.



###### 7\. ¿Qué significa que un método sea synchronized?

###### Indica cómo afecta a la ejecución cuando varios hilos intentan acceder al mismo recurso.

Esto nos dice que esta sincronizado hasta con otro método, no mas de uno. Si intentan acceder al mismo recurso, cuando uno entre, este recurso quedará bloqueado para el otro, asegurando la integridad de los datos y la lógica del programa.



###### 8\. Explica la diferencia entre sleep() e interrupt().

###### Indica qué ocurre cuando interrumpimos un hilo que está durmiendo.

El sleep seria para parar el hilo temporalmente y el interrupt para detener ese estado de espera.

Se sigue ejecutando el código que hay después del sleep y si no hay finaliza.



###### 9\. Dado el siguiente código, indica si produce o no una condición de carrera y por qué:



class Contador {

&nbsp;   public int valor = 0;

}



Contador c = new Contador();



Thread t1 = new Thread(() -> { for(int i = 0; i < 1000; i++) c.valor++; });

Thread t2 = new Thread(() -> { for(int i = 0; i < 1000; i++) c.valor++; });



t1.start();

t2.start();



Si que se produce una condición de carrera porque los dos hilos están accediendo a la variable valor del objeto contador, por tanto ambos la manipulan mientras operan con ella lo que nos va a dar errores más tarde. Al realizar una prueba con este código sustituyendo los for hasta 10, se puede ver este orden de prints:



0,1,2,3,4,5,6,0,1,2,3,4,7,8,9,5,6,7,8,9



###### 10\. Describe qué es la prioridad de un hilo (setPriority()) y cómo afecta realmente a la ejecución.

###### ¿Es una garantía de orden? ¿Por qué puede comportarse de forma diferente según el sistema operativo?



El setPriority() es un método con el que le decimos al planificador que tiene más o menos prioridad en el orden de ejecución. Con esto el planificador puede gestionar recursos de CPU para los hilos, pero no está asegurado del todo. En los sistemas operativos pueden comportarse de manera diferente porque digamos que los S.O. modernos evitan que un hilo acapare todo el procesador. Así que si queremos un programa funcional y multiplataforma lo óptimo es no ajustar la prioridad a mano. (Funciona distinto en Windows 11 que un ubuntu por ejemplo)

