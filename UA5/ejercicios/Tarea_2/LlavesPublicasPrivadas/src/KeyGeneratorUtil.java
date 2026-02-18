package com.marissa.hash_api.Utils;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGeneratorUtil {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);

        KeyPair pair = keyGen.generateKeyPair();

        // Guardar clave privada
        try (FileOutputStream fos = new FileOutputStream("private.key")) {
            fos.write(pair.getPrivate().getEncoded());
        }

        // Guardar clave pública
        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(pair.getPublic().getEncoded());
        }

        System.out.println("Claves generadas correctamente");
    }
}
