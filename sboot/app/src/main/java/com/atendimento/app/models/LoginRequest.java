package com.atendimento.app.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Classe para representar os dados de login do usuário.
 * 
 * <p>Esta classe é usada para capturar as credenciais de login enviadas
 * pelo cliente, como nome de usuário e senha. Ambos os campos são obrigatórios.</p>
 */
public class LoginRequest {

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres")
    private final String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private final String password;

    /**
     * Construtor para LoginRequest.
     *
     * @param username Nome de usuário.
     * @param password Senha.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retorna o nome de usuário.
     *
     * @return Nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retorna a senha.
     *
     * @return Senha.
     */
    public String getPassword() {
        return password;
    }
}