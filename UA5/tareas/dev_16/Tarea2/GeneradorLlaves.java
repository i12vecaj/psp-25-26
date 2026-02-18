package yoni;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/*
 * Este programa genera un par de claves criptográficas (pública y privada) utilizando el algoritmo RSA.
 *
 * Funcionamiento:
 * 1. Se utiliza la clase KeyPairGenerator de Java para inicializar un generador de claves con el algoritmo RSA y un tamaño de 1024 bits.
 *    RSA es un algoritmo de criptografía asimétrica ampliamente utilizado.
 * 2. Se genera el par de claves (KeyPair), que contiene tanto la clave pública como la privada.
 * 3. Las claves se extraen del par generado.
 * 4. Se muestran por consola algunos detalles de las claves, incluyendo un fragmento de su representación codificada para verificación visual.
 * 5. Finalmente, las claves se guardan en archivos físicos en el disco. Se utiliza FileOutputStream para escribir los bytes crudos de las claves.
 *
 * El propósito de esto es permitir el cifrado asimétrico: lo que se cifra con la clave pública solo puede descifrarse con la clave privada,
 * y viceversa (para firmas digitales). Esto asegura que solo el poseedor de la clave privada pueda acceder a la información cifrada para él.
 */
public class GeneradorLlaves {

    // Nombres de los archivos donde se guardarán las claves
    private static final String ARCHIVO_CLAVE_PUBLICA = "publicKey";
    private static final String ARCHIVO_CLAVE_PRIVADA = "privateKey";

    public static void main(String[] args) {
        try {
            // 1. Crear el generador de claves para el algoritmo RSA
            // KeyPairGenerator es una clase de fábrica utilizada para generar pares de claves públicas y privadas.
            KeyPairGenerator generadorParDeClaves = KeyPairGenerator.getInstance("RSA");

            // 2. Inicializar el generador con un tamaño de clave de 1024 bits
            // Un tamaño mayor (como 2048) es más seguro, pero 1024 es suficiente para ejemplos básicos.
            generadorParDeClaves.initialize(1024);

            // 3. Generar el par de claves
            KeyPair parDeClaves = generadorParDeClaves.generateKeyPair();

            // 4. Obtener las claves individuales del par
            PublicKey clavePublica = parDeClaves.getPublic();
            PrivateKey clavePrivada = parDeClaves.getPrivate();

            System.out.println("Generando par de claves RSA...");
            System.out.println("Algoritmo: " + clavePublica.getAlgorithm());
            System.out.println("Formato clave pública: " + clavePublica.getFormat());
            System.out.println("Formato clave privada: " + clavePrivada.getFormat());

            // Mostrar un fragmento de las claves (primeros caracteres en Base64)
            // Esto sirve para verificar visualmente que se han generado datos.
            mostrarFragmentoClave("Pública", clavePublica);
            mostrarFragmentoClave("Privada", clavePrivada);

            // 5. Guardar las claves en archivos
            // Se guardan en el sistema de archivos para poder ser usadas posteriormente por otros programas.
            guardarClaveEnArchivo(clavePublica, ARCHIVO_CLAVE_PUBLICA);
            guardarClaveEnArchivo(clavePrivada, ARCHIVO_CLAVE_PRIVADA);

        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println("Error al generar o guardar las claves: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra los primeros caracteres de la clave codificada en Base64.
     *
     * @param tipo El tipo de clave (Pública o Privada) para el mensaje.
     * @param clave La clave de la cual mostrar el fragmento.
     */
    private static void mostrarFragmentoClave(String tipo, Key clave) {
        // Codificamos la clave a Base64 para que sea legible como texto
        String claveBase64 = Base64.getEncoder().encodeToString(clave.getEncoded());
        // Tomamos los primeros 20 caracteres (o menos si la clave fuera muy corta)
        String fragmento = claveBase64.length() > 20 ? claveBase64.substring(0, 20) : claveBase64;
        System.out.println("Fragmento de clave " + tipo + ": " + fragmento + "...");
    }

    /**
     * Guarda una clave (pública o privada) en un archivo binario.
     *
     * @param clave La clave que se va a guardar.
     * @param nombreArchivo El nombre del archivo donde se guardará.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    private static void guardarClaveEnArchivo(Key clave, String nombreArchivo) throws IOException {
        File archivo = new File(nombreArchivo);
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            // Escribimos la representación codificada de la clave (bytes) en el archivo
            fos.write(clave.getEncoded());
        }
        // Mostramos la ruta absoluta para que el usuario sepa exactamente dónde quedó el archivo
        System.out.println("Clave guardada correctamente en: " + archivo.getAbsolutePath());
    }

    /**
     * Guarda una clave en formato Base64 (opcional, estilo PEM).
     * Esta función convierte los bytes de la clave a texto Base64 y añade cabeceras.
     *
     * @param clave La clave a guardar.
     * @param nombreArchivo El nombre del archivo destino.
     * @param tipoClave El tipo de clave (ej. "PUBLIC KEY" o "PRIVATE KEY") para la cabecera.
     * @throws IOException Si hay error de escritura.
     */
    private static void guardarClaveEnArchivoBase64(Key clave, String nombreArchivo, String tipoClave) throws IOException {
        Base64.Encoder codificador = Base64.getEncoder();
        File archivo = new File(nombreArchivo);
        try (FileWriter escritor = new FileWriter(archivo)) {
            escritor.write("-----BEGIN " + tipoClave + "-----\n");
            escritor.write(codificador.encodeToString(clave.getEncoded()));
            escritor.write("\n-----END " + tipoClave + "-----\n");
        }
        System.out.println("Clave Base64 guardada correctamente en: " + archivo.getAbsolutePath());
    }
}