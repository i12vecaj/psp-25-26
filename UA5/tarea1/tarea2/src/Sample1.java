/*
 * Copyright 2018 Gustavo Pacheco.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0
 */
public class Sample1 {

    private static final Logger LOG = Logger.getLogger(Sample1.class.getName());

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Generating RSA Public and Private keys
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Saving RSA Public and Private keys
        saveKey(publicKey, "id_rsa.pub", "rsa public key");
        LOG.info(String.format("Public key format: %s", publicKey.getFormat()));

        saveKey(privateKey, "id_rsa.key", "rsa private key");
        LOG.info(String.format("Private key format: %s", privateKey.getFormat()));
    }

    private static void saveKey(Key key, String fileName, String header) {
        Base64.Encoder encoder = Base64.getEncoder();
        final String FORMAT = "----%s %s----";
        try (FileWriter out = new FileWriter(fileName)) {
            out.write(String.format(FORMAT, "BEGIN", header.toUpperCase()));
            out.write("\n");

            out.write(encoder.encodeToString(key.getEncoded()));
            out.write("\n");

            out.write(String.format(FORMAT, "END", header.toUpperCase()));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}