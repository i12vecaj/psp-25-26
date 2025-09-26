<div align="center">

# **Actividad: Explorando procesos con ProcessBuilder**

</div>

<div align="justify">

## Objetivo

Aprender a usar la clase ProcessBuilder en Java para crear y gestionar procesos externos, leer su salida, redirigirla y controlar posibles errores.

## Parte 1 – Ejecutar un proceso simple

- Crea un programa en Java llamado EjecutaComando.java.
- Utiliza ProcessBuilder para ejecutar el comando ping a una dirección web (ejemplo:
  google.com).
- Captura la salida estándar y muéstrala en consola.

## Parte 2 – Manejar errores

- Modifica el programa para que intente hacer ping a una dirección inexistente (ejemplo:
  direcccionInexistente.test).
- Redirige la salida de error al flujo estándar y comprueba lo que ocurre.

## Parte 3 – Redirigir salida a un fichero

- Cambia el programa para que la salida del comando ping se guarde en un archivo de texto
  llamado salida.txt.
- Comprueba después que el archivo contiene la información esperada.

## Parte 4 – Encadenar procesos

- Crea un programa que ejecute dos comandos de forma secuencial con ProcessBuilder:
- Primero un comando que liste los archivos del directorio (ls en Linux/Mac, cmd /c dir en
  Windows).
- Luego, un comando que muestre el contenido de ese fichero de salida.
- Reflexiona: ¿cómo controlarías que el segundo comando solo se ejecute si el primero ha
  tenido exit code 0?

## Parte 5 – Reto adicional (para quien termine antes)

- Pide al usuario que introduzca un comando por consola.
- Ejecútalo usando ProcessBuilder y muestra el resultado o error.
- Analiza: ¿qué riesgos tiene permitir ejecutar cualquier comando del sistema?

## Entrega

- Crea un paquete con tu nombre dentro del repositorio.
- Añade tu código Java con las soluciones de cada parte.
- Sube los cambios a tu rama correspondiente (dev_X).

## Duración estimada

60–80 minutos

- 10 min → explicación de ProcessBuilder y ejemplo inicial.
- 35–45 min → desarrollo de las partes 1 a 4.
- 10–15 min → reflexión + reto opcional.

</div>
