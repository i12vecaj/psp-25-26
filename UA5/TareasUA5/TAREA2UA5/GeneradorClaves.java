package org.example.generadorclaves;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class GeneradorClaves {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(1024);
        KeyPair parDeClaves = generador.generateKeyPair();

        PublicKey clavePublica = parDeClaves.getPublic();
        PrivateKey clavePrivada = parDeClaves.getPrivate();

        guardarClaveEnTextPlano(clavePrivada.getEncoded(), "clave_privada.txt", "PRIVATE");
        guardarClaveEnTextPlano(clavePublica.getEncoded(), "clave_publica.txt", "PUBLIC");

        guardarClaveEnBinario(clavePrivada.getEncoded(), "clave_privada.key");
        guardarClaveEnBinario(clavePublica.getEncoded(), "clave_publica.key");

        System.out.println("Formato clave publica:  " + clavePublica.getFormat());
        System.out.println("Formato clave privada:  " + clavePrivada.getFormat());
        System.out.println("Claves generadas y guardadas correctamente.");
    }

    static void guardarClaveEnTextPlano(byte[] bytes, String nombreArchivo, String tipo) throws Exception {
        Base64.Encoder codificador = Base64.getEncoder();
        FileWriter escritor = new FileWriter(nombreArchivo);
        escritor.write("-----BEGIN RSA " + tipo + " KEY-----\n");
        escritor.write(codificador.encodeToString(bytes));
        escritor.write("\n-----END RSA " + tipo + " KEY-----\n");
        escritor.close();
    }

    static void guardarClaveEnBinario(byte[] bytes, String nombreArchivo) throws Exception {
        FileOutputStream salida = new FileOutputStream(nombreArchivo);
        salida.write(bytes);
        salida.close();
    }
}