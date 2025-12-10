# ☕ Guía de Supervivencia: Concurrencia en Java

Resumen práctico para aprobar el examen de Hilos, Procesos y el patrón Productor-Consumidor.

---

## 1. Conceptos Base: ¿Cómo se ejecuta el código?

### 🐢 Ejecución Secuencial (Bloqueante)
* **Qué es:** Una sola "línea de tiempo". Una tarea no empieza hasta que la anterior termina.
* **Quién trabaja:** Solo el hilo `Main`.
* **En código:** Llamada a métodos normales o llamar a `.run()` directamente.
* **Esquema:** `Tarea A` ➔ `Tarea B` ➔ `Tarea C`

### 🐇 Ejecución Concurrente / Paralela (No Bloqueante)
* **Qué es:** Múltiples tareas ocurriendo "a la vez" (o turnándose muy rápido).
* **Quién trabaja:** El hilo `Main` + Hilos secundarios (`Thread-0`, `Thread-1`...).
* **En código:** Instanciar `Thread` y llamar a `.start()`.
* **Esquema:**
    ```
    Main:   Inicio -------------------------> Fin
              └-> Hilo A: Tarea A -------->
              └-> Hilo B: Tarea B ------>
    ```

---

## 2. Creación de Hilos

La forma recomendada (Best Practice) es implementar la interfaz `Runnable`.

```java
public class MiTarea implements Runnable {
    @Override
    public void run() {
        // Código que se ejecutará en paralelo
    }
}
```
### Como se lanza 
```java
MiTarea tarea = new MiTarea();
Thread hilo = new Thread(tarea);
hilo.start(); // ¡OJO! Usar start(), NUNCA run()
```
## 3. Diccionario de Métodos (Para el Examen
Método	Descripción	¿Bloquea?
start()	Crea un nuevo hilo del S.O. y llama a run() internamente.	NO
run()	Ejecuta el código en el hilo actual (Secuencial).	SÍ (hasta que acabe)
join()	El hilo actual espera a que el otro termine.	SÍ
sleep(ms)	Pausa el hilo actual X milisegundos.	SÍ
yield()	Cede el turno de CPU a otro hilo (amabilidad).	NO

## 4. Problemas de Compartir Recursos

Cuando varios hilos tocan la misma variable a la vez, ocurren Condiciones de Carrera (Race Conditions). Los datos se corrompen.
### 🛡️ Solución 1: synchronized (El Cerrojo)
Garantiza que solo un hilo entre al método a la vez.
```java 
public synchronized void sumar() {
contador++; // Ahora es seguro
}
```
### 🚦 Solución 2: Coordinación (Wait / Notify)

Usado en el patrón Productor-Consumidor para que los hilos no trabajen en balde.

    wait(): "No puedo seguir (ej: barra vacía/llena). Suelto el cerrojo y me duermo."

    notifyAll(): "He cambiado algo (ej: puse cerveza). ¡Despertad todos y comprobad!"

### Estructura Canónica (¡Memorizar!)
```java


public synchronized void metodoSeguro() {
// 1. COMPROBAR (Siempre con WHILE, nunca con IF)
while (condicionDeEspera) {
try {
wait(); // Me duermo y suelto la llave
} catch (InterruptedException e) { ... }
}

    // 2. ACTUAR
    realizarAccion();

    // 3. AVISAR
    notifyAll(); // Despierto a los demás
}
```
### 5. Trampas Comunes de Examen ⚠️

   Llamar a run() en vez de start():

        Resultado: El código corre, pero no es paralelo. El programa se bloquea hasta que termina la tarea.

   Usar if en vez de while antes del wait():

        Resultado: "Despertar espurio". El hilo despierta, pero la condición sigue sin cumplirse y rompe el programa.

   Olvidar el join():

        Resultado: El Main imprime "Total: 0" y termina antes de que los hilos hayan contado nada.

   Olvidar synchronized en Productor-Consumidor:

        Resultado: Excepciones IllegalMonitorStateException al hacer wait/notify, o datos corruptos.