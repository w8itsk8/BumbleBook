package com.example.bumblebook;
// created by Kate Wheeler 7 Oct 2026
// BumbleBook Book Inventory Application
// Password hasher generates strings on which to base encryption and encrypts passwords

// Import libraries
import android.util.Base64;
import android.util.Log;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// Instantiate class
public class PasswordHasher {

    private static final int SALT_LENGTH_BYTES = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH_BITS = 128;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    // Generate new random string on which to base algorithm
    public static String generateSalt() {

        byte[] salt = new byte[SALT_LENGTH_BYTES];
        new SecureRandom().nextBytes(salt);
        return Base64.encodeToString(salt, Base64.NO_WRAP);

    }

    // Matches password with salt
    public static String hash(String password, String saltBase64) {

        // catch errors ashing password
        try {

            byte[] salt = Base64.decode(saltBase64, Base64.NO_WRAP);

            // package attributes to pass to encryption
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH_BITS
            );

            // create and call instance of encryption algo
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hashBytes = factory.generateSecret(spec).getEncoded();

            return Base64.encodeToString(hashBytes, Base64.NO_WRAP);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

            // if encryption failed, log error message and return
            Log.e("PasswordHasher", "Failer to hash password: " + e.getMessage());
            return null;

        }

    }

    // Validate typed password with stored password
    public static boolean verify(String password, String saltBase64, String expectedHash) {

        String actualHash = hash(password, saltBase64);
        return actualHash != null && actualHash.equals(expectedHash);

    }

    // prevent instantiation
    private PasswordHasher() {}

}
