# 🧹 Buenas prácticas: Qué **NO subir a GitHub** en un proyecto Java

Este documento sirve como guía para mantener nuestro repo **limpio, profesional y funcional**.  
Subir archivos innecesarios o generados automáticamente puede causar errores, conflictos y ocupar espacio sin sentido.

---

## 🚫 Archivos y carpetas que **NO debes subir**

### 🗂️ 1. Carpetas del IDE
Cada entorno (IntelliJ, Eclipse, NetBeans...) crea carpetas de configuración **local** que no son necesarias para otros usuarios.

| IDE | Carpeta(s) que debes ignorar |
|-----|------------------------------|
| IntelliJ IDEA | `.idea/`, `*.iml` |
| Eclipse | `.settings/`, `.classpath`, `.project` |
| NetBeans | `nbproject/private/`, `build/`, `dist/` |

> 💡 Estas carpetas dependen del entorno y del equipo. No afectan al código fuente del proyecto.

---

### 🧩 2. Carpetas de compilación o salida

Los archivos `.class` o compilados **no deben subirse nunca**.  
GitHub es para **código fuente**, no para binarios.

Ignora:

/bin/
/build/
/out/


> ✅ El código fuente se compila en el equipo local de cada estudiante, no en el repositorio.

---

### 🔑 3. Archivos sensibles o de configuración local

Nunca subas:
- Contraseñas o tokens
- Rutas absolutas de tu sistema
- Archivos con configuración personal (por ejemplo, `config.properties` con claves)

Ejemplo:

config.properties
application.properties
.env


> 🔐 Si necesitas usar configuraciones, crea un archivo de ejemplo como `config.example.properties`.

---

### 💾 4. Dependencias (JARs, librerías externas)

No subas archivos `.jar` de librerías externas dentro del proyecto.  
Usa herramientas de gestión de dependencias (como **Maven** o **Gradle**) o indica las dependencias en el README.

Ejemplo de carpetas a ignorar:

/lib/
/dependencies/


> ⚙️ Si usas Maven, las dependencias se descargan automáticamente desde el `pom.xml`.

---

### 🧪 5. Archivos temporales o del sistema

Son creados automáticamente por el sistema operativo o el editor de texto.  
Ejemplos:

.DS_Store
Thumbs.db
*.log
*.tmp


---

## ✅ Qué **sí** debes subir

| Tipo de archivo | Ejemplo | Descripción |
|------------------|----------|--------------|
| Código fuente | `/src/...` | Todo el código Java de tu proyecto |
| Archivos de configuración del proyecto | `pom.xml`, `build.gradle` | Si usas Maven o Gradle |
| README.md | este archivo | Explica tu proyecto |
| .gitignore | `.gitignore` | Para definir qué no subir |
| Documentación | `/docs/`, `/uml/` | Diagramas, manuales, apuntes |

---

## 🧠 Ejemplo de `.gitignore` recomendado para proyectos Java

```gitignore
# Compilación
/bin/
/build/
/out/

# IDEs
.idea/
*.iml
.classpath
.project
.settings/
nbproject/private/

# Archivos temporales
*.log
*.tmp
.DS_Store
Thumbs.db

# Dependencias locales
/lib/
/dependencies/

# Archivos sensibles
*.properties
*.env

💬 Recomendación final

    Antes de hacer git add ., revisa qué vas a subir con:

git status

Y asegúrate de no subir nada que no sea código fuente o configuración necesaria.
Esto demuestra profesionalidad y buenas prácticas de desarrollo.
