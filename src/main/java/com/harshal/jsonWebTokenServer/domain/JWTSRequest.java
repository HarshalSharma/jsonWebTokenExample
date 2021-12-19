package com.harshal.jsonWebTokenServer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class JWTSRequest {

    private String subject;

    private Map<String, Object> customHeader;

    private Map<String, String> customClaims;

    private Date expiration;

    private String payload;

    public Optional<String> getSubject() {
        return Optional.ofNullable(subject);
    }

    public Optional<Map<String, Object>> getCustomHeader() {
        if(customHeader == null || customHeader.size() == 0)
            return Optional.empty();
        return Optional.of(customHeader);
    }

    public Optional<Map<String, String>> getCustomClaims() {
        if(customClaims == null || customClaims.size() == 0)
            return Optional.empty();
        return Optional.of(customClaims);
    }

    public Optional<Date> getExpiration() {
        return Optional.ofNullable(expiration);
    }

    public Optional<String> getPayload() {
        if(payload == null || payload.isEmpty())
            return Optional.empty();
        return Optional.of(payload);
    }

}
