<div align="center">

# T1 – Tarea 1 – Programación de Sockets 1

</div>

#### _Criterios c), f), g), h)_

**SOCKETS Y REDES**. Programa Java que permite introducir desde la línea de comandos una URL o dirección IP y mostrar información sobre ella. El programa se ejecuta de manera continua hasta que se introduce "localhost".

## Descripción del programa

`Archivo: PSP_UA3_Tarea1_URLInfo.java`

- El programa solicita al usuario que introduzca una IP o URL.
- Si la entrada comienza con `"http://"` o `"https://"`, se trata como URL y se muestra:
  - URL completa
  - Protocolo
  - Host
  - Puerto (o "Por defecto")
  - Ruta
  - IP asociada al host
- Si la entrada es solo un nombre de host o dirección IP, se muestra:
  - Nombre del host
  - Dirección IP
  - Si es alcanzable (`ping`) con timeout de 3000 ms
- Si se introduce una dirección no válida, se muestra un mensaje de error.
- El programa se ejecuta continuamente hasta que se introduce `"localhost"`.

## Ejecución y resultados

- Entrada probada: `www.google.es`
- Salida en la terminal:

<div align="center"> <img src="https://i.imgur.com/rk9OWBI.png" alt="Pantalla de salida del programa" />
</div>

### Conclusión

El programa reconoce correctamente URLs e IPs, muestra la información relevante y gestiona entradas no válidas.

## Ejecución continua

- El programa permite introducir varias IPs o URLs sin reiniciarse.
- El bucle termina al introducir `"localhost"`.
- Se probaron varias entradas (`www.google.es`, `127.0.0.1`, `www.github.com`) y el programa mostró correctamente la información de cada una.

<div align="center"> <img src="https://i.imgur.com/SxPnQpM.png" alt="Pantalla de ejecución continua" />

### Conclusión

La ejecución continua facilita la prueba de varias direcciones y URLs y mantiene control de errores.

</div>
