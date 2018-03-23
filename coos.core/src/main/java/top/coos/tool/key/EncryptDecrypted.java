package top.coos.tool.key;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class EncryptDecrypted implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7477307115829913562L;

    /**
     * 加密
     * 
     * @param src
     * @param enKey
     * @return
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     */
    public byte[] Encrypt(byte[] src, byte[] enKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        DESedeKeySpec dks = new DESedeKeySpec(enKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(src);
    }

    /**
     * 解密
     * 
     * @param debase64
     * @param spKey
     * @return
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] Decrypted(byte[] debase64, byte[] spKey) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("DESede");
        DESedeKeySpec dks = new DESedeKeySpec(spKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey sKey = keyFactory.generateSecret(dks);
        cipher.init(Cipher.DECRYPT_MODE, sKey);
        byte ciphertext[] = cipher.doFinal(debase64);
        return ciphertext;
    }

}
