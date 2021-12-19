package com.harshal.jsonWebTokenServer.domain;

import java.security.Key;

public interface KeyService {
    Key getPrivateKey();

    Key getPublicKey();

    String getPublicKeyString();
}
