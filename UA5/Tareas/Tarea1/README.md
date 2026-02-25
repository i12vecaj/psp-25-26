# Tarea 1 - API con Hash MD5

Esta implementación añade el endpoint `GET /api/hash` a la API base de UA4.

## Archivos

- `api.java`: arranque del servidor HTTP y registro de endpoints.
- `BasicHandler.java`: utilidades comunes (parseo de query y respuesta JSON).
- `HashHandler.java`: lógica de `GET /api/hash` para calcular hash MD5.

## Ejecución

Desde `UA5/Tareas/Tarea1`:

```bash
javac *.java
java api
```

Servidor por defecto: `http://localhost:8080`

## Petición de ejemplo

```bash
curl "http://localhost:8080/api/hash/?algorithm=md5&text=Generando%20un%20MD5%20de%20un%20texto"
```

## Respuesta de ejemplo

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
