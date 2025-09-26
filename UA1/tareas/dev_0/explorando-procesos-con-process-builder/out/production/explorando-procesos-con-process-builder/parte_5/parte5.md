<div align="center">

# **Parte 5 – Reto adicional (para quien termine antes)**

</div>

<div align="justify">

- Pide al usuario que introduzca un comando por consola.

- Ejecútalo usando ProcessBuilder y muestra el resultado o error.

- Analiza: ¿qué riesgos tiene permitir ejecutar cualquier comando del sistema?

</div>

<div align="center">

## _Pruebas con varios comandos:_

### ✅ Comando `ls -l`:

<img src="https://i.imgur.com/td3RKoq.png" alt="Comando ls -l"  />

### ✅ Comando `whoami`:

<img src="https://i.imgur.com/EXRd8yx.png" alt="Comando whoami"  />

### ✅ Comando `cat /etc/os-release`:

<img src="https://i.imgur.com/N3snCBF.png" alt="Comando cat /etc/os-release"  />

### ❌ Comando `ejecutoUnComandoInvalido`:

<img src="https://i.imgur.com/ElDFI1b.png" alt="Comando ejecutoUnComandoInvalido"  />

</div>

## _Reflexión: Riesgos de permitir ejecutar cualquier comando_

Permitir que el usuario escriba y ejecute cualquier comando del sistema representa graves riesgos de seguridad, especialmente si esto se hace en una aplicación real o con permisos elevados.

_**Principales riesgos:**_

1. **Inyección de comandos**. Si el usuario escribe algo como `rm -rf /`, puede borrar todo el sistema (si tiene permisos).
2. **Ejecución de malware o scripts peligrosos**. Pueden ejecutarse scripts descargados o creados por el usuario para dañar el sistema o robar datos.

3. **Escalada de privilegios**. Si se ejecutan comandos con `sudo` o se abusa del entorno, puede haber acceso a recursos críticos.

4. **Fugas de información**. Comandos como `cat /etc/passwd`, `env`, `ifconfig`, `history` pueden mostrar información sensible.

_**Solución en entornos reales**_

- Nunca permitas ejecutar comandos sin validar.

- Restringe los comandos permitidos a una lista segura (lista blanca).

- Usa sandboxes o entornos virtuales si es necesario.

- Escapa caracteres peligrosos si se va a ejecutar cualquier input.
