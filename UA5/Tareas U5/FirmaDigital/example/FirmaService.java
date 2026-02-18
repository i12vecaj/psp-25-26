package org.example;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.signatures.*;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class FirmaService {

public static void firmar(String inputPdf,
    String outputPdf,
    String certPath,
    String password) throws Exception {

    // Cargar certificado
    KeyStore ks = KeyStore.getInstance("PKCS12");
    ks.load(new FileInputStream(certPath), password.toCharArray());

    String alias = ks.aliases().nextElement();

    PrivateKey privateKey =
    (PrivateKey) ks.getKey(alias, password.toCharArray());

    Certificate[] chain =
    ks.getCertificateChain(alias);

    // Preparar PDF
    PdfReader reader = new PdfReader(inputPdf);
    PdfWriter writer = new PdfWriter(outputPdf);

    PdfSigner signer =
    new PdfSigner(reader, writer, new StampingProperties());

    signer.getSignatureAppearance()
    .setReason("Práctica PSP DAM")
    .setLocation("España")
    .setPageNumber(1);

    signer.setFieldName("firmaPSP");

    IExternalSignature signature =
    new PrivateKeySignature(privateKey, "SHA256", null);

    IExternalDigest digest =
    new BouncyCastleDigest();

    signer.signDetached(
                 digest,
                signature,
                chain,
                null,
                null,
                null,
                0,
                PdfSigner.CryptoStandard.CMS
        );
    }
}