<div align="center">

<a href="https://www.youtube.com/watch?v=uJHhCSNHIxQ">
    <img src="https://i.imgur.com/g0yL5RK.png" style="max-width: 400px; width: 100%;">
</a>

[Enlace al vídeo de Youtube](https://www.youtube.com/watch?v=c-2D3YEYnrg)

</div>

# Técnicas de Programación Segura

## 1. Código Seguro

- **Fundamento**: El software es la base de nuestro mundo, pero la seguridad no es un "extra" opcional ni responsabilidad de otro departamento; es vital para quien escribe el código.
- **Analogía**: El código es una _caja fuerte digital_. No protege joyas, sino algo más valioso hoy en día: datos de usuarios, secretos de empresa e información personal.
- **Programación segura**: La práctica de escribir código que es resistente a ataques y protege contra vulnerabilidades.

## 2. Mentalidad del Programador

- La seguridad debe ser una _actitud_: curiosidad y método desde el minuto uno.
- No se trata de reaccionar a fallos, sino de construir las defensas dentro de la propia estructura.
- La seguridad no es una funcionalidad del menú, es el **cimiento** del edificio. Si falla, todo se cae.
- **Aprendizaje Continuo**:
  - Estudiar fallos históricos (como Heartbleed o Log4Shell) para no repetir patrones.
  - Leer código de grandes proyectos Open Source (en GitHub) para aprender buenas prácticas.
- _Consejo: No intentes cambiar todo en un día. La seguridad se construye con la suma de pequeños hábitos correctos aplicados proyecto a proyecto_.

## 3. Manejo de Datos

- **Principio Fundamental**: "No fiarse de nada que venga de fuera".
- **La Primera Línea de Defensa**: Cualquier dato (formulario, API, línea de comandos) es potencialmente hostil.
- **Acción**:
  - **Validar y Sanitizar**: Comprobar límites y limpiar la entrada para evitar ataques.
  - **Amenazas Comunes**: Inyección SQL (colar comandos en la base de datos) o Desbordamiento de Búfer (romper el programa con exceso de datos).
- _La desconfianza debe ser total: desde una URL hasta una cookie oculta; todo puede ser manipulado_.

### Protección del Dato: Los Tres Pilares

- **Objetivo**: Una vez los datos están dentro, hay que garantizar tres aspectos:
  1. Confidencialidad: Solo accede quien debe.
  2. Integridad: Nadie altera los datos sin que nos demos cuenta.
  3. Disponibilidad.
- **Herramientas**:
  - El **cifrado** protege la confidencialidad.
  - Las **firmas digitales y hashes** aseguran la integridad.

## 4. Código Bajo el Microscopio

- **El Problema**: Es difícil ver nuestros propios errores. Necesitamos un sistema para "cazar" vulnerabilidades.
- **Estrategia de Capas**:
  - **Revisiones por pares**: Perspectiva humana que detecta errores de lógica.
  - **Análisis estático**: Herramientas automáticas que buscan patrones de error.
  - **Usar herramientas**: Identificar y usar software de seguridad disponible.

## 5. Herramientas en Java:

| Tecnología / API | Descripción / Función                                                                                                                             |
| :--------------- | :------------------------------------------------------------------------------------------------------------------------------------------------ |
| **JCA / JCE**    | Librerías **criptográficas**. Proporcionan las herramientas necesarias para cifrar datos y realizar firmas digitales.                             |
| **JSSE**         | **Extensión de Sockets Seguros**. Se encarga de establecer **comunicaciones seguras**. Es la responsable de crear canales protegidos, como HTTPS. |
| **JAAS**         | **Servicio de autentificación y autorización**. Gestiona la autenticación (verificar quién es) y la autorización (qué puede hacer).               |
