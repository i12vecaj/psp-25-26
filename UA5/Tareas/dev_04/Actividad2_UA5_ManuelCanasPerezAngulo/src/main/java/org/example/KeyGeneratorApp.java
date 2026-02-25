
package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;
import javax.crypto.Cipher;

    /**
     * Utilidad para la gestión de ciclo de vida de llaves RSA.
     * Genera, exporta y valida pares de llaves en formato PEM y BIN.
     */
    public class KeyGeneratorApp {

        private static final Logger LOGGER = Logger.getLogger(KeyGeneratorApp.class.getName());

        // Configuraciones por defecto
        private static final String ALGORITHM = "RSA";
        private static final String CIPHER_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
        private static final int DEFAULT_KEY_SIZE = 2048;

        public static void main(String[] args) {
            // 1. Procesamiento de argumentos de entrada
            Path targetDir = Paths.get(args.length > 0 ? args[0] : ".");
            int bits = (args.length > 1) ? parseSafeInt(args[1], DEFAULT_KEY_SIZE) : DEFAULT_KEY_SIZE;

            try {
                // 2. Generación del par de llaves
                KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
                generator.initialize(bits);
                KeyPair pair = generator.generateKeyPair();

                // 3. Preparación del directorio de salida
                if (Files.notExists(targetDir)) {
                    Files.createDirectories(targetDir);
                }

                // 4. Exportación de llaves
                saveToPem(pair.getPrivate(), targetDir.resolve("private.pem"), "PRIVATE KEY");
                saveToPem(pair.getPublic(), targetDir.resolve("public.pem"), "PUBLIC KEY");

                Files.write(targetDir.resolve("private.bin"), pair.getPrivate().getEncoded());
                Files.write(targetDir.resolve("public.bin"), pair.getPublic().getEncoded());

                System.out.println(">>> Llaves exportadas exitosamente en: " + targetDir.toAbsolutePath());

                // 5. Prueba de integridad (Round-trip test)
                performSelfTest(targetDir);

            } catch (Exception e) {
                LOGGER.severe("Fallo en la operación criptográfica: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private static void saveToPem(Key key, Path filePath, String label) throws IOException {
            String encoded = Base64.getMimeEncoder(64, System.lineSeparator().getBytes())
                    .encodeToString(key.getEncoded());

            StringBuilder pemContent = new StringBuilder();
            pemContent.append("-----BEGIN ").append(label).append("-----\n");
            pemContent.append(encoded).append("\n");
            pemContent.append("-----END ").append(label).append("-----\n");

            Files.write(filePath, pemContent.toString().getBytes(StandardCharsets.UTF_8));
        }

        private static void performSelfTest(Path dir) throws Exception {
            // Carga de llaves desde binario
            PublicKey pub = importPublicKey(dir.resolve("public.bin"));
            PrivateKey priv = importPrivateKey(dir.resolve("private.bin"));

            String secret = "Información confidencial 2026 - Test de validación.";

            // Cifrado
            byte[] encryptedData = crypt(secret.getBytes(StandardCharsets.UTF_8), pub, Cipher.ENCRYPT_MODE);
            String base64Encrypted = Base64.getEncoder().encodeToString(encryptedData);

            // Descifrado
            byte[] decryptedData = crypt(encryptedData, priv, Cipher.DECRYPT_MODE);
            String result = new String(decryptedData, StandardCharsets.UTF_8);

            System.out.println("\n[AUTO-VALIDACIÓN]");
            System.out.println("Original:  " + secret);
            System.out.println("Encrypted: " + base64Encrypted.substring(0, 40) + "...");
            System.out.println("Success:   " + secret.equals(result));
        }

        private static byte[] crypt(byte[] data, Key key, int mode) throws Exception {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(mode, key);
            return cipher.doFinal(data);
        }

        private static PublicKey importPublicKey(Path path) throws Exception {
            byte[] keyBytes = Files.readAllBytes(path);
            return KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(keyBytes));
        }

        private static PrivateKey importPrivateKey(Path path) throws Exception {
            byte[] keyBytes = Files.readAllBytes(path);
            return KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        }

        private static int parseSafeInt(String input, int fallback) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                LOGGER.warning("Formato de bits inválido, usando: " + fallback);
                return fallback;
            }
        }
    }
