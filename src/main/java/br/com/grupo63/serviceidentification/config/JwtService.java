package br.com.grupo63.serviceidentification.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Service
public class JwtService {

    @Value("${jwt.token.key.public}")
    private String jwtSigningKey;

    public Claims getClaims(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
