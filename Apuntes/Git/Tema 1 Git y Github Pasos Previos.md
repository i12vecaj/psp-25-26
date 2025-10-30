# 📘 Pasos Previos

📅 Fecha: 2025-09-22
📚 Asignatura: 
🏷️ Etiquetas: #2dam  #git 

---
 Vamos a ver los pasos previos para configurar nuestro ordenador para tener git configurado 
## 🧠 Conceptos clave

-  Configuracion de nuestra terminal para Git

## 💡 Pasos a Seguir

1. Creamos una carpeta ssh  

```bash
mkdir -p ~/-ssh
chmod 700 ~/.ssh
```
	 Usamos chmod para cambiar los permisos de nuestra caperta .ssh al usar 700 solo tiene permisos de edición el propietario
 2. Ahora creamos nuestra clave ssh
 ```bash
 ssh-keygen -t ed25519 -C "tucorreo@correo.com" -f ~/.shh/id_ed25519
 ```
	Asi creamos nuestra clave ssh ademas creamos otra clave publica 
3. Ahora iniciamos el agente y cargamos la clave
```bash
eval "$(ssh-agent-s)"
ssh-add ~/.ssh/id_ed25519
```
	Nos pide la passphras (solo una vez por sesión)
4. Vamos a configurar ~/ssh/config par GitHub
```bash
cat > ~/.ssh/config << 'EOF'
Host github.com
HostName github.com
User  git
IdentityFile ~/.ssh/id_ed25519
IdentitiesOnly yes
EOF
chmod 600 ~/.ssh/config
```
5. Copiamos la clave pública y la añadimos en Github
```bash
cat ~/.ssh/id_ed25519.pub
```
	Copia todo lo quer sale (empieza por ``ssh-ed25519``)
	Ve a Github -> Setting -> SSH and GPG Keys -> New SSH key -> pega y guarda

6. Una vez hecho todos esos pasos, vamos a probar que esta bien y probamos nuestra conexión
```bash
ssh -T git@github.com
```
	La primera vez nos pedira confirmar la hueca -> escribimos yes
	Si todo esta bien nos saludara y nos dara todo correcto



---
## 🧠 ¿Qué escribir en “Notas adicionales”?

Algunas ideas:
- Dudas que tienes que preguntar al profe
- Cosas que no entendiste bien
- Recordatorios para revisar más tarde
- Ideas para ampliarlo en un resumen

---

💡 Si en algún momento no necesitas esa sección, simplemente la dejas vacía o la borras al escribir la nota.

¿Quieres que prepare ahora una **segunda plantilla** para “resumen pre-examen” más concisa o tipo esquema rápido?
