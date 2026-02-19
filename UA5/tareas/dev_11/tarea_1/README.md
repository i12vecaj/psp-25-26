<div align="center">

# UA5 – Tarea 1 – API Añadir hash MD5

</div>

#### _Criterios a), b), c), d), e), f), g) y h)_

Se ha reutilizado el framework REST sin framework de UA4 y se ha implementado un nuevo endpoint para calcular hashes MD5.

<div align="center">

<img src="https://i.imgur.com/trx16xu.png" alt="Captura de la salida del funcionamiento"  />

</div>

## Descripción de lo realizado

Carpeta de trabajo: `UA5/tareas/dev_11/tarea_1`

- Se ha copiado la base de la API (clases `api`, `BasicHandler`, `DataStore`, `Person`, `PersonHandler`).
- Se ha añadido la clase `HashHandler.java`.
- Se ha registrado el endpoint en `api.java`:
  - `GET /api/hash`
- El endpoint recibe parámetros por query string:
  - `algorithm` (esperado: `md5`)
  - `text` (texto a hashear)

## Funcionamiento del endpoint

Ejemplo de petición:

- `http://localhost:8080/api/hash?algorithm=md5&text=Generando+un+MD5+de+un+texto`

Ejemplo de respuesta correcta:

```json
{
  "header": {
    "api_name": "My API Name",
    "api_version": "1.0.0",
    "api_user": "guest",
    "api_endpoint": "api/hash/",
    "http_request_method": "GET",
    "http_request_parameters": {
      "algorithm": "md5",
      "text": "Generando un MD5 de un texto"
    },
    "http_response_status": 200,
    "http_response_message": "OK",
    "response_time": 123456789
  },
  "body": {
    "algorithm": "md5",
    "text": "Generando un MD5 de un texto",
    "hash": "5df9f63916ebf8528697b629022993e8"
  }
}
```

## Compilación y ejecución

Desde la carpeta `UA5/tareas/dev_11/tarea_1`:

```bash
javac *.java
java api
```

Prueba rápida con `curl` en otra terminal:

```bash
curl "http://localhost:8080/api/hash?algorithm=md5&text=Generando+un+MD5+de+un+texto"
```

## Comprobaciones añadidas

- Si falta `text`, devuelve `400 Bad Request`.
- Si `algorithm` no es `md5`, devuelve `400 Bad Request`.
- Si el método HTTP no es `GET`, devuelve `405 Method Not Allowed`.
