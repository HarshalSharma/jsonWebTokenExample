package com.harshal.jsonWebTokenServer.apis;

import com.harshal.jsonWebTokenServer.domain.JWTSRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface JsonWebTokenServerApis {

    @GetMapping("/jwts/publicKey")
    String getPublicJsonWebToken();

    @PostMapping("/jwts/getHashedJWS")
    String getHashedJWSResponse(@RequestBody JWTSRequest body);

    @PostMapping(value = "/jwts/isTampered", produces = "application/json")
    String isTampered(@RequestBody String jwsBody);

}
