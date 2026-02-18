package api.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

/**
 * Tests unitarios para QueryStringParser.
 */
@DisplayName("Tests de QueryStringParser")
class QueryStringParserTest {

    @Test
    @DisplayName("Parsea parámetros simples correctamente")
    void testParseSimple() {
        Map<String, String> params = QueryStringParser.parse("algorithm=md5&text=hola");
        assertEquals("md5", params.get("algorithm"));
        assertEquals("hola", params.get("text"));
    }

    @Test
    @DisplayName("Decodifica caracteres URL encoded (+)")
    void testParseUrlEncoded() {
        Map<String, String> params = QueryStringParser.parse("text=hola+mundo");
        assertEquals("hola mundo", params.get("text"));
    }

    @Test
    @DisplayName("Decodifica caracteres URL encoded (%20)")
    void testParseUrlEncoded2() {
        Map<String, String> params = QueryStringParser.parse("text=hola%20mundo");
        assertEquals("hola mundo", params.get("text"));
    }

    @Test
    @DisplayName("Query string nula devuelve mapa vacío")
    void testParseNulo() {
        Map<String, String> params = QueryStringParser.parse(null);
        assertTrue(params.isEmpty());
    }

    @Test
    @DisplayName("Query string vacía devuelve mapa vacío")
    void testParseVacia() {
        Map<String, String> params = QueryStringParser.parse("");
        assertTrue(params.isEmpty());
    }

    @Test
    @DisplayName("Parsea texto con caracteres especiales")
    void testParseCaracteresEspeciales() {
        Map<String, String> params = QueryStringParser.parse(
                "text=Generando+un+MD5+de+un+texto");
        assertEquals("Generando un MD5 de un texto", params.get("text"));
    }
}