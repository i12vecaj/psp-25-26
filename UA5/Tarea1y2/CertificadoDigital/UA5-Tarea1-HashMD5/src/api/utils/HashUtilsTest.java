package api.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la clase HashUtils.
 * Verifica que los hashes generados son correctos y que se manejan
 * correctamente los casos de error.
 */
@DisplayName("Tests de HashUtils")
class HashUtilsTest {

    // ---------------------------------------------------------------
    // Tests MD5
    // ---------------------------------------------------------------

    @Test
    @DisplayName("MD5 del ejemplo del enunciado")
    void testMd5EnunciadoEjemplo() {
        // Hash esperado según el enunciado de la tarea
        String input    = "Generando un MD5 de un texto";
        String expected = "5df9f63916ebf8528697b629022993e8";
        assertEquals(expected, HashUtils.md5(input));
    }

    @Test
    @DisplayName("MD5 de cadena vacía")
    void testMd5CadenaVacia() {
        // MD5 de "" = d41d8cd98f00b204e9800998ecf8427e
        String expected = "d41d8cd98f00b204e9800998ecf8427e";
        // Pero nuestro método lanza excepción con texto vacío
        assertThrows(IllegalArgumentException.class, () -> HashUtils.md5(""));
    }

    @Test
    @DisplayName("MD5 de texto nulo lanza excepción")
    void testMd5Nulo() {
        assertThrows(IllegalArgumentException.class, () -> HashUtils.md5(null));
    }

    @Test
    @DisplayName("MD5 siempre devuelve 32 caracteres hexadecimales")
    void testMd5Longitud() {
        String hash = HashUtils.md5("cualquier texto");
        assertEquals(32, hash.length());
        assertTrue(hash.matches("[0-9a-f]+"), "El hash debe ser hexadecimal en minúsculas");
    }

    @Test
    @DisplayName("MD5 es determinista - mismo texto, mismo hash")
    void testMd5Determinista() {
        String texto = "texto de prueba";
        assertEquals(HashUtils.md5(texto), HashUtils.md5(texto));
    }

    @Test
    @DisplayName("MD5 es sensible a mayúsculas")
    void testMd5CaseSensitive() {
        assertNotEquals(HashUtils.md5("hola"), HashUtils.md5("Hola"));
    }

    // ---------------------------------------------------------------
    // Tests método hash() genérico
    // ---------------------------------------------------------------

    @Test
    @DisplayName("hash() con algoritmo md5 equivale a md5()")
    void testHashGenericoMd5() {
        String texto = "texto de prueba";
        assertEquals(HashUtils.md5(texto), HashUtils.hash("md5", texto));
    }

    @Test
    @DisplayName("hash() con algoritmo sha256 devuelve 64 caracteres")
    void testHashSha256Longitud() {
        String hash = HashUtils.hash("sha256", "texto de prueba");
        assertEquals(64, hash.length());
        assertTrue(hash.matches("[0-9a-f]+"));
    }

    @Test
    @DisplayName("hash() con algoritmo sha1 devuelve 40 caracteres")
    void testHashSha1Longitud() {
        String hash = HashUtils.hash("sha1", "texto de prueba");
        assertEquals(40, hash.length());
        assertTrue(hash.matches("[0-9a-f]+"));
    }

    @Test
    @DisplayName("hash() con algoritmo no soportado lanza excepción")
    void testHashAlgoritmoNoSoportado() {
        assertThrows(IllegalArgumentException.class,
                () -> HashUtils.hash("sha512", "texto"));
    }

    @Test
    @DisplayName("hash() con texto nulo lanza excepción")
    void testHashTextoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> HashUtils.hash("md5", null));
    }
}