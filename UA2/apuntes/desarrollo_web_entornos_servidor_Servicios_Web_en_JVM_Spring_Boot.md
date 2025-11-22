<div align="center">

<a href="https://www.youtube.com/watch?v=p_pT95ekaa4">
  <img src="https://i.imgur.com/p9XHWrD.png" style="max-width: 400px; width: 100%;">
</a>

[Enlace al vídeo de Youtube](https://www.youtube.com/watch?v=p_pT95ekaa4)

</div>

# Creando API con Spring Boot

## 1. Base: El Concepto de Servicio Web

- **Problema fundamental**: cómo aplicaciones totalmente distintas (como una en un móvil y otra en un servidor), escritas en lenguajes diferentes, pueden comunicarse a través de internet de forma ordenada y fiable.

- **Solución:** un servicio web, cuya funcionalidad ofrecida por una API para que otros componentes de software accedan a recursos en red.

      * Funciona como un "camarero universal". El servicio web ofrece una carta (la API) para saber qué se puede pedir, y no importa cómo se cocina el pedido por dentro.

- Protocolo fundamental que rige la web: **HTTP**.

      * Funciona como si se enviara un email: cada mensaje o petición es totalmente independiente y no recuerda el anterior. Esta característica es clave para que internet pueda escalar masivamente.

## 2. El Plano: La Arquitectura REST

- El diseño más famoso y utilizado es la **arquitectura REST**.

- REST se convirtió en el rey por su simplicidad y porque utiliza las mismas reglas que ya existían en la web, como el protocolo HTTP, sin complicarse la vida como otras alternativas (ej. SOAP).

      * Usar REST se asemeja a ir a un restaurante y pedir comida. La carta son los recursos de la API (ej. /productos o /usuarios).

- Las acciones son los verbos HTTP. Esto proporciona un lenguaje universal y predecible para manipular datos:

  | Verbo HTTP | Acción             |
  | ---------- | ------------------ |
  | GET        | Leer recurso       |
  | POST       | Crear recurso      |
  | PUT        | Actualizar recurso |
  | DELETE     | Eliminar recurso   |

- El servidor responde con códigos HTTP estandarizados que comunican el resultado al instante:

  | Código        | Significado        | Analogía                    |
  | ------------- | ------------------ | --------------------------- |
  | 200 OK        | Éxito              | _¡Pedido listo!_            |
  | 201 Created   | Recurso creado     | _Petición especial añadida_ |
  | 404 Not Found | No encontrado      | _Se acabó ese plato_        |
  | 500 Error     | Error del servidor | _Problema en la cocina_     |

## 3. La Caja de Herramientas: Spring Boot

- Una de las herramientas más potente del mundo Java para este trabajo es Spring Boot.

- Spring Boot facilita la vida enormemente por tres razones principales:

  1. **Starters**: Dependencias preconfiguradas para un inicio rápido.
  2. **Auto-configuración**: Configuración inteligente basada en librerías.
  3. **Servidor Web Integrado (Embebido)**: Ejecuta la aplicación directamente, sin servidor.

- El código dentro de Spring se organiza de manera elegante usando anotaciones (palabras que empiezan con una @).

- El **flujo/anatomía de una petición** dentro del código es limpio y ordenado:

  - **`@RestController`** (el portero del edificio) recibe la petición.
  - Pasa la petición al **`@Service`** (el cerebro) que contiene la lógica de negocio principal.
  - Si necesita interactuar con los datos, el `@Service` se lo pide al **`@Repository`** (el único capaz de comunicarse con la base de datos).

## 4. Control de Calidad: Pruebas y Seguridad

- Es crucial asegurarse de que la API funciona bien, es fiable y, sobre todo, segura.

- El control de calidad se realiza mediante dos niveles de pruebas que son necesarias:

  1. **Pruebas Unitarias**: Prueban un único componente de forma aisladas. Son rápidas y precisas.
  2. **Pruebas de Integración**: Prueban cómo varios componentes funcionan juntos, asegurando la comunicación.

- La seguridad es obligatoria por ser un aspecto crítico en el desarrollo de aplicaciones web y se debe diseñar desde el principio. Herramientas como _Spring Security_ proporcionan la puerta blindada y la alarma para proteger la API desde el minuto cero.

## 5. Lanzamiento a la Nube: Despliegue con Docker

- La forma más moderna y estándar de desplegar la aplicación es usando Docker.

- Pasos:

  1. **Crear el JAR**. Empaquetar la aplicación en un único archivo ejecutable (`.jar`).
  2. **Definir Dockerfile**. Crear una receta para construir una imagen de contenedor.
  3. **Ejecutar Contenedor**. Lanzar la aplicación en calquier lugar donde Docker esté instalado.

## Conclusión

La combinación de REST y Spring Boot es una pareja increíble para construir de forma sólida y rápida. Sin embargo, la historia continúa, y hay un debate sobre si REST seguirá siendo el rey ante nuevos jugadores como GraphQL.
