# 🧩 Tarea 2 – Procesos en Java

Este proyecto forma parte de la Unidad de Aprendizaje 1 (PSP) y tiene como objetivo **trabajar con procesos en Java**.  
Se implementan tres funcionalidades requeridas (FR1, FR2 y FR3) que muestran cómo **un programa puede ejecutar otro proceso Java independiente** usando la clase `ProcessBuilder`.

---

## 🚀 Estructura del Proyecto

Tarea2Procesos/
└── src/
└── procesos/
├── LectorTexto.java
└── Lanzador.java

---

## ⚙️ Funcionalidades Requeridas

| Código | Descripción | Implementación |
|:------:|:-------------|:----------------|
| **FR1** | Leer texto desde la entrada estándar hasta que el usuario introduzca un `*` | `LectorTexto.java` |
| **FR2** | Mostrar en pantalla todo el texto introducido antes del `*` | `LectorTexto.java` |
| **FR3** | Crear un segundo programa que ejecute el anterior como proceso independiente | `Lanzador.java` |

Además, se incluye **control de errores y documentación** mediante comentarios y JavaDoc.

---

## 💻 Ejecución en IntelliJ IDEA

1. Crear un **proyecto Java** y un **paquete llamado `procesos`** dentro de `src`.
2. Copiar los archivos `LectorTexto.java` y `Lanzador.java` en el paquete.
3. Compilar el proyecto (**Build → Build Project**).
4. Ejecutar la clase `Lanzador`.

El programa lanzará un proceso independiente que ejecutará `LectorTexto`.

---

## 🧠 Descripción de los Programas

### 🟢 `LectorTexto.java`

- Lee texto desde teclado línea por línea.
- Finaliza la lectura cuando detecta el carácter `*`.
- Muestra todo el texto escrito antes del asterisco.

Ejemplo de uso:
Introduce texto (finaliza al llegar a ''):
Hola mundo
Esto es una prueba
── Texto introducido ──
Hola mundo
Esto es una prueba
───────────────────────

### 🟣 `Lanzador.java`

- Utiliza `ProcessBuilder` para ejecutar el programa `LectorTexto` como proceso hijo.
- Hereda la entrada/salida del proceso padre (`pb.inheritIO()`), de modo que se usa la misma consola.
- Espera a que el proceso termine y muestra su código de salida.

Salida esperada:

---

## 🧩 Tecnologías Utilizadas

- Lenguaje: **Java 21+**
- Entorno: **IntelliJ IDEA 2025.1.2**
- JDK: **OpenJDK 24.0.1**
- Clase principal de ejecución: `procesos.Lanzador`

---

## 🔍 Control de Errores

Ambos programas implementan `try-catch` para:
- Errores de lectura (`IOException`)
- Interrupciones del proceso (`InterruptedException`)
- Mensajes claros en caso de fallo

---

## ✍️ Autor

**Bernardo Cubero Martínez**  
📚 Ciclo Formativo: Desarrollo de Aplicaciones Multiplataforma (DAM)  
📘 Módulo: Programación de Servicios y Procesos (PSP)  
📅 Curso: 2025/2026
