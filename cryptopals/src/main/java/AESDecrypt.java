package cryptopals;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;


public class AESDecrypt{

    //  throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException but I'm just going to catch all that crap...
    public static Stringform decrypt(Stringform ciphertext, Stringform key){
        byte[] result;
        try {
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            result = cipher.doFinal(ciphertext.getBytes());
        } catch (Exception e) {
            result = new byte[1];
            e.printStackTrace();
        }
        return new Stringform(result);
    }
}
