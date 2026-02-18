# Tarea 2 - Generando llaves publicas y privadas en Java

## Objetivo
Generar un par de claves (publica y privada) en Java, guardarlas en disco y reutilizarlas en ejemplos practicos.

## Criterios cubiertos
- a) Generar el par de claves con `KeyPairGenerator`.
- b) Configurar algoritmo RSA y tamano de clave (2048 bits).
- c) Obtener `PublicKey` y `PrivateKey` desde el `KeyPair`.
- d) Guardar ambas claves en ficheros (`public.key`, `private.key`).
- e) Cargar las claves desde disco para volver a usarlas.
- f) Cifrar texto con la clave publica.
- g) Descifrar texto con la clave privada.
- h) Firmar y verificar contenido (SHA256withRSA).

## Estructura
```text
UA5-Tarea2-ClavesJava/
??? pom.xml
??? README.md
??? src/main/java/com/example/keys/
    ??? KeyPairGeneratorApp.java
    ??? KeyUtils.java
```

## Ejecucion
1. Abrir terminal en `UA5-Tarea2-ClavesJava`.
2. Compilar:
   - `mvn compile`
3. Ejecutar:
   - `mvn exec:java`

Tambien puedes usar wrapper:
- `mvnw.cmd compile`
- `mvnw.cmd exec:java`

## Salida esperada
- Se crean los archivos `public.key` y `private.key` dentro de `keys/`.
- En consola veras:
  - mensaje original
  - mensaje cifrado en Base64
  - mensaje descifrado
  - resultado de verificacion de firma (`true`)

## Nota
No subas `private.key` a repositorios publicos.
