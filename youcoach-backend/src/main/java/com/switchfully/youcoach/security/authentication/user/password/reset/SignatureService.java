package com.switchfully.youcoach.security.authentication.user.password.reset;

import com.switchfully.youcoach.security.authentication.user.exception.SigningFailedException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Service
public class SignatureService {

    private final KeyPair keyPair;

    public SignatureService(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public boolean verifyBased64SignaturePasses(String signature, String value) {
        Objects.requireNonNull(signature);
        Objects.requireNonNull(value);

        byte[] decoded = Base64.getDecoder().decode(signature);
        byte[] toVerifyAgainst = value.getBytes(StandardCharsets.UTF_8);

        try {
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initVerify(this.keyPair.getPublic());
            sig.update(toVerifyAgainst);

            return sig.verify(decoded) && (Arrays.equals(decoded, digitallySign(value)));
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String digitallySignAndEncodeBase64(String value) {
        return Base64.getEncoder().encodeToString(digitallySign(value));
    }

    private Signature generateSignature(String value) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Objects.requireNonNull(value);
        byte[] data = value.getBytes(StandardCharsets.UTF_8);


        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(this.keyPair.getPrivate());
        sig.update(data);
        return sig;
    }

    private byte[] digitallySign(String value) {
        try {
            return generateSignature(value).sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            SigningFailedException sfe = new SigningFailedException(e.getMessage());
            sfe.addSuppressed(e);
            throw sfe;
        }
    }
}
