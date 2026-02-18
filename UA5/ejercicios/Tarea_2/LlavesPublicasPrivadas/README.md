# Hash API – README
## Descripción del proyecto
Esta actividad consiste en generar un par de claves criptográficas (pública y privada) utilizando un pequeño manual basado en Java.
Estas claves se guardan en archivos y podrán utilizarse en prácticas posteriores relacionadas con cifrado, firma digital o verificación.

## Pasos realizados
1. Se creó una clase Java encargada de generar un par de claves RSA de 2048 bits.

2. Al ejecutar la clase (Run), el programa genera dos archivos:

- private.key → clave privada
- public.key → clave pública

3. Los archivos generados se guardan en el directorio del proyecto y pueden moverse a:
    src/main/resources/keys/
4. Estas claves quedan listas para ser utilizadas en los ejemplos y prácticas siguientes.

## Archivos generados
- private.key
- public.key