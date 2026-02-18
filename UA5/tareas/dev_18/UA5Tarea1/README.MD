# UA5 - Tarea 1

## Descripción
He añadido un endpoint GET para generar un hash MD5 a partir de un texto recibido por parámetro.

## Endpoint

GET /api/hash

Parámetros:
- algorithm=md5
- text=texto a convertir

## Ejemplo

http://localhost:8080/api/hash?algorithm=md5&text=Hola

## Ejecución

1. Ejecutar la clase api.java
2. Acceder desde el navegador a la URL anterior
