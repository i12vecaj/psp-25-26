# 📚 Apuntes de Teoría: Programación de Servicios y Procesos

## BLOQUE 1: Gestión de Procesos y Sistema Operativo

Este bloque cubre cómo el sistema operativo maneja la ejecución de programas y la diferencia entre la teoría general (S.O.) y la implementación en Java.

### 1. Concepto de Proceso y PCB

- **Definición:** Un proceso no es solo código estático; es un **programa en ejecución**. Incluye el código ejecutable, los datos, la pila (stack) y el contador de programa (PC).
- **El PCB (Process Control Block):** Es la estructura de datos fundamental que actúa como el "DNI" del proceso.
  - Se crea cuando nace el proceso y se destruye al finalizar.
  - Contiene: Identificador (PID), estado actual, registros de la CPU, información de gestión de memoria y E/S.
  - **Importante:** Cada proceso tiene su propio PCB único y su propio espacio de memoria aislado.

### 2. Ciclo de Vida (Estados de un Proceso)

Los procesos no están siempre ejecutándose. Pasan por un diagrama de estados:

1.  **New (Creado):** El proceso se está construyendo (se crea el PCB).
2.  **Ready (Listo):** Está en memoria esperando turno de CPU. No se ejecuta porque el procesador está ocupado con otro.
3.  **Running (Ejecución):** Tiene el control de la CPU y está ejecutando instrucciones.
4.  **Blocked/Wait (Bloqueado):** El proceso se detiene voluntariamente (por ejemplo, esperando a leer un archivo o recibir una señal). No puede volver a _Running_ hasta que el evento ocurra; primero debe volver a _Ready_.
5.  **Zombie/Dead:** El proceso ha terminado (`exit()`), pero su estructura sigue en la tabla de procesos hasta que el padre recoge su estado de salida.

### 3. Creación y Gestión (Unix vs. Java)

- **Modelo Unix/Linux (`fork`):**
  - La función `fork()` crea un nuevo proceso (hijo) que es una **copia exacta** del padre.
  - El hijo recibe una copia de las variables, pero **no comparten memoria** (tienen espacios de direcciones distintos). Si el hijo cambia una variable, el padre no se entera.
- **Modelo Java (`ProcessBuilder`):**
  - Java gestiona procesos del S.O. mediante la clase `ProcessBuilder`.
  - Permite configurar atributos antes de lanzar el proceso: comando, **directorio de trabajo** y **variables de entorno** (un mapa clave-valor dependiente del sistema).
  - **Redirección E/S:** Por defecto, los procesos usan tuberías. `ProcessBuilder` permite redirigir la salida estándar (`stdout`) o errores a archivos. Si se redirige a un archivo, el flujo en Java pasa a ser nulo.
  - _Nota de seguridad:_ `ProcessBuilder` **no es sincronizada**. Si varios hilos la usan a la vez, debe sincronizarse externamente.

---

## BLOQUE 2: Hilos (Threads) y Concurrencia

Aquí se analiza la unidad de ejecución más pequeña y cómo Java la gestiona dentro de la JVM.

### 1. Definición y Diferencias con Procesos

- **Hilo:** Es una secuencia de código en ejecución _dentro_ del contexto de un proceso. Se les llama "procesos ligeros".
- **Diferencia Clave:**
  - Los **procesos** son independientes y tienen memoria aislada (comunicación lenta/compleja vía tuberías o sockets).
  - Los **hilos** comparten los recursos y la memoria del proceso padre (comunicación rápida mediante objetos compartidos, pero mayor riesgo de conflictos).

### 2. Estados del Hilo en Java

Un hilo pasa por estados similares a un proceso, pero gestionados por la JVM:

- **New:** Instanciado pero no arrancado (`new Thread()`).
- **Runnable (Ejecutable):** Tras llamar a `start()`. Puede estar ejecutándose o esperando turno en el planificador.
- **Blocked/Waiting:** Suspendido por `sleep()`, esperando E/S, o esperando en un `wait()` o bloqueo sincronizado.
- **Dead:** Ha finalizado su método `run()`.

### 3. Métodos de Control y Planificación

- **start() vs run():** `start()` crea el nuevo hilo de ejecución y llama internamente a `run()`. Si llamas a `run()` directamente, se ejecuta como un método normal en el hilo actual, sin concurrencia.
- **sleep(ms):** Pausa el hilo un tiempo determinado sin perder la posesión de monitores/candados.
- **yield():** Sugerencia al planificador para "ceder el paso" y dejar que otros hilos de igual prioridad se ejecuten. No garantiza nada.
- **join():** Permite la coordinación. El hilo que llama a `t.join()` se bloquea hasta que el hilo `t` termine. Es fundamental para esperar resultados.
- **interrupt():** La forma correcta de detener un hilo. El método antiguo `stop()` está obsoleto (deprecated) porque es inseguro (puede dejar datos corruptos o interbloqueos). Lo correcto es usar `interrupt()` y capturar la `InterruptedException`.

### 4. Prioridades

- Van del 1 (`MIN_PRIORITY`) al 10 (`MAX_PRIORITY`).
- **Concepto:** El planificador tiende a elegir hilos con mayor prioridad, pero **no es una garantía** de orden de ejecución. Depende totalmente de la implementación del S.O. subyacente.

---

## BLOQUE 3: Sincronización y Problemas Clásicos

Este es el bloque más crítico para la resolución de ejercicios teóricos sobre concurrencia.

### 1. El Problema: Condición de Carrera

- Ocurre cuando varios hilos acceden y modifican datos compartidos simultáneamente sin control. El resultado depende del orden aleatorio de ejecución ("quién llega primero"), provocando datos corruptos.
- **Condiciones de Bernstein:** Reglas teóricas para saber si dos instrucciones pueden ser concurrentes. Básicamente, si un proceso escribe en una variable que otro lee (o ambos escriben en la misma), **no** pueden ejecutarse en paralelo sin sincronización.

### 2. La Solución: Monitores y Bloqueo

Para evitar el caos, se usan secciones críticas (zonas de código donde solo entra uno a la vez).

- **Synchronized:** Actúa como un "pestillo" o cerrojo. Si un hilo entra en un bloque/método sincronizado, cierra la puerta. Los demás deben esperar fuera.
- **Atomicidad:** Los bloques sincronizados garantizan que las operaciones se hagan de una sola vez ("todo o nada").

### 3. Mecanismo de Coordinación (`wait` / `notify`)

A veces un hilo tiene el cerrojo pero no puede continuar (ej. el búfer está lleno).

- **wait():** El hilo suelta el cerrojo (`synchronized`) y se duerme. Es vital entender que **libera el recurso** para que otros entren.
- **notifyAll():** Despierta a todos los hilos dormidos en ese objeto. Se prefiere sobre `notify()` (que despierta solo a uno al azar) para evitar que se despierte al hilo equivocado y el sistema se quede parado.

### 4. Patrón Productor-Consumidor

Es el modelo clásico de cooperación:

- **Productor:** Genera datos y los pone en un búfer. Si el búfer está lleno, hace `wait()`.
- **Consumidor:** Saca datos. Si el búfer está vacío, hace `wait()`.
- **Regla de Oro:** Siempre se debe comprobar la condición del `wait` dentro de un bucle **`while`** (no un `if`). Esto protege contra "despertares espurios" (el hilo se despierta sin que la condición haya cambiado).

---

## BLOQUE 4: Servicios Web y Arquitectura (Spring Boot)

Teoría sobre cómo se estructuran las aplicaciones modernas distribuidas.

### 1. Concepto y Necesidad

- **El Problema:** Comunicar aplicaciones heterogéneas (distintos lenguajes, S.O., ubicaciones).
- **Servicio Web:** Actúa como un "camarero universal". Ofrece una interfaz (API) estándar para pedir recursos sin importar cómo se "cocinan" dentro.
- **Stateless (Sin Estado):** En la web moderna (REST), cada petición es independiente. El servidor no recuerda la petición anterior. Esto permite que el sistema escale fácilmente.

### 2. Arquitectura REST

- Se basa en el protocolo HTTP. Es simple y estándar.
- **Recursos:** Son los "nombres" (ej: `/productos`).
- **Verbos HTTP:** Son las acciones:
  - `GET`: Leer/Pedir info.
  - `POST`: Crear info nueva.
  - `PUT`: Actualizar.
  - `DELETE`: Borrar.
- **Códigos de Estado:** La respuesta del servidor.
  - `200 OK`: Todo bien.
  - `404 Not Found`: Recurso no encontrado.

### 3. Capas en Spring Boot

Spring organiza el código mediante anotaciones ("pegatinas" que definen roles):

1.  **@RestController (El Portero):** Recibe la petición HTTP del usuario.
2.  **@Service (El Cerebro):** Contiene la lógica de negocio. Procesa los datos.
3.  **@Repository (Las Llaves):** Es la capa de acceso a datos. Es el único que habla con la Base de Datos (SQL).

### 4. Calidad y Despliegue

- **Testing:**
  - _Unitarias:_ Prueban una pieza aislada (rápido).
  - _Integración:_ Prueban que las piezas encajan entre sí (motor con chasis).
- **Docker:** Soluciona el problema de "en mi máquina funciona". Empaqueta la aplicación y sus dependencias en un **contenedor** estándar que corre igual en cualquier sitio.

---

### 💡 Resumen de Analogías para el Examen

1.  **API REST = Restaurante:**

    - Tú (Cliente) miras la carta (API) y pides al Camarero (Servicio Web).
    - No entras a la cocina (Servidor/Lógica).
    - Si pides algo que no hay, el camarero dice "Lo siento" (Error 404).

2.  **Sincronización = El Baño con llave:**

    - Solo entra una persona (`synchronized`).
    - Si entras y no hay jabón, sales y esperas fuera (`wait`), dejando la llave libre.
    - Cuando el encargado repone jabón, avisa a todos (`notifyAll`) para que intenten entrar de nuevo.

3.  **Procesos vs Hilos = Casa vs Habitantes:**
    - **Proceso:** Una casa independiente. Si construyes otra, es una estructura nueva y separada.
    - **Hilos:** Los habitantes de la casa. Comparten el salón, la TV y el aire. Si uno incendia el salón (error grave en memoria compartida), afecta a todos los habitantes.
