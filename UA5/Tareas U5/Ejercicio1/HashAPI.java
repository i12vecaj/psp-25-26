package Ejercicio1;

import java.security.MessageDigest;
import java.time.Instant;

public class HashAPI {

    public static String generarHashMD5(String text) {
        try {
            long startTime = System.currentTimeMillis();

            // Crear MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] digest = md.digest();

            // Convertir bytes → Hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }

            String hashMD5 = hexString.toString();

            long responseTime = System.currentTimeMillis() - startTime;

            // Construir JSON manualmente (como en la UA4)
            String json = "{\n" +
                    "  \"header\": {\n" +
                    "    \"api_name\": \"My API Name\",\n" +
                    "    \"api_version\": \"1.0.0\",\n" +
                    "    \"api_user\": \"guest\",\n" +
                    "    \"api_endpoint\": \"api/hash/\",\n" +
                    "    \"http_request_method\": \"GET\",\n" +
                    "    \"http_request_parameters\": {\n" +
                    "      \"algorithm\": \"md5\",\n" +
                    "      \"text\": \"" + text + "\"\n" +
                    "    },\n" +
                    "    \"http_response_status\": 200,\n" +
                    "    \"http_response_message\": \"OK\",\n" +
                    "    \"response_time\": " + responseTime + "\n" +
                    "  },\n" +
                    "  \"body\": {\n" +
                    "    \"algorithm\": \"md5\",\n" +
                    "    \"text\": \"" + text + "\",\n" +
                    "    \"hash\": \"" + hashMD5 + "\"\n" +
                    "  }\n" +
                    "}";

            return json;

        } catch (Exception e) {
            return "{ \"error\": \"No se pudo generar el MD5\" }";
        }
    }
}
