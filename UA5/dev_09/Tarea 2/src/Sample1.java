/*
 * Copyright 2018 David Alberto Cruz Barranco.
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
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David Alberto Cruz Barranco
 * @version 1.0
 */
public class Sample1 {

    private static final Logger LOG            = Logger.getLogger(Sample1.class.getName());
    private static final String KEY_ALGORITHM  = "RSA";
    private static final int    KEY_SIZE       = 2048;
    private static final String PUB_KEY_FILE   = "id_rsa.pub";
    private static final String PRIV_KEY_FILE  = "id_rsa.key";
    private static final String PUB_KEY_HEADER = "rsa public key";
    private static final String PRIV_KEY_HEADER= "rsa private key";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Generating RSA Public and Private keys
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);

        KeyPair    keyPair    = keyPairGenerator.generateKeyPair();
        PublicKey  publicKey  = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Saving RSA Public and Private keys
        saveKey(publicKey,  PUB_KEY_FILE,  PUB_KEY_HEADER);
        LOG.info(String.format("Public key format: %s",  publicKey.getFormat()));

        saveKey(privateKey, PRIV_KEY_FILE, PRIV_KEY_HEADER);
        LOG.info(String.format("Private key format: %s", privateKey.getFormat()));
    }

    private static void saveKey(Key key, String fileName, String header) {
        Base64.Encoder encoder       = Base64.getEncoder();
        String         headerUpper   = header.toUpperCase();
        String         encodedKey    = encoder.encodeToString(key.getEncoded());

        try (FileWriter out = new FileWriter(fileName)) {
            out.write(String.format("----%s %s----", "BEGIN", headerUpper));
            out.write("\n");
            out.write(encodedKey);
            out.write("\n");
            out.write(String.format("----%s %s----", "END", headerUpper));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}