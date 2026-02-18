import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GenerarClaves {
    private static final Logger LOG = Logger.getLogger(GenerarClaves.class.getName());

    public static void main(String[] args) {
        System.out.println("=== Generador de Claves Públicas y Privadas ===\n");

        try {
            // 1. Generar el par de claves
            System.out.println("1. Generando par de claves RSA (1024 bits)...");
            KeyPair keyPair = generarKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("   ✓ Par de claves generado exitosamente\n");

            // 2. Guardar en archivos de texto plano
            System.out.println("2. Guardando claves en archivo de texto plano...");
            guardarClavePrivadaTexto("clavePrivada.txt", privateKey);
            guardarClavePublicaTexto("clavePublica.txt", publicKey);
            System.out.println("   ✓ Claves guardadas en texto plano\n");

            // 3. Guardar en archivos binarios
            System.out.println("3. Guardando claves en archivos binarios...");
            guardarClavePrivadaBinario("clavePrivada.bin", privateKey);
            guardarClavePublicaBinario("clavePublica.bin", publicKey);
            System.out.println("   ✓ Claves guardadas en archivos binarios\n");

            // 4. Mostrar formatos
            System.out.println("4. Formatos de las claves:");
            System.out.println("   - Formato de clave pública: " + publicKey.getFormat());
            System.out.println("   - Formato de clave privada: " + privateKey.getFormat());
            System.out.println();

            System.out.println("=== Proceso completado exitosamente ===");

        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.SEVERE, "Algoritmo RSA no disponible", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error al guardar las claves", e);
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return KeyPair con las claves pública y privada
     * @throws NoSuchAlgorithmException si el algoritmo RSA no está disponible
     */
    private static KeyPair generarKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 
     * @param fileName   nombre del archivo
     * @param privateKey clave privada a guardar
     * @throws IOException si hay error al escribir el archivo
     */
    private static void guardarClavePrivadaTexto(String fileName, PrivateKey privateKey) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter(fileName)) {
            out.write("-----BEGIN RSA PRIVATE KEY-----");
            out.write("\n");
            out.write(encoder.encodeToString(privateKey.getEncoded()));
            out.write("\n");
            out.write("-----END RSA PRIVATE KEY-----");
            out.write("\n");
            LOG.info("Clave privada guardada en: " + fileName);
        }
    }

    /**
     * 
     * @param fileName  nombre del archivo
     * @param publicKey clave pública a guardar
     * @throws IOException si hay error al escribir el archivo
     */
    private static void guardarClavePublicaTexto(String fileName, PublicKey publicKey) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter(fileName)) {
            out.write("-----BEGIN RSA PUBLIC KEY-----");
            out.write("\n");
            out.write(encoder.encodeToString(publicKey.getEncoded()));
            out.write("\n");
            out.write("-----END RSA PUBLIC KEY-----");
            out.write("\n");
            LOG.info("Clave pública guardada en: " + fileName);
        }
    }

    /**
     * 
     * @param fileName   nombre del archivo
     * @param privateKey clave privada a guardar
     * @throws IOException si hay error al escribir el archivo
     */
    private static void guardarClavePrivadaBinario(String fileName, PrivateKey privateKey) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(privateKey.getEncoded());
            LOG.info("Clave privada guardada en formato binario: " + fileName);
        }
    }

    /**
     * 
     * @param fileName  nombre del archivo
     * @param publicKey clave pública a guardar
     * @throws IOException si hay error al escribir el archivo
     */
    private static void guardarClavePublicaBinario(String fileName, PublicKey publicKey) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(publicKey.getEncoded());
            LOG.info("Clave pública guardada en formato binario: " + fileName);
        }
    }
}
