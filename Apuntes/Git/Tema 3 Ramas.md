# 📘 Trabajar con ramas en GIT

📅 Fecha: 2025-09-23
📚 Asignatura:  
🏷️ Etiquetas: #2dam #git

---
Vamos a explicar como trabajar con ramas en git y la forma de hacerlo
## 🧠 Conceptos clave

- Clonar un repo
- Crear nuestra propia rama y trabajar con ella
- Subir a **GIT** nuestra rama

### Comandos para trabajar con ramas en **GIT**

1. Clonamos nuestro repositorio en local
```bash
git clone git@github.com:TU_USUARIO/TU_REPO.git
```

Si usamos WSL,  la forma mas fácil es usar *URL SSH* ya que evitamos problemas de autentificación.

2. Entrar en el repositorio
```bash
cd proyecto_demo
```

Una vez clonado nos movemos a nuestra carpeta 

3. Crear una rama de trabajo
```bash
git checkout -b nombre_de_la_rama
```

- ``checkout -b `` -> crea la rama y te cambia a ella.

**EJEMPLO**
```bash
git checkout -b dev_bernardo
```

4. Hacer cambios en los archivos

```bash
git status
```

- En que ramas estas.
- Que archivos han cambiado
- Cuales están preparados para un commit
- Si hay archivos sin seguimiento


#### Flujo de trabajo
Vamos a poner un ejemplo de como seria el flujo de trabajo clonando un repositorio, creando nuestra rama y posteriormente subiendo nuestra rama a git
##### Pasos
1. Clonar nuestro repositorio 
```git
git clone git@github.com TU_USUARIO@TU_REPOSITORIO.GIT
```

2. Crear nuestra rama en local
```git
git checkout -b dev_bernardo
```
	Crea nuestra rama y nos cambia a ella
3. Comprobamos si se ha creado correctamente.
```git
git status
git branch 
```
📊 **Diferencia clave**:
- `git status` → para ver qué archivos has modificado y si están en staging.
- `git branch` → para ver tus ramas y en cuál estás.
4. Añadimos archivos y comenzamos la subida
```git
git add .
```
5. Hacemos commit
```git
git commit -m "Primer commit"
```
 6. Subimos nuestra rama 
 ```git
 git push -u origin dev_bernardo
 ```
	Queda ya vinculada nuestra rama gracias a usar -u 
7. Posteriores subidas
```git
git push
```
	Ya no hace falta usar -u origin dev_bernardo ya que esta vinculada la rama



