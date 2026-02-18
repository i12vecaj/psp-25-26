# UA4 API - Hash MD5

API REST en Node.js + Express para generar hash MD5 mediante método GET.

## Requisitos
- Node.js 18+ (o compatible)

## Instalación
```bash
npm install
```

## Ejecución
```bash
npm start
```

Servidor por defecto:
- `http://localhost:3000`

## Endpoint
- **GET** `/api/hash`

### Query params
- `algorithm`: debe ser `md5`
- `text`: texto a hashear

### Ejemplo de llamada
`http://localhost:3000/api/hash?algorithm=md5&text=Generando%20un%20MD5%20de%20un%20texto`

### Respuesta esperada (200)
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
    "response_time": 123456
  },
  "body": {
    "algorithm": "md5",
    "text": "Generando un MD5 de un texto",
    "hash": "5df9f63916ebf8528697b629022993e8"
  }
}
```

## Errores controlados
- `400` si falta `algorithm`
- `400` si `algorithm` no es `md5`
- `400` si falta `text`
