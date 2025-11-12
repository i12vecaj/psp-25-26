<div align="center">

# T2 – Tarea 2 – Programación y sincronización de hilos en Java 2

</div>

#### _Criterios a), b), c), d), e), f), g) y h)_

- FR1: Crea una clase CuentaCorriente, con un atributo que indique el saldo de la cuenta, el constructor que le da un valor inicial al saldo y después los métodos setter y getter. En estos métodos deberás añadir un sleep() aleatorio, que duerma al hilo durante un número de milisegundos que oscile entre 250 y 2000 (0,25s y 2s respectivamente). Añade también otro método que reciba una cantidad y la añada al saldo, indicando por pantalla el estado previo del saldo, el estado final y quién realiza el ingreso. Deberás definir los parámetros que reciba este método y deberás definirlo como synchronized - 2 puntos.
- FR2: Crea una clase que extienda de Thread, y desde el método run() usará el método de la clase CuentaCorriente que permite añadir saldo a la cuenta - 2 puntos.
- FR3: Crea en el método main un objeto de tipo CuentaCorriente asignando un valor inicial y visualiza el saldo inicial. Ahora crea varios hilos que compartan dicho objeto. Cada hilo recibirá un nombre y tendrá una cantidad de dinero. Lanza los hilos y espera a que finalicen para visualizar el saldo final de la cuenta - 2 puntos.
- FR4: Comprueba ahora los resultados quitando el modificador synchronized del método de la clase CuentaCorriente y del método que permite añadir saldo. ¿Cuál es la diferencia? ¿Por qué el resultado obtenido difiere respecto al apartado 3? - 2 puntos.
- Implementa el control de errores básico - 1 punto
- Documenta el código indicando el funcionamiento del programa y las diferencias que has observado entre el tercer y el cuarto apartado. - 1 punto

## FR1 — Ejecución de hilos sin interferencia visible

`Archivo: UA2Tarea2.java`

En esta versión, se crean varios hilos que comparten un mismo objeto `CuentaCorriente`. Cada hilo ingresa una cantidad determinada en la cuenta.

### Funcionamiento

- Se crea un objeto `CuentaCorriente` con saldo inicial de 1000 €.
- Se crean tres hilos (`HiloIngreso`) que ingresan 500 €, 300 € y 200 €, respectivamente.
- Cada hilo ejecuta el método `ingresar()`, mostrando el saldo antes y después del ingreso.
- Se aplican pausas aleatorias para simular latencia en la ejecución de los hilos.

### Resultado esperado y observado

- Esperado: El saldo final debe reflejar la suma de todos los ingresos (1000 + 500 + 300 + 200 = 2000 €).
- Resultado real: Gracias a la sincronización, el saldo final siempre coincide con el esperado, sin pérdida de datos.

Conclusión: La sincronización evita condiciones de carrera y garantiza la consistencia de los datos al modificar el saldo compartido.

</div>

## FR2 — Ejecución con métodos sincronizados

`Archivo: CuentaCorriente.java`

En esta versión, los métodos `getSaldo()`, `setSaldo()` e `ingresar()` están declarados como `synchronized` para asegurar que solo un hilo pueda acceder a ellos a la vez.

### Funcionamiento

- La palabra clave `synchronized` bloquea temporalmente los métodos para que un hilo complete su operación antes de que otro pueda acceder.
- Esto garantiza que los cálculos del saldo sean correctos incluso cuando los hilos se ejecutan concurrentemente.
- Se incluyen pausas aleatorias para simular latencia y observar la importancia de la sincronización.

### Resultado esperado y observado

- Esperado: Saldo final = 2000 €
- Resultado real: Saldo final = 2000 € (coincide en todas las ejecuciones)

Conclusión: La sincronización evita interferencias entre hilos, garantizando la correcta actualización del saldo compartido.

</div>

## FR2 — Clase `HiloIngreso`

`Archivo: HiloIngreso.java`

Esta clase representa un hilo que realiza un ingreso en la cuenta compartida.

### Funcionamiento

- Cada hilo recibe un objeto `CuentaCorriente`, una cantidad y un nombre.
- Al ejecutar `run()`, invoca `cuenta.ingresar(cantidad, nombreHilo)`.
- Los métodos sincronizados de `CuentaCorriente` aseguran que los ingresos se realicen de forma segura.

### Resultado esperado y observado

- Esperado: Cada ingreso se realiza correctamente, mostrando saldo antes y después de la operación.
- Resultado real: Los ingresos se aplican de forma correcta y el saldo final coincide con la suma de todos los ingresos.

Conclusión: Utilizar hilos con métodos sincronizados garantiza la seguridad y consistencia de los datos en escenarios concurrentes.

<div align="center">

<img src="https://i.imgur.com/Ig0ZUx1.png" alt="Ejemplo de salida"  />

</div>
</div>
