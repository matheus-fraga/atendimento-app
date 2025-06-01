package com.atendimento.app.models;

import java.util.Objects;

/**
 * Classe para representar a resposta do token JWT.
 * 
 * <p>Essa classe é usada para encapsular informações sobre o token JWT retornado
 * para o cliente após uma autenticação bem-sucedida.</p>
 */
public class TokenResponse {
    private final String token;
    private final long expiresIn;

    /**
     * Construtor para TokenResponse.
     *
     * @param token     O token JWT.
     * @param expiresIn O tempo de expiração do token (em segundos).
     * @throws IllegalArgumentException Se o token for nulo ou vazio, ou se o tempo de expiração for negativo.
     */
    public TokenResponse(String token, long expiresIn) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("O token não pode ser nulo ou vazio.");
        }
        if (expiresIn < 0) {
            throw new IllegalArgumentException("O tempo de expiração deve ser positivo.");
        }
        this.token = token;
        this.expiresIn = expiresIn;
    }

    /**
     * Retorna o token JWT.
     *
     * @return Token JWT.
     */
    public String getToken() {
        return token;
    }

    /**
     * Retorna o tempo de expiração do token (em segundos).
     *
     * @return Tempo de expiração em segundos.
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Verifica a igualdade entre dois objetos TokenResponse.
     *
     * @param o Objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenResponse that = (TokenResponse) o;
        return expiresIn == that.expiresIn && Objects.equals(token, that.token);
    }

    /**
     * Gera o hashcode para o objeto TokenResponse.
     *
     * @return Hashcode gerado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(token, expiresIn);
    }

    /**
     * Retorna uma representação em string do objeto TokenResponse.
     *
     * @return Representação em string.
     */
    @Override
    public String toString() {
        return "TokenResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}