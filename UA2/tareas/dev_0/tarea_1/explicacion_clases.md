# Explicación de las clases de la tarea de hilos en Java

## `ua2tarea1fr1.java` – Hilos sin sincronización

Creamos 5 hilos que incrementan una variable compartida (`contador`) 1000 veces cada uno, **sin utilizar sincronización**.

### Funcionamiento
- Se crea una variable `contador` compartida.
- Se lanzan 5 hilos, cada uno incrementa el contador 1000 veces.
- No se controla el acceso concurrente al contador.

### Resultado esperado
**Teórico:** 5000  
**Real:** suele ser **menor que 5000** debido a condiciones de carrera.
> Sin sincronización, varios hilos pueden intentar actualizar la variable al mismo tiempo. Esto genera valores incorrectos.

---

## `ua2tarea1fr2.java` – Hilos con sincronización usando `Thread`

Repetimos el ejercicio anterior pero **sin errores de concurrencia**, usando sincronización.

### Funcionamiento
- Se mantiene el uso de `Thread` directamente.
- Se añade un método `synchronized incrementar()` para controlar el acceso al contador.
- Cada hilo llama a ese método al incrementar.

### Resultado esperado
Siempre se obtiene **5000**.
> `synchronized` garantiza que solo un hilo acceda a la variable a la vez.

---

## `ua2tarea1fr2runnable.java` – Hilos con sincronización usando `Runnable`

Lo mismo que en la clase anterior, pero usando la interfaz `Runnable`.

### Funcionamiento
- Se crea un `Runnable` que contiene la lógica del incremento.
- Se pasan varias instancias `Runnable` a objetos `Thread`.
- Se usa de nuevo el método `synchronized incrementar()`.

### Resultado esperado
Siempre se obtiene **5000**.
> Con `Runnable`, se separa la lógica del hilo de su ejecución. Es la forma más profesional de trabajar con hilos.

---

## Resumen comparativo

| Archivo | Sincronización | Método | Resultado |
|--------|----------------|--------|----------|
| `ua2tarea1fr1.java` |  No | Thread |  Incorrecto (<5000) |
| `ua2tarea1fr2.java` |  Sí | Thread |  5000 |
| `ua2tarea1fr2runnable.java` |  Sí | Runnable + Thread |  5000 |

---


