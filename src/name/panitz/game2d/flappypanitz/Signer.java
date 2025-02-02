package name.panitz.game2d.flappypanitz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Signer {

    public static PrivateKey loadPrivateKey(String pemResourcePath) throws Exception {
        InputStream is = Signer.class.getResourceAsStream(pemResourcePath);
        if (is == null) {
            throw new Exception("Private key file not found: " + pemResourcePath);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("-----BEGIN") || line.contains("-----END")) {
                continue;
            }
            sb.append(line.trim());
        }
        br.close();
        byte[] keyBytes = Base64.getDecoder().decode(sb.toString());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static String signData(String data, PrivateKey key) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signedBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signedBytes);
    }

}

