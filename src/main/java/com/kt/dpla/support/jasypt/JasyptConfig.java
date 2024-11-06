package com.kt.dpla.support.jasypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${dpla.support.aes256ciper.secretKey}")
    private String secretKey;

    @Bean
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setAlgorithm("PBEWithSHA256And256BitAES-CBC-BC");
        encryptor.setPassword(secretKey);
        encryptor.setPoolSize(2);

        return encryptor;
    }
}
