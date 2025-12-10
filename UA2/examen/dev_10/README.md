# 🍺 Bar de Moe — Examen PSP (UA2)

Este proyecto implementa un sistema concurrente inspirado en el *Bar de Moe*, donde un **Camarero** sirve y recoge vasos de cerveza y varios **Clientes** beben indefinidamente siguiendo un modelo de **productor–consumidor** usando hilos en Java.

Todo el desarrollo cumple los requisitos funcionales FR1–FR5 establecidos en el examen.

---

## 📘 FR1 — Clase `VasoCerveza`

### ✔ Descripción
Representa el objeto que será preparado por el Camarero y consumido por los Clientes.  
Cada vaso incorpora datos de identificación y tipo.

### ✔ Atributos
| Atributo | Tipo | Descripción |
|---------|------|--------------|
| `id`   | `int` | Identificador único del vaso (se incrementa automáticamente). |
| `tipo` | `int` | `0 = Media pinta`, `1 = Pinta`. |
| `litros` | `double` | Cantidad de cerveza en litros (0.25L o 0.5L). |

### ✔ Métodos
- **Constructor**: genera un id y asigna un tipo aleatorio (0 o 1).
- **Getters y setters**.
- **toString()**: representación textual completa del vaso.

---

## 🍻 FR2 — Clase `Camarero`

### ✔ Descripción
Simula a “Mou”, el propietario del bar.  
Es responsable de almacenar vasos, servirlos a los clientes y recibirlos de vuelta.

### ✔ Atributos
| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `listaVasos` | `List<VasoCerveza>` | Almacén de vasos disponibles en el bar. |
| `nombre` | `String` | Nombre del camarero (ej. “Mou”). |
| `totalConsumidos` | `int` | Contador global de vasos consumidos. |

### ✔ Comportamiento del Constructor
- Recibe el nombre del camarero.
- Inicializa la lista.
- Crea **3 vasos aleatorios** y los añade a `listaVasos`.

### ✔ Métodos
#### **1. servirCerveza(String cliente)**
- Método sincronizado.
- Si no hay vasos disponibles → el cliente **espera** (`wait()`).
- Selecciona un vaso aleatorio y lo retira de la lista.
- Devuelve el vaso al cliente.

#### **2. devolverCerveza(VasoCerveza vaso, String cliente)**
- Método sincronizado.
- Recibe un vaso de un cliente y lo añade a la lista.
- Usa `notifyAll()` para despertar a los hilos que esperan.

#### **3. contarVasos()**
- Imprime cuántos vasos hay disponibles.
- Muestra detalle de cada vaso.

#### **4. incrementarTotalConsumidos(double litros)**
- Aumenta el contador global de vasos consumidos.
- Imprime información del estado.

---

## 👥 FR3 — Clase `HilosClientes` (extiende `Thread`)

Cada cliente se ejecuta como un hilo independiente.  
Simula a los clientes de Moe: Homer, Barney, Carl, Lenny y Lurleen.

### ✔ Comportamiento (run)

Cada cliente debe:

1. Indicar que comienza su ejecución.
2. En un **bucle infinito controlado por interrupción**:
    - Pedir un vaso al camarero.
    - “Beber” (simulación con `sleep()`).
    - Contabilizar la cantidad de litros consumidos.
    - Devolver el vaso al camarero.
    - Dormir entre **250 y 1000 ms** antes de pedir otro vaso.

### ✔ Atributos propios del cliente
| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `litrosConsumidos` | `double` | Total de litros bebidos por el cliente. |
| `vasosConsumidos` | `int` | Cantidad de vasos bebidos. |

---

## 🏁 FR4 — Clase `ua2ex1` (main)

El método `main`:

1. Crea al **Camarero Mou**.
2. Crea los clientes:
    - Homer
    - Barney
    - Carl
    - Lenny
    - Lurleen
3. Inicia los hilos de los clientes.
4. Ejecuta el sistema durante un periodo de prueba (p. ej. 20 segundos).
5. Interrumpe los hilos de forma controlada.
6. Espera a que terminen (`join()`).
7. Imprime:
    - Vasos restantes en el bar.
    - Total global de vasos consumidos.

---

## ⚙️ FR5 — Características adicionales requeridas

### ✔ Código en un único fichero
El archivo **ua2ex1.java** contiene todas las clases:

- `VasoCerveza`
- `Camarero`
- `HilosClientes`
- `ua2ex1` (con main)

### ✔ Todos los métodos imprimen información del estado
Cada operación del camarero y cada acción del cliente se registra por consola:

- Servir vasos
- Esperar por vasos
- Beber
- Devolver vasos
- Contadores
- Errores controlados

### ✔ Gestión de errores
Se usan bloques `try/catch` en:
- Accesos sincronizados
- Regiones de `wait()` / `notifyAll()`
- Pausas (`sleep`)
- Devolución y consumo de vasos

---

## 🧩 Resumen técnico del sistema

Este proyecto es una **implementación práctica del patrón Productor–Consumidor**:

- El **Camarero** produce y gestiona recursos (vasos).
- Los **Clientes (hilos)** consumen recursos.
- Se sincroniza todo mediante:
    - `synchronized`
    - `wait()`
    - `notifyAll()`

La simulación demuestra correctamente la interacción concurrente entre múltiples hilos en un entorno de recursos compartidos.

---

## 📦 Estructura recomendada del repositorio

UA2/
└── examen/
└── dev_10/
└── ua2ex1.java
└── README.md ← este archivo