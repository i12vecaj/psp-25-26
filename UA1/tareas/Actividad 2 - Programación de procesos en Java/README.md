Este proyecto contiene dos clases principales en Java:

- **App.java** Programa que pide al usuario introducir texto hasta que escriba `*`.  
- **Launcher.java** Se encarga de ejecutar el programa `App` como un subproceso usando `ProcessBuilder`.

---

## Estructura 
TAREA2/
│── src/
│ ├── App.java
│ └── Launcher.java
│── bin/ (salida de compilación .class)
│── README.md

---

## Funcionamiento

### `App.java`
- Solicita texto al usuario.
- Finaliza cuando se introduce `*`.
- Muestra en pantalla todo lo que se escribió.

### `Launcher.java`
- Lanza `App` con **ProcessBuilder** (`java -cp bin App`).
- Hereda la entrada/salida estándar (`pb.inheritIO()`).
- Espera a que el proceso hijo finalice y muestra el código de salida.

---



```mermaid
Copiar código
flowchart TD
    A[Launcher.java] -->|ProcessBuilder| B[Ejecuta App.java]
    B --> C[Usuario escribe texto]
    C --> D{¿Caracter = * ?}
    D -- Sí --> E[Finaliza entrada]
    D -- No --> C
    E --> F[Muestra el texto escrito]
    F --> G[Devuelve exitCode]
    G --> H[Launcher imprime código de salida]
```