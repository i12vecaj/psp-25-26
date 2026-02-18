package main.java;

import com.sun.net.httpserver.HttpHandler;
import java.util.HashMap;
import java.util.Map;

abstract class BasicHandler implements HttpHandler {

    /**
     * Convierte una query string en un Map de parámetros
     * 
     * @param query string de parámetros (ej: "text=hola&algorithm=md5")
     * @return Map con los parámetros
     */
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();

        if (query != null) {
            for (String param : query.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1) {
                    result.put(pair[0], pair[1]);
                } else {
                    result.put(pair[0], "");
                }
            }
        }
        return result;
    }
}
