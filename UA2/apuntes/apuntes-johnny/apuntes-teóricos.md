# Apuntes de Teoría: Fundamentos de Procesos, Hilos y Servicios Web

## 1. Procesos y Sistema Operativo

Esta sección cubre la base de cómo el SO gestiona las tareas.

- **Definición de Proceso:** Es un programa en ejecución. No es solo el código estático, sino que incluye el contador de programa (PC), la pila (stack), los datos y el estado actual.
- **El PCB (Process Control Block):** Es el "DNI" del proceso. Una estructura de datos única para cada proceso que almacena su PID, registros de CPU, estado y gestión de memoria. Se crea al iniciar el proceso y se destruye al acabar.
- **Ciclo de Vida (Estados):**
  - _New (Creado):_ Acaba de nacer.
  - _Ready (Listo):_ Esperando turno de CPU.
  - _Running (Ejecución):_ Usando la CPU.
  - _Blocked/Wait (Bloqueado):_ Esperando un evento externo (E/S, señal).
  - _Zombie/Dead:_ Ha terminado pero su padre aún no ha recogido su estado.
- **Gestión en Linux:**
  - `fork()`: Crea una **copia exacta** del proceso padre (hijo), pero con su propio PID y espacio de memoria independiente. Las variables se copian, no se comparten.
  - **Comunicación (IPC):** Como los procesos tienen memoria aislada, necesitan mecanismos como **Tuberías (Pipes)** (unidireccionales), colas de mensajes o semáforos para hablar entre sí.
- **Condiciones de Bernstein:** Reglas teóricas para saber si dos instrucciones pueden ejecutarse en paralelo sin errores. Básicamente, si uno escribe una variable que el otro lee (o ambos escriben la misma), no pueden ser concurrentes.

---

## 2. Hilos (Threads) y Concurrencia

Diferencias clave con los procesos y cómo se gestionan dentro de la JVM.

- **Definición:** Se les llama "procesos ligeros". Son secuencias de ejecución dentro del contexto de un mismo proceso.
- **Hilo vs. Proceso:**
  - **Memoria:** Los procesos tienen memoria independiente; los hilos **comparten** la memoria y recursos del proceso padre (lo que facilita la comunicación pero aumenta el riesgo de errores).
  - **Eficiencia:** Crear un hilo es mucho más rápido y "barato" en recursos que crear un proceso nuevo.
- **Estados del Hilo:**
  - _New:_ Creado pero no arrancado (`start()` aún no invocado).
  - _Runnable:_ Listo o ejecutándose.
  - _Blocked/Waiting:_ Dormido (`sleep`), esperando un monitor (`wait`) o esperando E/S.
  - _Dead:_ Terminó su método `run()`.
- **Prioridades:** Valor del 1 (mínimo) al 10 (máximo). **Nota:** La prioridad es una sugerencia al planificador del S.O., no una garantía de orden de ejecución.
- **Conceptos de Control:**
  - `join()`: Fuerza a un hilo a esperar a que otro termine (coordinación).
  - `yield()`: Un hilo sugiere "ceder el paso" voluntariamente para que otros usen la CPU.
  - `interrupt()`: La forma segura de detener un hilo, en contraposición al obsoleto `stop()`.

---

## 3. Sincronización y Problemas de Concurrencia

El problema fundamental de compartir memoria y cómo solucionarlo.

- **Condición de Carrera (Race Condition):** El caos que ocurre cuando varios hilos intentan leer/escribir datos compartidos al mismo tiempo sin orden. Resulta en datos corruptos e impredecibles.
- **Exclusión Mutua (Monitor):** Mecanismo para proteger una "Sección Crítica" (código delicado).
  - **Synchronized:** Actúa como un **cerrojo o pestillo**. Solo un hilo puede estar dentro de un bloque sincronizado a la vez.
- **Mecanismo de Coordinación (Semáforo interno):**
  - `wait()`: El hilo se detiene voluntariamente y **suelta el cerrojo** para que otros entren. Se usa cuando no puede continuar (ej: buffer lleno).
  - `notifyAll()`: Despierta a todos los hilos que estaban esperando. Es más seguro que `notify()` (que despierta solo a uno aleatorio) para evitar bloqueos indeseados.
- **Patrón Productor-Consumidor:**
  - Modelo clásico donde unos hilos crean datos y otros los usan, comunicándose a través de un **Buffer (Monitor)**.
  - El monitor asegura que el productor pare si está lleno y el consumidor espere si está vacío.

---

## 4. Gestión Avanzada de Procesos (ProcessBuilder)

Cómo Java interactúa con el Sistema Operativo a bajo nivel.

- **ProcessBuilder:** Clase no sincronizada diseñada para gestionar atributos de procesos del S.O. antes de lanzarlos.
- **Atributos Gestionables:**
  - **Comando:** La instrucción a ejecutar.
  - **Entorno:** Variables de entorno (Map Key-Value) que se pueden modificar antes de lanzar el proceso.
  - **Directorio de trabajo:** Desde dónde se ejecuta el comando.
- **Redirección de E/S:** Permite redirigir la entrada, salida y errores estándar (stdin, stdout, stderr) a archivos, tuberías o heredarlas del proceso padre. Si se redirige a un archivo, el flujo en Java será nulo.

---

## 5. Arquitectura de Servicios Web (Spring Boot)

Conceptos de diseño para la comunicación entre aplicaciones en red.

- **Concepto de Servicio Web:** Es como un "camarero universal" que permite que aplicaciones en distintos lenguajes se comuniquen vía HTTP.
- **REST (Representational State Transfer):**
  - Arquitectura estándar basada en recursos y verbos HTTP.
  - **Stateless (Sin estado):** Cada petición es independiente, el servidor no guarda memoria de la anterior (como enviar un email, no una llamada telefónica).
- **Verbos y Códigos HTTP:**
  - _Verbos:_ Definen la acción (GET=Leer, POST=Crear, PUT=Actualizar, DELETE=Borrar).
  - _Códigos:_ La respuesta del servidor (200 OK = Éxito, 404 = No encontrado).
- **Arquitectura en Capas (Spring):**
  - **Controller:** El "Portero". Recibe la petición HTTP.
  - **Service:** El "Cerebro". Ejecuta la lógica de negocio.
  - **Repository:** Las "Llaves". Accede a la base de datos.
- **Despliegue y Calidad:**
  - **Docker:** Empaqueta la aplicación en un contenedor ("caja a medida") para que funcione igual en cualquier máquina.
  - **Pruebas:** Unitarias (componentes aislados) e Integración (componentes juntos) son obligatorias.

---

### 🧠 Analogías para el Examen (Reglas Mnemotécnicas)

1.  **Hilos vs Procesos:**
    - _Proceso:_ Una casa entera (tiene sus propias habitaciones/memoria).
    - _Hilo:_ Los habitantes de la casa (comparten el salón/memoria, si uno rompe algo, afecta a todos).
2.  **Sincronización (El Baño):**
    - `synchronized`: El pestillo de la puerta. Solo entra uno.
    - `wait()`: Entras, ves que no hay papel higiénico, quitas el pestillo y te sientas a esperar fuera.
    - `notifyAll()`: Alguien repone el papel y grita "¡Ya hay papel!", despertando a los que esperaban.
3.  **API REST (El Restaurante):**
    - _Carta:_ La documentación de la API.
    - _Camarero:_ El Servicio Web.
    - _Cocina:_ El Servidor/Lógica.
    - _Cliente:_ Tu aplicación móvil/web.
    - No te importa cómo cocinan (backend), solo que si pides "Hamburguesa" (Request), te traigan una (Response 200 OK).
