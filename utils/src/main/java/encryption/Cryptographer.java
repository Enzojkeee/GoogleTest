package encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import static java.util.Base64.*;
import static javax.crypto.Cipher.*;

/**
 * Класс осуществляющий шифровку/дешифровку данных
 */
public class Cryptographer {

    private static final byte[] keyValue = System.getenv("key").getBytes(StandardCharsets.UTF_8);
    private static final String algorithm = "AES";

    public static String encrypt(String plainText) {
        return encryptOrDecrypt("encrypt", plainText);
    }

    public static String decrypt(String encryptedText) {
        return encryptOrDecrypt("decrypt", encryptedText);
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, algorithm);
    }

    private static String encryptOrDecrypt(String command, String str){
        String result = null;
        try {
            Key key = generateKey();
            Cipher cipher = getInstance(algorithm);
            if (command.equals("encrypt")) {
                cipher.init(ENCRYPT_MODE, key);
                byte[] encVal = cipher.doFinal(str.getBytes());
                result = getEncoder().encodeToString(encVal);
            } else {
                cipher.init(DECRYPT_MODE, key);
                byte[] decordedValue = getDecoder().decode(str);
                byte[] decValue = cipher.doFinal(decordedValue);
                result = new String(decValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
