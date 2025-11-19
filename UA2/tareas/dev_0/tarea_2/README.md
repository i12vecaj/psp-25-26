
# Explicación

## 1. `Cuenta.java` – 

Esta clase contiene:

* Un atributo privado `saldo`
* Un constructor que inicializa el saldo
* Métodos `getSaldo()` y `setSaldo()` con pausa aleatoria simulando retardo real
* Un método `ingresar()` que suma dinero al saldo y lo muestra

### Sincronización

Los métodos se declaran:

```java
public synchronized void ingresar(String nombreHilo, double cantidad)
```

`synchronized` garantiza que **solo un hilo puede ejecutar el método a la vez**, evitando errores de concurrencia.

---

## 2. `Ingreso.java` – Clase que extiende Thread

Esta clase recibe:

* El objeto `Cuenta`
* Un nombre de hilo
* Una cantidad a ingresar

En su método `run()` simplemente llama a:

```java
cuenta.ingresar(getName(), cantidad);
```

Cada hilo que creemos representará un ingreso distinto.

---

## 3. `Main.java` – Clase principal

En el `main`:

1. Se crea una cuenta con saldo inicial.
2. Se crean varios hilos (`Ingreso`) que comparten la misma cuenta.
3. Se ejecutan los hilos con `start()`.
4. Se espera con `join()` a que terminen.
5. Finalmente se muestra el saldo definitivo.

Esto permite ver cómo varios hilos modifican el mismo recurso compartido.

---

# Comportamiento con y sin `synchronized`

## Con synchronized 

Cada operación de ingreso se realiza de forma exclusiva:

* Un hilo entra al método
* Modifica el saldo
* Sale
* Entonces entra el siguiente


##  Sin synchronized 

* Varios hilos modificarán el saldo al mismo tiempo
* Un hilo leerá el saldo sin haber recibido los cambios de otro
* Se producen **condiciones de carrera**
* El valor final del saldo puede ser incorrecto

---

Daniel Ronda