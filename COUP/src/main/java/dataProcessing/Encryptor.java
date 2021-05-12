package dataProcessing;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Encryptor
{
    //TODO - Move to Aynur's project
    public static byte[] getPBKDF2SecurePassword(String userName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
            byte[] salt;
            salt = userName.getBytes(StandardCharsets.UTF_8);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
    }
}
