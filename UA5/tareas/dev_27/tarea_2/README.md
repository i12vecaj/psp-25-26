# UA5 - Tarea 2 - Generando llaves públicas y privadas en Java

## Objetivo

Generar un par de claves pública y privada en Java y guardarlas en disco para reutilizarlas en ejemplos de criptografía.

## Conceptos clave

- Criptografía asimétrica: usa dos claves distintas (pública y privada).
- Algoritmo RSA: permite cifrado y firma digital.
- `KeyPairGenerator`: genera el par de claves.
- Formatos típicos en Java:
  - Clave pública: `X.509`
  - Clave privada: `PKCS#8`
- Guardado de claves:
  - Texto PEM (Base64 con cabecera y pie)
  - Binario DER (bytes crudos)

## Archivos de la tarea

- `GeneradorClavesRSA.java`: genera y guarda claves RSA.
- Carpeta de salida por defecto: `keys/`
  - `public_key.pem`
  - `private_key.pem`
  - `public_key.der`
  - `private_key.der`

## Compilar y ejecutar

Desde esta carpeta:

```powershell
javac GeneradorClavesRSA.java
java GeneradorClavesRSA
```

## Opciones de ejecución

```powershell
java GeneradorClavesRSA <carpeta_salida> <tamano_bits>
```

Ejemplo:

```powershell
java GeneradorClavesRSA claves_4096 4096
```

## Resultado esperado

Al ejecutar correctamente, el programa muestra:

- Confirmación de generación del par.
- Rutas de los archivos creados.
- Formato de la clave pública (`X.509`).
- Formato de la clave privada (`PKCS#8`).

## Nota práctica

Estas claves son de práctica para laboratorio y ejemplos docentes.
No se deben reutilizar en entornos reales de producción.
