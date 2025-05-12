package com.uos.dsd.cinema.core.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uos.dsd.cinema.integration.IntegrationTest;

import org.springframework.beans.factory.annotation.Autowired;

import org.jasypt.encryption.StringEncryptor;

import org.junit.jupiter.api.Test;

public class JasyptConfigTest extends IntegrationTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void testEncrypt() {

        // given
        String sample = "sample";

        // when
        String encryptedSample = stringEncryptor.encrypt(sample);
        String decryptedSample = stringEncryptor.decrypt(encryptedSample);

        // then
        assertEquals(sample, decryptedSample);
    }
}
