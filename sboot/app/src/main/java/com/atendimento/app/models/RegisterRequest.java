package com.atendimento.app.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import com.atendimento.app.entities.Role;

/**
 * Classe para representar os dados de registro de um novo usuário.
 * 
 * <p>Esta classe é usada para capturar as informações enviadas pelo cliente
 * ao registrar um novo usuário, como nome de usuário, senha e papel.</p>
 * 
 * <p>Validações:</p>
 * <ul>
 *   <li>O nome de usuário deve ter entre 3 e 50 caracteres.</li>
 *   <li>A senha deve ter pelo menos 8 caracteres.</li>
 *   <li>O papel do usuário é obrigatório e deve ser válido.</li>
 * </ul>
 */
public class RegisterRequest {

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres")
    private final String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private final String password;

    @NotNull(message = "O papel do usuário é obrigatório")
    private final Role role;

    /**
     * Construtor para RegisterRequest.
     *
     * @param username O nome de usuário.
     * @param password A senha.
     * @param role O papel do usuário.
     */
    public RegisterRequest(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Retorna o nome de usuário.
     *
     * @return O nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retorna a senha.
     *
     * @return A senha.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retorna o papel do usuário.
     *
     * @return O papel do usuário.
     */
    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}