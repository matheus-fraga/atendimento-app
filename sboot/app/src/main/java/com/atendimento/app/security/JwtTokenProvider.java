package com.atendimento.app.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.refresh.expiration.ms}")
    private long refreshExpirationMs;

    @Value("${jwt.expiration.ms}")
    private long expirationMs;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    /**
     * Gera um token JWT para o usuário autenticado.
     *
     * @param authentication Dados de autenticação do usuário.
     * @return Token JWT.
     */
    public String generateToken(Authentication authentication) {
        // Obtém o principal (usuário autenticado) do objeto de autenticação
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // Gera o token JWT
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // Define o "subject" como o nome de usuário
                .claim("role", userPrincipal.getUser().getRole().name()) // Adiciona o papel como uma claim
                .setIssuedAt(new Date()) // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // Data de expiração
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256) // Assina o token
                .compact();
    }

    /**
     * Extrai o nome de usuário (subject) de um token JWT.
     *
     * @param token Token JWT.
     * @return Nome de usuário contido no token.
     */
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            logger.error("Erro ao extrair o nome de usuário do token. Erro: {}", e.getMessage());
            throw new IllegalArgumentException("Token inválido ou mal formatado");
        }
    }

    /**
     * Valida o token JWT.
     *
     * @param token Token JWT.
     * @return `true` se o token for válido, `false` caso contrário.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token expirado: {}", e.getMessage());
        } catch (SecurityException e) {
            logger.warn("Falha de segurança no token: {}", e.getMessage());
        } catch (Exception e) {
            logger.warn("Falha na validação do token. Erro: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Gera um token de atualização (refresh token) para o usuário autenticado.
     *
     * @param authentication Dados de autenticação do usuário.
     * @return Refresh Token JWT.
     */
    public String generateRefreshToken(Authentication authentication) {
        // Obtém o principal (usuário autenticado) do objeto de autenticação
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retorna o tempo de expiração do token JWT em milissegundos.
     *
     * @return Tempo de expiração em milissegundos.
     */
    public long getExpirationMs() {
        return expirationMs;
    }

    /**
     * Retorna o tempo de expiração do refresh token em milissegundos.
     *
     * @return Tempo de expiração do refresh token em milissegundos.
     */
    public long getRefreshExpirationMs() {
        return refreshExpirationMs;
    }
}