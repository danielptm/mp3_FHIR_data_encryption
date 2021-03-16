package com.encryption.demo;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class EncryptionService {

    public EncryptionService() {

    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(3000, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }

    public static String encryptChunks(String text, KeyPair keyPair) throws Exception {
        String result = "";
        int size = 100;
        for (int i = 0; i < text.length(); i+=size) {
            if (text.length() -  i > size) {
                String substring = text.substring(i, i + size);
                String chunk = encrypt(substring, keyPair.getPublic());
                result = result + chunk + "...";
            } else {
                String substring = text.substring(i);
                String chunk = encrypt(substring, keyPair.getPublic());
                result = result + chunk + "...";
            }
        }

        return result;
    }

    public static String decryptChunks(String text, KeyPair keyPair) throws Exception {
        String result = "";
        List<String> chunks = Arrays.asList(text.split("..."));
        for(String c: chunks) {
            String decrypted = decrypt(c, keyPair.getPrivate());
            result += decrypted;
        }
        return result;
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    private static final String initVector = "encryptionIntVec";

    //Tips for getting the AES encryption to work was taken from this website. https://www.javacodegeeks.com/2018/03/aes-encryption-and-decryption-in-javacbc-mode.html
    public static String aesEncrypt(String text, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String aesDecrypt(String encryptedText, String key) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
