package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.security.verification.exception.SigningFailedException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Service
public class VerificationService {

    private final KeyPair keyPair;
    private final boolean signingAndVerifyingAvailable;

    public boolean isSigningAndVerifyingAvailable() {
        return signingAndVerifyingAvailable;
    }

    public VerificationService() {
        KeyPairGenerator kpg;
        KeyPair kp = null;
        boolean available = true;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            kp = kpg.genKeyPair();
        } catch (NoSuchAlgorithmException ignore) {
            available = false;
        } finally {
            this.keyPair = kp;
            signingAndVerifyingAvailable = available;
        }
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


    public Signature generateSignature(String value) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Objects.requireNonNull(value);
        byte[] data = value.getBytes(StandardCharsets.UTF_8);


        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(this.keyPair.getPrivate());
        sig.update(data);
        return sig;
    }

    public byte[] digitallySign(String value) {
        try {
            return generateSignature(value).sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            SigningFailedException sfe = new SigningFailedException(e.getMessage());
            sfe.addSuppressed(e);
            throw sfe;
        }
    }

    public String digitallySignAndEncodeBase64(String value) {
        return Base64.getEncoder().encodeToString(digitallySign(value));
    }

    public boolean isEmailValid(String email) {
        if (email == null) return false;

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isPasswordValid(String password) {
        if (password == null) return false;

        String pattern = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }
}
