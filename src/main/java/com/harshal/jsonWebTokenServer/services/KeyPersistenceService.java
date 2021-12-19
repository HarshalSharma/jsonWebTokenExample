package com.harshal.jsonWebTokenServer.services;

import com.harshal.jsonWebTokenServer.domain.StoredKeyPair;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

@Component
public class KeyPersistenceService {

    @Autowired
    Environment environment;

    final String keyPairFilePath = "keyPairFilepath";

    public void saveKeyToDisk(KeyPair keyPair){
        String publicKey = Encoders.BASE64.encode(keyPair.getPublic().getEncoded());
        String privateKey = Encoders.BASE64.encode(keyPair.getPrivate().getEncoded());
        storeKeyPairToDisk(new StoredKeyPair(publicKey, privateKey));
    }

    public KeyPair getKeyPairFromDisk(){
        StoredKeyPair storedKeyPair = loadKeyPairFromDisk();
        if(storedKeyPair == null){
            throw new RuntimeException("KeyPair Not Found !");
        }
        byte[] publicKeyBytes = Decoders.BASE64.decode(storedKeyPair.getPublicKey());
        byte[] privateKeyBytes = Decoders.BASE64.decode(storedKeyPair.getPrivateKey());
        PublicKey publicKey;
        PrivateKey privateKey;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new KeyPair(publicKey, privateKey);
    }

    private StoredKeyPair loadKeyPairFromDisk(){
        File file = new File(Objects.requireNonNull(environment.getProperty(keyPairFilePath)));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new FileInputStream(file), StoredKeyPair.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void storeKeyPairToDisk(StoredKeyPair storedKeyPair){
        File file = new File(Objects.requireNonNull(environment.getProperty(keyPairFilePath)));
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, storedKeyPair);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
