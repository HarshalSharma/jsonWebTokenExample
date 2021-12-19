package com.harshal.jsonWebTokenServer.services;

import com.harshal.jsonWebTokenServer.domain.KeyService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.security.KeyPair;

@Component
public class KeyServiceImpl implements KeyService {

    private KeyPair keyPair;

    @Autowired
    KeyPersistenceService keyPersistenceService;

    @PostConstruct
    private void setupKeyPair() {
        try {
            keyPair = keyPersistenceService.getKeyPairFromDisk();
        } catch (Exception ignored) {}

        if (keyPair == null) {
            System.out.println("Generating new KeyPair.");
            keyPersistenceService.saveKeyToDisk(Keys.keyPairFor(SignatureAlgorithm.PS256));
            keyPair = keyPersistenceService.getKeyPairFromDisk();
        }
    }

    @Override
    public Key getPrivateKey() {
        return keyPair.getPrivate();
    }

    @Override
    public Key getPublicKey() {
        return keyPair.getPublic();
    }

    @Override
    public String getPublicKeyString() {
        return "-----BEGIN PUBLIC KEY-----" + Encoders.BASE64.encode(keyPair.getPublic().getEncoded()) + "-----END PUBLIC KEY-----";
    }
}
