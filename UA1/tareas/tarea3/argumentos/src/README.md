# 🧮 Proyecto: Validación de Argumentos en Java

Este proyecto forma parte de la Unidad de Aprendizaje de **Procesos**.  
Su objetivo es implementar dos programas en Java que interactúan entre sí:

1. **ValidadorArgumentos.java** → analiza los argumentos y devuelve un código de salida con `System.exit()`.
2. **Lanzador.java** → ejecuta al programa anterior y muestra un mensaje en función del código devuelto.

---

## ⚙️ Funcionalidades Requeridas (FR)

| Código | Descripción                                                                 |
|:-------:|------------------------------------------------------------------------------|
| **FR1** | El programa principal admite argumentos desde `main(String[] args)`         |
| **FR2** | Devuelve códigos con `System.exit()` según las condiciones del argumento     |
| **FR3** | Un segundo programa ejecuta al primero y muestra el resultado correspondiente |

---

## 🧩 Códigos de salida

| Código | Significado                                 | Descripción detallada                                       |
|:-------:|---------------------------------------------|--------------------------------------------------------------|
| `1`     | ❌ Sin argumentos                           | No se ha proporcionado ningún argumento al programa          |
| `2`     | ⚠️ Argumento no numérico                    | El argumento no puede convertirse a número entero            |
| `3`     | 🚫 Número entero negativo                   | El argumento es un número entero menor que cero              |
| `0`     | ✅ Argumento correcto                       | El argumento es válido (entero mayor o igual que cero)       |

---

## 🧠 Estructura del proyecto
```
argumentos/
│
├── src/
│   ├── ValidadorArgumentos.java
│   └── Lanzador.java
│
└── README.md
```
## 🧮 Diagrama 1 — Flujo de ValidadorArgumentos
```
┌──────────────────────────────┐
│ ValidadorArgumentos.main()   │
└──────────────┬───────────────┘
│
▼
¿args.length < 1?
│
┌────┴────┐
│Sí       │No
▼          ▼
exit(1)   intentar parseInt()
│
┌──────┴────────┐
│Excepción?      │
▼                ▼
exit(2)        número < 0 ?
│
┌──────┴───────┐
│Sí            │No
▼              ▼
exit(3)         exit(0)
```

## ⚙️ Diagrama 2 — Flujo de Lanzador
```
┌───────────────────────────────┐
│ Lanzador.main(args)           │
└───────────────┬───────────────┘
│
▼
recibe args desde IntelliJ
│
▼
java -cp <classpath> ValidadorArgumentos [arg?]
│
▼
espera a que termine el proceso hijo
│
▼
recoge código de salida (exitValue)
│
▼
switch(exitValue):
│
├─ 1 → No se pasó argumento
├─ 2 → Argumento no es entero
├─ 3 → Número negativo
└─ 0 → Argumento correcto
```
## 🧰 Instrucciones de ejecución (IntelliJ)

Crea un nuevo proyecto sin package.

Añade los archivos:

ValidadorArgumentos.java

Lanzador.java

Compila el proyecto con Build ▸ Build Project.

Ejecuta Lanzador:

En el campo Program arguments (arriba a la derecha ▸ Edit Configurations…):

(vacío) → muestra Error: No se ha proporcionado ningún argumento

hola → muestra Error: El argumento no es un número entero

-5 → muestra Error: El número proporcionado es negativo

7 → muestra El argumento es correcto
## 💻 Ejecución desde terminal
```bash
# Compilar
javac ValidadorArgumentos.java Lanzador.java

# Ejecutar pruebas
java Lanzador
java Lanzador hola
java Lanzador -5
java Lanzador 7
```
## 🧾 Ejemplo de salida esperada
```
[Lanzador] args recibidos (1): [hola]
[Lanzador] Comando: java -cp C:\...\argumentos\out\production\argumentos ValidadorArgumentos hola
⚠️ El argumento no es un número entero
```
## 🧩 Esquema general de comunicación entre procesos
```
┌──────────────────────────────┐        ┌───────────────────────────────┐
│        Lanzador.java         │        │    ValidadorArgumentos.java   │
│                              │        │                               │
│  recibe argumento (args)     │        │  valida argumento             │
│  ─────────────────────────▶  │        │  devuelve exit(1,2,3,0)       │
│                              │ ◀──────┘                               │
│  muestra mensaje por pantalla │
└──────────────────────────────┘

```
