package com.harshal.jsonWebTokenServer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class StoredKeyPair {

    private String publicKey;
    private String privateKey;

    public StoredKeyPair(@JsonProperty("publicKey") String publicKey, @JsonProperty("privateKey") String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
