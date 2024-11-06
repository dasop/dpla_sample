package com.kt.dpla.support.jasypt;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyGeneratorService {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        SecretKey secretKey = generateSecretKey();
        byte[] keyBytes = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);

        System.out.println("Secret Key: " + encodedKey);

        String iv = iv();
        System.out.println("iv: " + iv);
    }

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        String ALGORITHM = "AES";
        int KEY_SIZE = 256;

        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }

    public static String iv() {
        Random rnd = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            if (rnd.nextBoolean()) {
                sb.append((char) ((int) (rnd.nextInt(26)) + 97));
            } else {
                sb.append((rnd.nextInt(10)));
            }
        }
        return sb.toString();
    }
}
