import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
/**
 * Programa que:
 *  1. Lee caracteres desde la entrada estándar hasta encontrar un asterisco '*'.
 *  2. Una vez encontrado, muestra todo el texto (sin incluir el '*') y estadísticas simples.
 *
 * Funcionalidades:
 *  - FR1: Lectura hasta caracter terminación.
 *  - FR2: Mostrar información leída al finalizar.
 *
 * Control de errores:
 *  - EOF sin encontrar '*': error y código de salida != 0.
 *  - Errores de IO gestionados y reportados.
 *
 * Uso:
 *  java ReaderProgram
 *  (Escribir texto y terminar con '*')
 */
public class ReaderProgram {
    private static final char TERMINATOR = '*';
    public static void main(String[] args) {
        try {
            ReadResult result = readUntilTerminator();
            if (!result.terminatorFound) {
                System.err.println("[ERROR] No se encontró el caracter de terminación '" + TERMINATOR + "'. Final inesperado de entrada.");
                System.exit(2);
            }
            printReport(result);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("[ERROR] Fallo de E/S durante la lectura: " + e.getMessage());
            System.exit(1);
        } catch (SecurityException se) {
            System.err.println("[ERROR] Restricción de seguridad: " + se.getMessage());
            System.exit(3);
        } catch (Exception ex) {
            System.err.println("[ERROR] Error inesperado: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            System.exit(10);
        }
    }
    private static class ReadResult {
        final String content;
        final int lineCount;
        final boolean terminatorFound;
        ReadResult(String content, int lineCount, boolean terminatorFound) {
            this.content = content;
            this.lineCount = lineCount;
            this.terminatorFound = terminatorFound;
        }
    }
    private static ReadResult readUntilTerminator() throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        StringBuilder builder = new StringBuilder();
        int lineCount = 0;
        boolean terminatorFound = false;
        int ch;
        boolean lastWasCR = false;
        while ((ch = reader.read()) != -1) {
            char c = (char) ch;
            if (c == TERMINATOR) {
                terminatorFound = true;
                break;
            }
            builder.append(c);
            if (c == '\n') {
                lineCount++;
                lastWasCR = false;
            } else if (c == '\r') {
                lineCount++;
                lastWasCR = true;
            } else {
                lastWasCR = false;
            }
        }
        if (builder.length() > 0 && lineCount == 0) {
            lineCount = 1;
        }
        return new ReadResult(builder.toString(), lineCount, terminatorFound);
    }
    private static void printReport(ReadResult result) {
        System.out.println("[INFO] Caracter de terminación recibido. Mostrando datos recopilados...");
        System.out.println("===== CONTENIDO LEÍDO =====");
        System.out.println(result.content);
        System.out.println("Longitud (caracteres): " + result.content.length());
        System.out.println("Líneas leídas: " + result.lineCount);
        System.out.println("===========================");
    }
}