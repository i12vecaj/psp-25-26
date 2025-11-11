# Tarea 1  Programacion y sincronización de hilos

## Enunciado Parte 1
FR1: Crea un programa en Java que lance 5 hilos. 
Cada hilo incrementará una variable contador de tipo entero en 1000 unidades. 
Esta variable estará compartida por todos los hilos. 
Comprueba el resultado final de la variable y reflexiona sobre el resultado. 
¿Se obtiene el resultado esperado? - 3 puntos

### Muestra 
La salida es la siguiente por termianl
```
Hilo: 37 Contador parcial: 4761
Hilo: 40 Contador parcial: 5000
Hilo: 38 Contador parcial: 2562
Hilo: 36 Contador parcial: 3815
Hilo: 39 Contador parcial: 5000
Valor  5000
```
### Reflexión de resultados
Cada vez que ejecuto el programa, el contador muestra un número diferente
porque los hilos se están ejecutando al mismo tiempo. Cuando el for de cada
hilo llega a lo establecido, ese hilo se detiene, pero como ninguno espera
a los demás, todos avanzan en paralelo. Al hacerlo, cada hilo toma valores
distintos de la variable compartida y por eso el resultado final del contador
cambia en cada ejecución.

## Enunciado Parte 2
FR2: Modifica el programa anterior para sincronizar el acceso a dicha varaible. 
Lanza primero los hilos mediante la clase Thread y después mediante el interfaz Runnable. 
Comprueba los resultados e indica las variaciones - 3 puntos

### Muestra Sin sincronizra
```
Hilo: 37 Contador: 3131
Hilo: 36 Contador: 1784
Hilo: 38 Contador: 2010
Hilo: 40 Contador: 2549
Hilo: 39 Contador: 3383
````

### Muestra Sincronizados
```
Hilo: 36 Contador parcial: 1352
Hilo: 38 Contador parcial: 4877
Hilo: 40 Contador parcial: 4908
Hilo: 37 Contador parcial: 3875
Hilo: 39 Contador parcial: 5000
Valor  5000
```

### Reflexión resultados

Al no estar sincronizados, los hilos nunca llegan al valor que queremos,
porque todos acceden al contador al mismo tiempo y se van pisando unos a otros.
Esto provoca que muchos incrementos se pierdan y el resultado final cambie
en cada ejecución.

En cambio, cuando sincronizamos los hilos correctamente, ya no se pisan entre sí.
Cada incremento del contador se hace de forma ordenada y no se solapa con
los demás hilos. Por eso el contador sí llega al valor correcto.

Además, al usar Runnable ya no podemos acceder directamente al id del hilo
como hacíamos con Thread. En este caso debemos usar otros métodos, como
Thread.currentThread().getId(), para obtener el identificador del hilo.