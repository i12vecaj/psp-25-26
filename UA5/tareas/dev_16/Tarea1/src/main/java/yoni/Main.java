package yoni;

/*
 * API REST para generación de hashes MD5
 *
 * Este programa implementa un servidor web ligero usando Javalin que expone un endpoint REST
 * para calcular el hash MD5 de un texto. La arquitectura sigue un patrón modular donde cada
 * petición se procesa de forma independiente y devuelve una respuesta JSON estructurada.
 *
 * El flujo de ejecución es el siguiente:
 * 1. El servidor inicia en el puerto 7070 y queda a la escucha de peticiones GET en /api/hash/
 * 2. Cuando llega una petición, se captura el timestamp inicial para medir el tiempo de respuesta
 * 3. Se extraen y validan los parámetros de la query string (algorithm y text)
 * 4. Si los parámetros son válidos y el algoritmo es MD5, se calcula el hash usando MessageDigest
 * 5. Se construye una respuesta JSON con dos secciones: header (metadatos de la API) y body (datos)
 * 6. La respuesta se serializa automáticamente a JSON gracias a Jackson y se envía al cliente
 *
 * El uso de Maps permite flexibilidad en la estructura JSON
 * sin necesidad de crear clases POJO adicionales.
 */

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // Instancia de Gson para posibles conversiones JSON adicionales
    private static final Gson gson = new Gson();

    /**
     * Método principal que inicia el servidor Javalin en el puerto 7070
     * y registra el endpoint GET para el cálculo de hashes
     */
    public static void main(String[] args) {
        Javalin aplicacion = Javalin.create().start(7070);

        // Registro del endpoint GET /api/hash/ que maneja las peticiones de hash
        aplicacion.get("/api/hash/", contexto -> {
            manejarPeticionHash(contexto);
        });
    }

    /**
     * Procesa las peticiones al endpoint de hash MD5
     * Valida los parámetros, genera el hash y construye la respuesta apropiada
     *
     * @param contexto Objeto Context de Javalin que contiene la información de la petición HTTP
     */
    private static void manejarPeticionHash(Context contexto) {
        // Captura el tiempo de inicio para calcular el tiempo de respuesta
        long tiempoInicio = System.currentTimeMillis();

        // Extracción de los parámetros de la query string
        String algoritmo = contexto.queryParam("algorithm");
        String texto = contexto.queryParam("text");

        // Validación: verificar que ambos parámetros estén presentes
        if (algoritmo == null || texto == null) {
            contexto.status(400);
            contexto.json(crearRespuestaError("Missing required parameters: algorithm and text", 400, tiempoInicio, contexto));
            return;
        }

        // Validación: verificar que el algoritmo sea MD5
        if (!algoritmo.equalsIgnoreCase("md5")) {
            contexto.status(400);
            contexto.json(crearRespuestaError("Only MD5 algorithm is supported", 400, tiempoInicio, contexto));
            return;
        }

        // Intento de generar el hash MD5 del texto proporcionado
        try {
            String hash = generarMD5(texto);
            contexto.status(200);
            contexto.json(crearRespuestaExitosa(algoritmo, texto, hash, tiempoInicio, contexto));
        } catch (NoSuchAlgorithmException e) {
            contexto.status(500);
            contexto.json(crearRespuestaError("Error generando hash", 500, tiempoInicio, contexto));
        }
    }

    /**
     * Genera el hash MD5 de un texto dado
     * Utiliza MessageDigest de Java Security para calcular el hash
     * y lo convierte a su representación hexadecimal
     *
     * @param texto El texto del cual se calculará el hash MD5
     * @return String con el hash MD5 en formato hexadecimal (32 caracteres)
     * @throws NoSuchAlgorithmException Si el algoritmo MD5 no está disponible
     */
    private static String generarMD5(String texto) throws NoSuchAlgorithmException {
        // Obtener una instancia del algoritmo MD5
        MessageDigest digestorMensaje = MessageDigest.getInstance("MD5");

        // Calcular el hash del texto convertido a bytes
        byte[] bytesHash = digestorMensaje.digest(texto.getBytes());

        // Convertir los bytes del hash a formato hexadecimal
        StringBuilder cadenaHexadecimal = new StringBuilder();
        for (byte b : bytesHash) {
            String hexadecimal = Integer.toHexString(0xff & b);
            // Añadir un cero inicial si el valor hexadecimal tiene solo un dígito
            if (hexadecimal.length() == 1) {
                cadenaHexadecimal.append('0');
            }
            cadenaHexadecimal.append(hexadecimal);
        }
        return cadenaHexadecimal.toString();
    }

    /**
     * Construye la respuesta JSON para peticiones exitosas
     * La respuesta incluye un header con metadatos de la API y un body con los resultados
     *
     * @param algoritmo El algoritmo utilizado (md5)
     * @param texto El texto original proporcionado
     * @param hash El hash MD5 calculado
     * @param tiempoInicio Timestamp del inicio de la petición para calcular el tiempo de respuesta
     * @param contexto Contexto de la petición HTTP
     * @return Map que representa la estructura JSON de la respuesta
     */
    private static Map<String, Object> crearRespuestaExitosa(String algoritmo, String texto, String hash, long tiempoInicio, Context contexto) {
        Map<String, Object> respuesta = new HashMap<>();

        // Construcción del header con metadatos de la API
        Map<String, Object> cabecera = new HashMap<>();
        cabecera.put("api_name", "My API Name");
        cabecera.put("api_version", "1.0.0");
        cabecera.put("api_user", "guest");
        cabecera.put("api_endpoint", "api/hash/");
        cabecera.put("http_request_method", "GET");

        // Incluir los parámetros recibidos en la petición
        Map<String, String> parametros = new HashMap<>();
        parametros.put("algorithm", algoritmo);
        parametros.put("text", texto);
        cabecera.put("http_request_parameters", parametros);

        // Metadatos de la respuesta HTTP
        cabecera.put("http_response_status", 200);
        cabecera.put("http_response_message", "OK");
        cabecera.put("response_time", System.currentTimeMillis() - tiempoInicio);

        // Construcción del body con los datos de respuesta
        Map<String, String> cuerpo = new HashMap<>();
        cuerpo.put("algorithm", algoritmo);
        cuerpo.put("text", texto);
        cuerpo.put("hash", hash);

        // Ensamblar la respuesta completa
        respuesta.put("header", cabecera);
        respuesta.put("body", cuerpo);

        return respuesta;
    }

    /**
     * Construye la respuesta JSON para peticiones con error
     * Similar a la respuesta exitosa pero sin body, solo header con información del error
     *
     * @param mensaje Descripción del error ocurrido
     * @param estado Código de estado HTTP (400, 500, etc.)
     * @param tiempoInicio Timestamp del inicio de la petición
     * @param contexto Contexto de la petición HTTP
     * @return Map que representa la estructura JSON de la respuesta de error
     */
    private static Map<String, Object> crearRespuestaError(String mensaje, int estado, long tiempoInicio, Context contexto) {
        Map<String, Object> respuesta = new HashMap<>();

        // Header con información del error
        Map<String, Object> cabecera = new HashMap<>();
        cabecera.put("api_name", "My API Name");
        cabecera.put("api_version", "1.0.0");
        cabecera.put("api_user", "guest");
        cabecera.put("api_endpoint", "api/hash/");
        cabecera.put("http_request_method", "GET");
        cabecera.put("http_response_status", estado);
        cabecera.put("http_response_message", mensaje);
        cabecera.put("response_time", System.currentTimeMillis() - tiempoInicio);

        respuesta.put("header", cabecera);

        return respuesta;
    }
}
