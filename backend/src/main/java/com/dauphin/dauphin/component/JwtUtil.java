package com.dauphin.dauphin.component;

import java.util.Date;
import java.util.Map;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import com.dauphin.dauphin.exceptions.TokenFailedException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JwtUtil {
    private static final String SECRET = "super-secreta-chave-chave1234567890"; // deve ter 256 bits para HS256

    public String generateToken(Long groupId, long expirationMillis) throws Exception {
        // Data de expiração
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMillis);

        // Criar JWT com claims personalizadas
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("groupId", groupId)
                .issueTime(now)
                .expirationTime(exp)
                .build();

        // Cabeçalho
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        // JWT assinado
        SignedJWT signedJWT = new SignedJWT(header, claims);

        // Assinar com chave compartilhada
        JWSSigner signer = new MACSigner(SECRET.getBytes());
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public Long extractGroupIdFromToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
        if (!signedJWT.verify(verifier)) throw new SecurityException(TokenFailedException.TOKEN_INVALID);

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        Date now = new Date();
        if (claims.getExpirationTime().before(now)) {
            throw new SecurityException(TokenFailedException.TOKEN_EXPIRED);
        }

        return claims.getLongClaim("groupId");
    }
}
