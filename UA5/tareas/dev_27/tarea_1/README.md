# UA5 - Tarea 1 - API Añadir hash MD5

## Objetivo de la práctica

Implementar en una API Java (sin framework) un endpoint `GET` que reciba un texto y devuelva su hash MD5 en formato JSON con dos bloques: `header` y `body`.

---

## Conceptos clave para examen

### 1) API REST básica en Java

- Se usa `HttpServer` de `com.sun.net.httpserver`.
- Se registran contextos (rutas) con `server.createContext(...)`.
- En esta tarea se aceptan ambas rutas:
	- `/api/hash`
	- `/api/hash/`

### 2) Método HTTP GET

- El endpoint solo admite `GET`.
- Si llega otro método (`POST`, `PUT`, etc.), se responde `405 Method Not Allowed`.

### 3) Parámetros de consulta (query params)

- Van en la URL después de `?`.
- Parámetros usados:
	- `algorithm` (en esta tarea solo `md5`)
	- `text` (texto a calcular)
- Ejemplo:
	- `?algorithm=md5&text=hola`

### 4) URL encoding

- Los espacios y caracteres especiales en URL deben codificarse.
- Ejemplo: `Generando un MD5` → `Generando%20un%20MD5`.
- En el código, los parámetros se decodifican con `URLDecoder` usando `UTF-8`.

### 5) Función hash

- Una función hash transforma una entrada en una salida de longitud fija.
- Para la misma entrada, siempre da la misma salida.
- No está pensada para “descifrar” el dato original.

### 6) MD5

- Produce 128 bits (32 caracteres hexadecimales).
- En Java se calcula con:
	- `MessageDigest.getInstance("MD5")`
	- `digest(...)`
	- conversión a hexadecimal con `%02x`

### 7) Estructura de respuesta JSON

- `header`: metadatos de la petición/respuesta.
- `body`: resultado funcional (`algorithm`, `text`, `hash`).
- Campos relevantes de `header`:
	- `http_request_method`
	- `http_request_parameters`
	- `http_response_status`
	- `http_response_message`
	- `response_time`

### 8) Códigos de estado usados

- `200 OK`: petición correcta.
- `400 Bad Request`: algoritmo no válido (distinto de `md5`).
- `405 Method Not Allowed`: método distinto de `GET`.

### 9) Tiempo de respuesta

- Se mide con `System.nanoTime()`.
- Se devuelve en `header.response_time`.

### 10) Escape de JSON

- Para construir JSON manualmente se escapan caracteres especiales (`\"`, `\\`, saltos de línea, etc.).

---

## Estructura de archivos

- `api.java`: arranque del servidor y registro de rutas.
- `BasicHandler.java`: parseo y decodificación de query params.
- `HashHandler.java`: validación de método/parámetros, cálculo MD5 y construcción de respuesta JSON.

---

## Compilar y ejecutar

Desde esta carpeta:

```powershell
javac *.java
java api
```

Servidor por defecto:

- `http://localhost:8080`

---

## Endpoints de la práctica

- `GET /api/hash?algorithm=md5&text=...`
- `GET /api/hash/?algorithm=md5&text=...`

Ambos son válidos.

---

## Pruebas rápidas

### 1) Caso correcto

```powershell
curl "http://localhost:8080/api/hash?algorithm=md5&text=Generando%20un%20MD5%20de%20un%20texto"
```

Hash esperado:

- `5df9f63916ebf8528697b629022993e8`

### 2) Mismo endpoint con barra final

```powershell
curl "http://localhost:8080/api/hash/?algorithm=md5&text=hola"
```

### 3) Error por algoritmo no soportado

```powershell
curl "http://localhost:8080/api/hash?algorithm=sha256&text=hola"
```

Debe responder `400` con mensaje de error.

---

## Preguntas típicas de examen

1. Diferencia entre `GET` y otros métodos HTTP en este endpoint.
2. Qué es una función hash y por qué MD5 siempre da 32 caracteres hex.
3. Por qué hace falta codificar parámetros en URL (`%20`, etc.).
4. Qué información aporta el bloque `header` frente al bloque `body`.
5. Cuándo devolver `200`, `400` y `405`.
6. Qué hace `MessageDigest` y cómo se convierte el resultado a hexadecimal.

---

## Nota de seguridad (teórica)

MD5 se usa aquí con fines didácticos. Para integridad o seguridad moderna se recomienda SHA-256 o superior.
