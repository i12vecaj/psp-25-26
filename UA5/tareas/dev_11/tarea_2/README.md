<div align="center">

# UA5 – Tarea 2 – Generando llaves públicas y privadas en Java

</div>

#### _Criterios a), b), c), d), e), f), g) y h)_

Se ha realizado la práctica siguiendo el manual indicado para generar un par de claves pública/privada en Java y guardarlas en disco para poder reutilizarlas en los ejemplos de la unidad.

<div align="center">

<img src="https://i.imgur.com/ScL7NNH.png" alt="Ejecución"  />
<img src="https://i.imgur.com/aOWCDMB.png" alt="Resultado"  />

</div>

## Descripción de lo realizado

Carpeta de trabajo: `UA5/tareas/dev_11/tarea_2`

- Se ha creado el programa `GeneradorClavesRSA.java`.
- El programa genera un par de claves con algoritmo `RSA` y tamaño `2048` bits.
- Se guardan automáticamente en la carpeta `keys/`:
  - `public.key` (clave pública en binario)
  - `private.key` (clave privada en binario)
  - `public_base64.txt` (clave pública en Base64)
  - `private_base64.txt` (clave privada en Base64)

## Código principal

Archivo: `GeneradorClavesRSA.java`

Pasos que realiza:

1. Crea un `KeyPairGenerator` para RSA.
2. Inicializa el generador a 2048 bits.
3. Genera el `KeyPair` (pública y privada).
4. Guarda ambas claves en formato binario.
5. Guarda también una versión en Base64 para facilitar su uso en otros ejemplos.

## Compilación y ejecución

Desde la carpeta `UA5/tareas/dev_11/tarea_2`:

```bash
javac GeneradorClavesRSA.java
java GeneradorClavesRSA
```

## Resultado obtenido

Tras ejecutar el programa, se han generado correctamente los siguientes ficheros:

- `keys/public.key`
- `keys/private.key`
- `keys/public_base64.txt`
- `keys/private_base64.txt`

Salida de consola esperada (similar):

```text
Par de claves RSA generado correctamente.
Clave pública (binario): .../keys/public.key
Clave privada (binario): .../keys/private.key
Clave pública (Base64): .../keys/public_base64.txt
Clave privada (Base64): .../keys/private_base64.txt
```

## Referencia

Manual seguido:

- [Generating Public and Private Keys in Java](https://ryctabo.wordpress.com/2018/02/04/generating-public-and-private-keys-in-java/)

## IMPORTANTE

Las claves generadas en `keys/` (especialmente la clave privada) **no deben subirse al repositorio**.

Para evitar subidas accidentales, se ha añadido una regla en `.gitignore` que excluye:

- `UA5/tareas/dev_11/tarea_2/keys/`

Esto permite generar y usar las claves en local para las prácticas, manteniéndolas fuera del control de versiones.
