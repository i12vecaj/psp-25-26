import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneradorClavesRSA {

    private static final Logger LOG = Logger.getLogger(GeneradorClavesRSA.class.getName());
    private static final int KEY_SIZE = 2048;
    private static final String KEY_FOLDER = "keys";

    public static void main(String[] args) {

        try {
            // Crear carpeta keys si no existe
            crearCarpetaClaves();

            // Generar par de claves
            KeyPair pair = generarClaves();
            PublicKey clavePublica = pair.getPublic();
            PrivateKey clavePrivada = pair.getPrivate();

            System.out.println("Claves generadas correctamente\n");

            // Guardar en PEM
            guardarPEM(KEY_FOLDER + "/public_key.pem", "PUBLIC KEY", clavePublica.getEncoded());
            guardarPEM(KEY_FOLDER + "/private_key.pem", "PRIVATE KEY", clavePrivada.getEncoded());

            // Guardar en formato binario (.key)
            guardarBinario(KEY_FOLDER + "/public.key", clavePublica.getEncoded());
            guardarBinario(KEY_FOLDER + "/private.key", clavePrivada.getEncoded());

            // Mostrar formatos
            System.out.println("Formatos:");
            System.out.println("Clave pública → " + clavePublica.getFormat());
            System.out.println("Clave privada → " + clavePrivada.getFormat());

            System.out.println("\nClaves guardadas en carpeta: " + KEY_FOLDER);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error generando las claves", e);
        }
    }

    // Crear carpeta "keys" si no existe
    private static void crearCarpetaClaves() {
        File carpeta = new File(KEY_FOLDER);
        if (!carpeta.exists()) {
            carpeta.mkdir();
            System.out.println("Carpeta 'keys' creada.");
        }
    }

    // Generar par de claves RSA
    private static KeyPair generarClaves() throws NoSuchAlgorithmException {
        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(KEY_SIZE);
        return generador.generateKeyPair();
    }

    // Guardar en formato PEM (.pem)
    private static void guardarPEM(String archivo, String tipo, byte[] datos) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(datos);

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("-----BEGIN " + tipo + "-----\n");

            for (int i = 0; i < base64.length(); i += 64) {
                writer.write(base64.substring(i, Math.min(i + 64, base64.length())));
                writer.write("\n");
            }

            writer.write("-----END " + tipo + "-----\n");
        }

        LOG.info("Archivo PEM creado: " + archivo);
    }

    // Guardar en formato binario (.key)
    private static void guardarBinario(String archivo, byte[] datos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(datos);
        }
        LOG.info("Archivo binario creado: " + archivo);
    }
}
