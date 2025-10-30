# 📘 Comandos Básicos

📅 Fecha: 2025-09-22
📚 Asignatura: 
🏷️ Etiquetas: #2dam #git

---

## 🧠 Conceptos clave

- Iniciar Git y subir a nuestro repositorio
- Creación de Ramas

### Comandos Básicos

Antes de todo tenemos que crear nuestra carpeta en local, para si posteriormente tener ahí nuestro archivos y poder subirlos a Git

1. Iniciamos nuestro repo
```bash
git init
```

Con este comando iniciamos nuestro repositorio para subir los archivos a git
2. Agregamos a git nuestros archivos
```bash
git add.
```

Si usamos ``add . `` indicamos que suba todas las modificaciones que hayamos realizado, si por lo contrario solo ponemos el archivo que queremos subir no podemos usar ``add . `` si no poner a continuación el nombre del archivo

3. Hacer commit 
```bash
git commit - m "Comentario "
```

Este comando nos sirve para añadir un comentario a nuestra subida y de estar forma poner de que se trata, si es una modificación,  etc.

4. Conectar con un repositorio remoto en **Github**
```git
git remote add origin git@github.como:"TU_USUARIO/TU_REPO.GIT
```

Previamente deberíamos haber creado nuestro repositorio en **GIT** vacío  y esta es la forma de conectar nuestro equipo local con el remoto

5. Subir tu código al remoto
```bash
git push -u origin main
```
 - -u hace que quede vinculada la rama local *main* con la remota *main*, así después solo necesitaras ``git push o git pull

## 💡 Ejemplos

```java
// Código o ejemplo
```

---

## 🧠 ¿Qué escribir en “Notas adicionales”?

Algunas ideas:
- Dudas que tienes que preguntar al profe
- Cosas que no entendiste bien
- Recordatorios para revisar más tarde
- Ideas para ampliarlo en un resumen

---
