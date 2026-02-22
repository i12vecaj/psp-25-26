
---


### Tarea 1: Generación de Hash MD5
Se ha integrado un motor de hashing en la API para transformar datos de texto en huellas digitales únicas e irreversibles.

* **Endpoint:** `GET /api/hash?text=tu_texto`
* **Lógica:** Uso de `java.security.MessageDigest`.
* **Salida:** JSON estructurado con metadatos y el hash en formato Hexadecimal.



### Tarea 2: Criptografía Asimétrica (RSA)
Simulación de la generación de un par de llaves (Pública/Privada) para entender los cimientos de la seguridad en internet.

* **Algoritmo:** RSA (2048 bits).
* **Concepto:** La llave pública cifra, la privada descifra. Es la base del protocolo SSL/TLS.



### Tarea 3: Securización HTTPS
Transformación del servidor básico en un entorno seguro mediante el protocolo TLS.

* **Servidor:** Migración de `HttpServer` a `HttpsServer`.
* **Certificados:** Implementación de un `SSLContext` cargado desde un archivo `keystore.jks`.
* **Seguridad:** Cifrado de extremo a extremo entre el cliente (Postman) y la API.



---

## Especificaciones de Respuesta (JSON)
Todas las tareas cumplen con el formato de respuesta profesional requerido:

| Campo | Descripción |
| :--- | :--- |
| `header` | Contiene metadatos: versión, método, status HTTP y tiempo de respuesta. |
| `body` | Contiene la carga útil (datos del hash o información de personas). |

---

## Guía de Puesta en Marcha

### Pre-requisitos
1.  Tener instalada la librería **`org.json`** en el Classpath.
2.  Generar el almacén de llaves con el siguiente comando:
    ```bash
    keytool -genkeypair -alias selfsigned -keyalg RSA -keystore keystore.jks -storepass password -keypass password
    ```

### Compilación y Ejecución
```bash
# Compilar todas las tareas
javac -cp ".;json.jar" Tarea*.java

# Ejecutar la API Segura
java -cp ".;json.jar" Tarea03_Https