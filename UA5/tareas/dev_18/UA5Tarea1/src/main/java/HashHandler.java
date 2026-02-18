import java.io.OutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

// Handler encargado de gestionar el endpoint /api/hash
class HashHandler extends BasicHandler {

    public HashHandler(DataStore store) {
        super(store);
    }

    public void handle(HttpExchange exchange) throws IOException {

        // Inicio de medición del tiempo de respuesta
        long startTime = System.nanoTime();

        // Solo se permite método GET
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        // Obtener parámetros de la query
        Map<String, String> params =
                queryToMap(exchange.getRequestURI().getQuery());

        String algorithm = params.get("algorithm");
        String text = params.get("text");

        // Validación de parámetros
        if (algorithm == null || text == null
                || !algorithm.equalsIgnoreCase("md5")) {
            exchange.sendResponseHeaders(400, -1);
            exchange.close();
            return;
        }

        String hash = "";

        try {
            // Generar hash MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes());

            // Convertir bytes a hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            hash = sb.toString();

        } catch (Exception e) {
            // Error interno al generar el hash
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
            return;
        }

        // Fin de medición del tiempo
        long endTime = System.nanoTime();

        // Construcción manual del JSON de respuesta
        String response = "{";
        response += "\"header\":{";
        response += "\"api_name\":\"My API Name\",";
        response += "\"api_version\":\"1.0.0\",";
        response += "\"api_user\":\"guest\",";
        response += "\"api_endpoint\":\"api/hash/\",";
        response += "\"http_request_method\":\"GET\",";
        response += "\"http_request_parameters\":{";
        response += "\"algorithm\":\"md5\",";
        response += "\"text\":\"" + text + "\"";
        response += "},";
        response += "\"http_response_status\":200,";
        response += "\"http_response_message\":\"OK\",";
        response += "\"response_time\":" + (endTime - startTime);
        response += "},";
        response += "\"body\":{";
        response += "\"algorithm\":\"md5\",";
        response += "\"text\":\"" + text + "\",";
        response += "\"hash\":\"" + hash + "\"";
        response += "}";
        response += "}";

        // Envío de la respuesta al cliente
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        exchange.close();
    }
}
