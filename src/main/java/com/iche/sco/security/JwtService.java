package com.iche.sco.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    @Value("${jwt.expiration.access_token}")
    private long accessTokenExpiration;
    @Value("${jwt.expiration.refresh_token}")
    private long refreshTokenExpiration;

    @Value("${jwt.secret_key}")
    private String generateSecret;

    public Key getSignInKey(){
        byte [] signedInkey  = Decoders.BASE64.decode(generateSecret);
        return Keys.hmacShaKeyFor(signedInkey);
    }

    public Claims extractAllClaim(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        Claims claims = extractAllClaim(token);
        return claims.getSubject();
    }

    public <T> T extractSingleClaim(String token, Function<Claims, T>  claimResolver){
        Claims claims = extractAllClaim(token);
        return claimResolver.apply(claims);
    }
    public Date extractExpiration(String token){
        return extractSingleClaim(token, Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String buildToken(
            UserDetails userDetails,
            Map<String, Object> extractClaims,
            long expiration){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getExtractedClaims(
            UserDetails userDetails,
            Map<String, Object> extractClaim,
            long expiration){
        Set<String> role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        extractClaim.put("role", role);
        return buildToken(userDetails,extractClaim,expiration);
    }
    public String generateToken(UserDetails userDetails){
        return getExtractedClaims(userDetails,new HashMap<>(),accessTokenExpiration);

    }
    public String generateRefreshToken(
            UserDetails userDetails
    ){
        return buildToken(userDetails,new HashMap<>(),refreshTokenExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
}
