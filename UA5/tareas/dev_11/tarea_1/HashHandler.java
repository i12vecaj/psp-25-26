import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

class HashHandler extends BasicHandler
{
    private static final String API_NAME = "My API Name";
    private static final String API_VERSION = "1.0.0";

    public HashHandler(DataStore store)
    {
        super(store);
    }

    public void handle(HttpExchange exchange) throws IOException
    {
        long startTime = System.nanoTime();
        String remoteAddress = exchange.getRemoteAddress().getHostString();
        System.out.println("[" + new Date() + "] Received " + exchange.getRequestMethod() + " " + exchange.getRequestURI() + " from client: " + remoteAddress);

        if (!"GET".equals(exchange.getRequestMethod()))
        {
            String responseString = buildResponse(
                "guest",
                "md5",
                "",
                "",
                405,
                "Method Not Allowed",
                System.nanoTime() - startTime
            );
            writeJson(exchange, 405, responseString);
            return;
        }

        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        String algorithm = params.getOrDefault("algorithm", "md5");
        String text = params.get("text");

        if (text == null || text.isEmpty())
        {
            String responseString = buildResponse(
                "guest",
                algorithm,
                "",
                "",
                400,
                "Bad Request: missing text parameter",
                System.nanoTime() - startTime
            );
            writeJson(exchange, 400, responseString);
            return;
        }

        if (!"md5".equalsIgnoreCase(algorithm))
        {
            String responseString = buildResponse(
                "guest",
                algorithm,
                text,
                "",
                400,
                "Bad Request: unsupported algorithm",
                System.nanoTime() - startTime
            );
            writeJson(exchange, 400, responseString);
            return;
        }

        String hash = md5Hex(text);
        String responseString = buildResponse(
            "guest",
            "md5",
            text,
            hash,
            200,
            "OK",
            System.nanoTime() - startTime
        );
        writeJson(exchange, 200, responseString);
    }

    private static String md5Hex(String text)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : digest)
            {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalStateException("MD5 algorithm is not available", e);
        }
    }

    private static String buildResponse(String apiUser, String algorithm, String text, String hash, int status, String message, long responseTime)
    {
        String safeAlgorithm = jsonEscape(algorithm == null ? "" : algorithm.toLowerCase());
        String safeText = jsonEscape(text);
        String safeHash = jsonEscape(hash);
        String safeMessage = jsonEscape(message);

        return "{" +
            "\"header\":{" +
                "\"api_name\":\"" + API_NAME + "\"," +
                "\"api_version\":\"" + API_VERSION + "\"," +
                "\"api_user\":\"" + jsonEscape(apiUser) + "\"," +
                "\"api_endpoint\":\"api/hash/\"," +
                "\"http_request_method\":\"GET\"," +
                "\"http_request_parameters\":{" +
                    "\"algorithm\":\"" + safeAlgorithm + "\"," +
                    "\"text\":\"" + safeText + "\"" +
                "}," +
                "\"http_response_status\":" + status + "," +
                "\"http_response_message\":\"" + safeMessage + "\"," +
                "\"response_time\":" + responseTime +
            "}," +
            "\"body\":{" +
                "\"algorithm\":\"" + safeAlgorithm + "\"," +
                "\"text\":\"" + safeText + "\"," +
                "\"hash\":\"" + safeHash + "\"" +
            "}" +
        "}";
    }

    private static String jsonEscape(String value)
    {
        if (value == null)
        {
            return "";
        }
        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    private static void writeJson(HttpExchange exchange, int status, String responseString) throws IOException
    {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] responseBytes = responseString.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, responseBytes.length);
        OutputStream output = exchange.getResponseBody();
        output.write(responseBytes);
        output.flush();
        exchange.close();
    }
}
