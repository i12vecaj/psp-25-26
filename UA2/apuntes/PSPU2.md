# 📘 PSP – Unidad 2 (Teoría + Hilos + Productor–Consumidor)

## 1. INTRODUCCIÓN

Esta unidad trata sobre:
- Procesos
- Hilos (threads)
- Concurrencia
- Sincronización
- El problema Productor–Consumidor

---

## 2. PROCESOS

### ¿Qué es un proceso?
Un proceso es un programa en ejecución.

### Características:
- Tiene su propia memoria.
- Gestionado por el sistema operativo.
- Puede contener uno o varios hilos.

---

## 3. HILOS (THREADS)

### ¿Qué es un hilo?
Un hilo es una unidad de ejecución dentro de un proceso.

### Ventajas:
- Más rápidos que los procesos.
- Comparten memoria.
- Permiten multitarea.

---

## 4. DIFERENCIAS PROCESO VS HILO

| Proceso | Hilo |
|--------|------|
| Más pesado | Más ligero |
| Memoria independiente | Memoria compartida |
| Comunicación más lenta | Comunicación rápida |

---

## 5. ESTADOS DE UN HILO

- NEW → creado
- RUNNABLE → listo/ejecutándose
- BLOCKED → bloqueado
- WAITING → esperando
- TIMED_WAITING → esperando con tiempo
- TERMINATED → terminado

---

## 6. CONCURRENCIA

Capacidad de ejecutar varias tareas de manera aparente simultánea.

Ejemplo: escuchar música y navegar a la vez.

---

## 7. PROBLEMAS EN LA CONCURRENCIA

### Condición de carrera:
Varios hilos modifican un recurso compartido al mismo tiempo.

### Sección crítica:
Zona del código que solo puede ejecutar un hilo cada vez.

### Deadlock:
Dos hilos se quedan bloqueados esperando recursos.

---

## 8. SINCRONIZACIÓN

### Palabra clave: `synchronized`

Garantiza que solo un hilo ejecuta el método o bloque.

Ejemplo:

```java
public synchronized void metodoSeguro() {
    // código protegido
}
