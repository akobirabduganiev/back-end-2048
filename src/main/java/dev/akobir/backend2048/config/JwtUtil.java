package dev.akobir.backend2048.config;

import dev.akobir.backend2048.enums.Role;
import dev.akobir.backend2048.exception.AppBadRequestException;
import io.jsonwebtoken.*;


import java.util.Date;

public class JwtUtil {

    private final static String secretKey = "IAmWritingThisWordBecauseWeNeedSecretKeyButItWillBeLongerThanOtherWords";

    public static String encode(Long id, Role role) {

        // todo open the comment before upload project to server
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis()+(60*60*1000*24)))
                .setIssuer("Akobir Abduganiev")
                .claim("role", role)
                .compact();
    }

    public static JwtDecode decode(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

            Claims claims = jws.getBody();

            String id = claims.getSubject();
            String  role = (String) claims.get("role");

            return new JwtDecode(id, Role.valueOf(role));
        } catch (JwtException e) {
            throw new AppBadRequestException("JWT invalid!");
        }
    }

}