package com.kt.dpla.support.jasypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;

public class Test111 {

    public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setAlgorithm("PBEWITHSHA256AND256BITAES-CBC-BC");
        encryptor.setPassword("lHHO/UpOANsEBJjZj3+7c4vFbmsGeiwOhRyQb1LkTjE=");
//        encryptor.setIvGenerator(new StringFixedIvGenerator("u11pymdr0nynia34"));
        encryptor.setPoolSize(2);

        String target = "dpla";
        System.out.println("target :: " + target);

        String encrypt = encryptor.encrypt(target);
        System.out.println("encrypt :: " + encrypt);

        String decrypt = encryptor.decrypt(encrypt);
        System.out.println("decrypt :: " + decrypt);
    }

}
