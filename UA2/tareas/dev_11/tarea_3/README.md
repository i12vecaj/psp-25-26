<div align="center">

# T3 – Tarea 3 – Programación y sincronización de hilos en Java 3

</div>

#### _Criterios a), b), c), d), e), f), g) y hCriterios a), b), c), d), e), f), g) y h_

- FR1: Crea un programa que reciba a través de sus argumentos una lista de ficheros de texto (procura que sean de un cierto tamaño, por ejemplo El Quijote.txt) y cuente el número de caracteres que hay en cada fichero (ejecución secuencial). Ayuda en este enlace - 2 puntos.
- FR2: Modifica el programa para que cree un hilo para cada fichero (ejecución concurrente) - 2 puntos.
- FR3: Compara el tiempo de ejecución entre los dos apartados anteriores - 2 puntos.
- Implementa el control de errores básico - 2 puntos
- Documenta el código indicando el funcionamiento del programa y las diferencias que has observado entre el primer y el segundo apartado - 2 puntos

Para el FR3 puedes usar el siguiente código, o alguna variación del mismo:

```
long t_comienzo, t_fin;
t_comienzo = System.currentTimeMillis();
Hilo(); // Se ejecuta el hilo o el proceso (en el caso secuencial)
t_fin = System.currentTimeMillis();
long t_total = t_fin - t_comienzo;
```

## FR1 — Ejecución secuencial

**Archivos: Carpeta `secuencial/`**

- `Main.java`
- `ContadorCaracteres.java`
- `ContadorCaracteresHilo.java`

### Funcionamiento

- Se recibe un fichero de texto (`el_quijote.txt`) como argumento.
- Se crea un objeto `ContadorCaracteresHilo` que extiende de `Thread`.
- La ejecución se hace secuencial usando `hilo.run()`.
- Se recorre el fichero carácter a carácter y se cuenta el total.
- Se muestra por pantalla el número de caracteres del fichero.

### Resultado esperado y observado

- Esperado: Se imprime el número total de caracteres.
- Observado: Correcto, el programa termina de manera secuencial sin errores.

## FR2 — Ejecución concurrente

**Archivos: Carpeta `concurrente/`**

- `Main.java`
- `ContadorCaracteres.java`
- `ContadorCaracteresHilo.java`

### Funcionamiento

- `ContadorCaracteresHilo` implementa `Runnable`.
- Se crea un `Thread` para cada fichero y se ejecuta con `start()`.
- Se hace `join()` para que el hilo principal espere a que terminen los hilos.
- Cada hilo cuenta los caracteres de manera independiente.
- Se imprime el número de caracteres junto con el ID del hilo, mostrando concurrencia.

### Resultado esperado y observado

- Esperado: Cada hilo muestra el número de caracteres y el programa principal espera a que terminen.
- Observado: Correcto, los hilos se ejecutan concurrentemente y la salida puede variar de orden según la ejecución de cada hilo.
- **Conclusión**: La concurrencia permite que varios ficheros se procesen a la vez, reduciendo el tiempo total si se usan varios ficheros grandes.

## FR3 — Comparación de tiempos

Se midió el tiempo total de ejecución usando:

```
long t_comienzo = System.currentTimeMillis();
// Ejecución secuencial o concurrente
long t_fin = System.currentTimeMillis();
long t_total = t_fin - t_comienzo;
System.out.println("Tiempo total: " + t_total + " ms");
```

### Observaciones

- Secuencial: tarda más, ya que procesa cada fichero de manera independiente.
- Concurrente: tarda menos cuando hay varios ficheros, porque se procesan en paralelo.
- En el caso de un solo fichero, los tiempos son casi equivalentes, ya que solo hay un hilo.

**Conclusión**: La concurrencia es beneficiosa principalmente al procesar varios ficheros grandes al mismo tiempo.

## Control de errores

- Se comprueba que el fichero existe y se puede leer.
- Se gestionan excepciones de tipo `IOException`.
- En concurrente, también se captura `InterruptedException` al hacer `join()`.
