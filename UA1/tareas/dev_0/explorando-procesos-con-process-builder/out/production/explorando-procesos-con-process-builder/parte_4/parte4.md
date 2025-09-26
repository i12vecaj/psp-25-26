<div align="center">

# **Parte 4 – Encadenar procesos**

</div>

<div align="justify">

- Crea un programa que ejecute dos comandos de forma secuencial con ProcessBuilder:

- Primero un comando que liste los archivos del directorio (ls en Linux/Mac, cmd /c dir en
  Windows).

- Luego, un comando que muestre el contenido de ese fichero de salida.

- Reflexiona: ¿cómo controlarías que el segundo comando solo se ejecute si el primero ha
  tenido exit code 0?

</div>

<div align="center">

<img src="https://i.imgur.com/xPJsUNV.png" alt="Captura de la salida del comando"  />

</div>

## _Reflexión:_

Para que el segundo comando solo se ejecute si el primero fue exitoso, he usado:

```sh
if (exitCode1 == 0) {
    // Ejecutar segundo comando
}
```

Esto asegura que:

- Si el primer comando falla (por ejemplo, si `ls` no existe o da error), el segundo no se ejecuta.

- Es una buena práctica en la gestión de procesos, para evitar ejecutar comandos innecesarios o dependientes.

## _Otras notas:_

Si lo hiciese en Windows (por ejemplo, desde CMD), se podría cambiar:

```sh
new ProcessBuilder("cmd.exe", "/c", "dir")
```

y para mostrar el archivo:

```sh
new ProcessBuilder("cmd.exe", "/c", "type salida.txt")
```
