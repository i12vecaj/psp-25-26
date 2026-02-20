package com.generador.claves;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

public class KeyGeneratorApp {
    private static final Logger LOG = Logger.getLogger(KeyGeneratorApp.class.getName());

    public static void main(String[] args) {
        int keySize = 2048; // por defecto seguro (recomendado)
        String outDir = ".";
        if (args.length > 0) {
            outDir = args[0];
        }
        if (args.length > 1) {
            try {
                keySize = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                LOG.log(Level.WARNING, "Segundo argumento no es un entero, se usa {0}", keySize);
            }
        }

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            LOG.info(String.format("Public key format: %s", publicKey.getFormat()));
            LOG.info(String.format("Private key format: %s", privateKey.getFormat()));

            File dir = new File(outDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File pemPrivate = new File(dir, "private_key.pem");
            File pemPublic = new File(dir, "public_key.pem");
            File binPrivate = new File(dir, "private_key.bin");
            File binPublic = new File(dir, "public_key.bin");

            // Usar encabezados genericos que reflejan los formatos PKCS#8 (privada) y X.509 (publica)
            writePem(privateKey, pemPrivate, "PRIVATE KEY");
            writePem(publicKey, pemPublic, "PUBLIC KEY");

            writeBinary(privateKey, binPrivate);
            writeBinary(publicKey, binPublic);

            System.out.println("Llaves generadas en: " + dir.getAbsolutePath());
            System.out.println("Archivos: " + pemPrivate.getName() + ", " + pemPublic.getName() + 
                    ", " + binPrivate.getName() + ", " + binPublic.getName());

            // Cargar las claves desde los archivos binarios y demostrar cifrado/descifrado
            PublicKey loadedPublic = loadPublicKey(binPublic.getAbsolutePath());
            PrivateKey loadedPrivate = loadPrivateKey(binPrivate.getAbsolutePath());

            String mensaje = "Prueba de cifrado/descifrado desde KeyGeneratorApp.";
            String cifrado = encrypt(mensaje, loadedPublic);
            String descifrado = decrypt(cifrado, loadedPrivate);

            System.out.println("\nMensaje original: " + mensaje);
            System.out.println("Mensaje cifrado (base64): " + cifrado);
            System.out.println("Mensaje descifrado: " + descifrado);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private static void writePem(Key key, File file, String header) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter(file)) {
            out.write("-----BEGIN " + header + "-----");
            out.write(System.lineSeparator());
            out.write(encoder.encodeToString(key.getEncoded()));
            out.write(System.lineSeparator());
            out.write("-----END " + header + "-----");
            out.write(System.lineSeparator());
        }
    }

    private static void writeBinary(Key key, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(key.getEncoded());
        }
    }

    private static PublicKey loadPublicKey(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private static PrivateKey loadPrivateKey(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private static String encrypt(String plain, PublicKey pub) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        byte[] out = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(out);
    }

    private static String decrypt(String cipherText, PrivateKey priv) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priv);
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        byte[] out = cipher.doFinal(decoded);
        return new String(out, StandardCharsets.UTF_8);
    }
}
