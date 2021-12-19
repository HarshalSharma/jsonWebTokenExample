package com.harshal.jsonWebTokenServer.services;

import com.harshal.jsonWebTokenServer.apis.JsonWebTokenServerApis;
import com.harshal.jsonWebTokenServer.domain.JWTSRequest;
import com.harshal.jsonWebTokenServer.domain.KeyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonWebTokenServerApisImpl implements JsonWebTokenServerApis {

    @Autowired
    private KeyService keyService;

    @Override
    public String getPublicJsonWebToken() {
        return keyService.getPublicKeyString();
    }

    @Override
    public String getHashedJWSResponse(JWTSRequest body) {
        JwtBuilder builder = Jwts.builder();
        body.getCustomHeader().ifPresent(builder::setHeaderParams);
        body.getCustomClaims().ifPresent(builder::setClaims);
        body.getSubject().ifPresent(builder::setSubject);
        body.getExpiration().ifPresent(builder::setExpiration);
        body.getPayload().ifPresent(builder::setPayload);
        try {
            return builder.signWith(keyService.getPrivateKey(), SignatureAlgorithm.PS256).compact();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    @Override
    public String isTampered(String jwsBody) {
        try {
            Jwts.parserBuilder().setSigningKey(keyService.getPublicKey())
                    .build().parseClaimsJws(jwsBody);
            return tamperingResponse(true);
        } catch (JwtException e) {
            //tampered
        }
        return tamperingResponse(false);
    }

    private String tamperingResponse(boolean isValid) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.set("isTampered", BooleanNode.valueOf(!isValid));
        try {
            return mapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
