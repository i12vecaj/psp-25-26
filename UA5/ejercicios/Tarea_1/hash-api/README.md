# Hash API – README
## Descripción del proyecto

Hash API es una pequeña API desarrollada con Spring Boot cuyo objetivo es generar el hash MD5 de un texto recibido mediante una petición HTTP GET.
El proyecto forma parte de las prácticas de la asignatura y sigue la estructura solicitada por el profesor, incluyendo un header y un body en la respuesta JSON.

## Tecnologías utilizadas
- Java 17
- Spring Boot
- Spring Web
- Maven
- Postman(pruebas)

## Estructura del proyecto
src/main/java/com/marissa/hash_api/
│
├── controller/
│   └── HashController.java
│
├── models/
│   ├── ApiHeader.java
│   ├── ApiBody.java
│   └── ApiResponse.java
│
├── utils/
│   ├── HashUtils.java
│   └── KeyGeneratorUtil.java
│
└── HashApiApplication.java




## Funcionamiento del endpoint MD5

GET /api/hash

## Parámetros
| Nombre | Tipo | Descripción |
|--------------|--------------|--------------|
| algorithm       | String      | Solo se acepta "md5" (valor por defecto)       |
| text       | String      | Texto del que se generará el hash MD5       |

## Ejemplo de petición

http://localhost:8080/api/hash?algorithm=md5&text=Generando


## Respuesta de JSON:
{
  "header": {
    "api_name": "My API Name",
    "api_version": "1.0.0",
    "api_user": "guest",
    "api_endpoint": "api/hash/",
    "http_request_method": "GET",
    "http_request_parameters": {
      "algorithm": "md5",
      "text": "Hola"
    },
    "http_response_status": 200,
    "http_response_message": "OK",
    "response_time": 12
  },
  "body": {
    "algorithm": "md5",
    "text": "Hola",
    "hash": "4d186321c1a7f0f354b297e8914ab240"
  }
}

