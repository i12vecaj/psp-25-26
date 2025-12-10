# ☕ Guía de Supervivencia: Java Concurrente

Resumen práctico para aprobar el examen de Hilos, Procesos y el patrón Productor-Consumidor.

---

## 1. Conceptos Base: ¿Cómo se ejecuta el código?

### 🐢 Ejecución Secuencial (Bloqueante)
* **Qué es:** Una sola "línea de tiempo". Una tarea no empieza hasta que la anterior termina.
* **Quién trabaja:** Solo el hilo `Main`.
* **Código:** Llamar a métodos normales o llamar a `.run()` directamente.
* **Esquema:** `Tarea A` ➔ `Tarea B` ➔ `Tarea C`

### 🐇 Ejecución Concurrente / Paralela (No Bloqueante)
* **Qué es:** Múltiples tareas ocurriendo "a la vez" (o turnándose muy rápido).
* **Quién trabaja:** El hilo `Main` + Hilos secundarios (`Thread-0`, `Thread-1`...).
* **Código:** Instanciar `Thread` y llamar a `.start()`.
* **Esquema:**
    ```text
    Main:   Inicio -------------------------> Fin
              └-> Hilo A: Tarea A -------->
              └-> Hilo B: Tarea B ------>
    ```

---

## 2. Creación de Hilos (La forma correcta)

La "Best Practice" es implementar la interfaz `Runnable`. Esto permite que tu clase pueda heredar de otra cosa si lo necesitas.

```java
public class MiTarea implements Runnable {
    @
```
### ¿Como se lanza?
````java
MiTarea tarea = new MiTarea();
Thread hilo = new Thread(tarea);
hilo.start(); // ¡OJO! Usar start(), NUNCA run()
````
### 3. Diccionario de Métodos (Vital para el Examen)

Java desde 0 hasta Concurrencia y Spring boot
Gem personalizado

¡Claro que sí! Aquí tienes un README.md estructurado como una "Chuleta de Estudio" definitiva.

He condensado todo lo que hemos visto: la diferencia entre secuencial y paralelo, los métodos clave de Thread, y la lógica compleja del Productor-Consumidor (El Bar).

Copia el siguiente bloque y guárdalo como RESUMEN_CONCURRENCIA.md.
Markdown

# ☕ Guía de Supervivencia: Java Concurrente

Resumen práctico para aprobar el examen de Hilos, Procesos y el patrón Productor-Consumidor.

---

## 1. Conceptos Base: ¿Cómo se ejecuta el código?

### 🐢 Ejecución Secuencial (Bloqueante)
* **Qué es:** Una sola "línea de tiempo". Una tarea no empieza hasta que la anterior termina.
* **Quién trabaja:** Solo el hilo `Main`.
* **Código:** Llamar a métodos normales o llamar a `.run()` directamente.
* **Esquema:** `Tarea A` ➔ `Tarea B` ➔ `Tarea C`

### 🐇 Ejecución Concurrente / Paralela (No Bloqueante)
* **Qué es:** Múltiples tareas ocurriendo "a la vez" (o turnándose muy rápido).
* **Quién trabaja:** El hilo `Main` + Hilos secundarios (`Thread-0`, `Thread-1`...).
* **Código:** Instanciar `Thread` y llamar a `.start()`.
* **Esquema:**
    ```text
    Main:   Inicio -------------------------> Fin
              └-> Hilo A: Tarea A -------->
              └-> Hilo B: Tarea B ------>
    ```

---

## 2. Creación de Hilos (La forma correcta)

La "Best Practice" es implementar la interfaz `Runnable`. Esto permite que tu clase pueda heredar de otra cosa si lo necesitas.

```java
public class MiTarea implements Runnable {
    @Override
    public void run() {
        // Código que se ejecutará en paralelo
    }
}
```
### ¿Cómo se lanza?
```Java

MiTarea tarea = new MiTarea();
Thread hilo = new Thread(tarea);
hilo.start(); // ¡OJO! Usar start(), NUNCA run()
```
### 3. Diccionario de Métodos (Vital para el Examen)
Método	Descripción	¿Bloquea al que llama?
start()	Crea un nuevo hilo del S.O. y llama a run() internamente.	NO (Sigue ejecutando)
run()	Ejecuta el código en el hilo actual (Secuencial).	SÍ (Es una trampa)
join()	El hilo actual se detiene y espera a que el otro termine.	SÍ
sleep(ms)	Pausa el hilo actual X milisegundos.	SÍ
interrupt()	Envía una señal al hilo para que pare.	NO

### 4.El Problema: Recursos Compartidos 💥

Cuando varios hilos tocan la misma variable a la vez (ej: escribir en un fichero, sumar un contador), ocurren Condiciones de Carrera. Los datos se corrompen.
Solución A: El Cerrojo (synchronized)
```
public synchronized void sumar() {
contador++; // Ahora es seguro
}
```
Garantiza que solo un hilo entre al método a la vez. Es como el pestillo de un baño.

#### Solucion B El Patron Productor - Consumidor

Usado cuando unos hilos generan datos y otros los consumen. Necesitamos wait() y notifyAll() para coordinarlos.
Los 3 Actores

    Monitor (La Barra): Objeto compartido con métodos synchronized.

    Productor (Camarero): Pone datos. Si está lleno, espera.

    Consumidor (Cliente): Quita datos. Si está vacío, espera.

Código "Canónico" (Memorizar)
```java


public synchronized void metodoSeguro() {
// 1. COMPROBAR (Siempre con WHILE, nunca con IF)
// "Mientras no pueda actuar, me duermo"
while (condicionDeEspera) {
try {
wait(); // Me duermo y suelto la llave (synchronized)
} catch (InterruptedException e) { ... }
}

    // 2. ACTUAR (Sección Crítica)
    realizarAccion();

    // 3. AVISAR
    notifyAll(); // Despierto a los que dormían (wait)
}
```
## 5. Trampas Comunes de Examen ⚠️

  #### Llamar a run() en vez de start():

- Resultado: El código corre, pero no es paralelo. El programa va lento y se bloquea.

  #### Usar if en vez de while antes del wait():

- Resultado: "Despertar espurio". El hilo despierta, pero la condición sigue sin cumplirse (otro hilo le ganó) y rompe el programa.

   #### Olvidar el join():
 - Resultado: El Main imprime resultados vacíos (ej: "Total: 0") y termina antes de que los hilos hayan trabajado.

   #### Instanciar múltiples Monitores:

 -Resultado: Hacer new Barra() dentro del Camarero y otro new Barra() dentro del Cliente. Cada uno espera en una barra distinta y nunca se encuentran. Hay que pasar la misma instancia.