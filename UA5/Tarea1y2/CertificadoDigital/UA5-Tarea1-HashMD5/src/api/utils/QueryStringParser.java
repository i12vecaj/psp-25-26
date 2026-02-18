package api.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilidad para parsear los parámetros de la query string de una URL.
 * Ejemplo: "algorithm=md5&text=hola+mundo" → {algorithm: "md5", text: "hola mundo"}
 */
public class QueryStringParser {

    private QueryStringParser() {}

    /**
     * Parsea una query string y devuelve un mapa de clave-valor.
     *
     * @param query Query string sin el símbolo '?'
     * @return Mapa con los parámetros decodificados
     */
    public static Map<String, String> parse(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isBlank()) {
            return params;
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf('=');
            if (idx > 0) {
                String key   = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
                params.put(key.trim(), value);
            }
        }
        return params;
    }
}