![lope_logo](https://www.ceslopedevega.com/wp-content/uploads/2020/03/pruebalogo.svg_.png)

# UA5 - Técnicas de programación segura

- Temporalización: **2T**
- Duración: **12 horas**
- Ponderación respecto a la asignatura: **20%**

## Resultados de Aprendizaje

En esta unidad de aprendizaje se desarrollan los siguientes resultados de aprendizaje:

- **RA5:** Protege las aplicaciones y los datos definiendo y aplicando criterios de seguridad en el acceso, almacenamiento y transmisión de la información

### Criterios de Evaluación

a) Se han identificado y aplicado principios y prácticas de programación segura.

b) Se han analizado las principales técnicas y prácticas criptográficas.

c) Se han definido e implantado políticas de seguridad para limitar y controlar el acceso de los usuarios a las aplicaciones desarrolladas.

d) Se han utilizado esquemas de seguridad basados en roles.

e) Se han empleado algoritmos criptográficos para proteger el acceso a la información almacenada.

f) Se han identificado métodos para asegurar la información transmitida.

g) Se han desarrollado aplicaciones que utilicen sockets seguros para la transmisión de información.

h) Se han depurado y documentado las aplicaciones desarrolladas.


## Contenidos

* Prácticas de programación segura.
* Criptografía.
* Principales aplicaciones de la criptografía.
* Criptografía simétrica y asimétrica.
* Algoritmos de cifrado.
* Funciones hash.
* Firma digital.
* Certificados digitales.
* La infraestructura de clave pública.
* La seguridad en Java.
* Introducción a la seguridad en java
* Proveedores de seguridad.
* Criptografía.
* Autenticación.
* Comunicación segura.
* Control de acceso.
* Políticas de seguridad.
* Programación de aplicaciones con comunicaciones seguras.


## Evaluación

La evaluación de esta unidad didáctica estará basada en la entrega de las tareas, corregidas automáticamente por los tests necesarios a superar y examen teórico y práctico de la materia, acorde a la siguiente tabla

| CRITERIO de EVALUACIÓN | PONDERACIÓN | INSTRUMENTOS de EVALUACIÓN|
|------------------------|-------------|-------------|
| a)                     |12,5 %       | Tareas 1,2 y 3      |
| b)                     |12,5 %       |  Tareas 1,2 y 3      |
| c)                     |12,5 %       | Tareas 1,2 y 3      |
| d)                     |12,5 %       | Tareas 1,2 y 3      |
| e)                     |12,5 %       | Tareas 1,2 y 3     |
| f)                     |12,5 %       | Tareas 1,2 y 3      |
| g)                     |12,5 %       | Tareas 1,2 y 3      |
| h)                     |12,5 %       | Tareas 1,2 y 3      |
|                        |**100 %**    |             |

### **Tarea 1 - API Añadir hash MD5**
##### **Criterios a), b), c), d), e), f), g) y h)**
Utilizando el *framework* creado con las prácticas de la UA4 (API), añade la funcionalidad de generar [HASH](https://es.wikipedia.org/wiki/Función_hash) MD5 a tu API.  El método GET debe recibir como parámetro el texto al que se le debe calcular su hash [MD5](https://es.wikipedia.org/wiki/MD5).  En el siguiente bloque puedes ver un ejemplo de respuesta correcta.


```json
{
  "header": {
    "api_name": "My API Name",
    "api_version": "1.0.0",
    "api_user": "guest",
    "api_endpoint": "api/hash/",
    "http_request_method": "GET",
    "http_request_parameters": {
      "algorithm" : "md5",
      "text": "Generando un MD5 de un texto"
      },
    "http_response_status": 200,
    "http_response_message": "OK",
    "response_time": 123456789
  },
  "body" :
  {
    "algorithm" : "md5",
    "text" : "Generando un MD5 de un texto",
    "hash" : "5df9f63916ebf8528697b629022993e8"
  }
}
```
```
Fecha de Entrega: 10/03/2023
```


### **Tarea 2 - Generando llaves públicas y privadas en Java**
##### **Criterios a), b), c), d), e), f), g) y h)**

Esta práctica es sencilla, basta con ejecutar el pequeño [MANUAL](https://ryctabo.wordpress.com/2018/02/04/generating-public-and-private-keys-in-java/) que os indico a continuación acerca de la generación de un par de claves (keys) públicas y privadas.
Estas claves generadas y guardadas pueden utilizarse en los ejemplos.

```
Fecha de Entrega: 10/03/2023
```


### **Tarea 3 - API meets HTTPS**
##### **Criterios a), b), c), d), e), f), g) y h)**

Después de todo lo estudiado, podrás imaginar que nuestra API no es la más segura del mundo.  Vamos a intentar darle una capa adicional de seguridad permitiendo que nuestro código utilice [HTTPS](https://es.wikipedia.org/wiki/Protocolo_seguro_de_transferencia_de_hipertexto).  En lugar de HTTP simple.

Para ello, modificaremos nuestra API, para que por argumentos en la línea de comandos se pueda elegir entre lanzar un servidor HttpServer o bien su hermano mayor más seguro HttpsServer.  Por defecto HttpServer (no seguro).

- [HttpServer](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html)
- [HttpsServer](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpsServer.html)
- [Stack overflow: Simple Java HTTPS server](https://stackoverflow.com/questions/2308479/simple-java-https-server)

```
Fecha de Entrega: 10/03/2023
```

## Recursos

- [Repositorio Asignatura](https://github.com/daniteleco/psp-22-23)
- Apuntes del Profesorado //TODO
- Bibliografía Recomendada
  - **Programación de servicios y procesos - Técnico Superior en DAM.** *Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.*
- Moodle

## Autor

Creado por [Daniel Pérez Rodríguez](https://twitter.com/daniteleco) e inspirado en el trabajo de [José Luis González Sánchez](https://github.com/joseluisgs/ProgServiciosProcesos-00-2021-2022)

## Contacto
- Email: [dperez@ceslopedevega.com](mailto:dperez@ceslopedevega.com)
