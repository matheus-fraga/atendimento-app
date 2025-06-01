package com.atendimento.app.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.atendimento.app.entities.User;

/**
 * Implementação de {@link UserDetails} para adaptar a entidade {@link User}
 * ao contrato do Spring Security.
 */
public class UserPrincipal implements UserDetails {

    private final User user;

    /**
     * Construtor para UserPrincipal.
     *
     * @param user A entidade {@link User} que será adaptada.
     */
    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * Retorna as autoridades (roles) do usuário.
     *
     * @return Uma coleção de {@link GrantedAuthority} contendo os papéis do usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna uma lista com o papel do usuário
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    /**
     * Retorna a senha do usuário.
     *
     * @return A senha do usuário.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retorna o nome de usuário.
     *
     * @return O nome de usuário.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indica se a conta do usuário está expirada.
     *
     * @return {@code true} se a conta não estiver expirada, {@code false} caso contrário.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Alterar conforme sua lógica de negócios
    }

    /**
     * Indica se a conta do usuário está bloqueada.
     *
     * @return {@code true} se a conta não estiver bloqueada, {@code false} caso contrário.
     */
    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    /**
     * Indica se as credenciais do usuário estão expiradas.
     *
     * @return {@code true} se as credenciais não estiverem expiradas, {@code false} caso contrário.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Alterar conforme sua lógica de negócios
    }

    /**
     * Indica se o usuário está habilitado.
     *
     * @return {@code true} se o usuário estiver habilitado, {@code false} caso contrário.
     */
    @Override
    public boolean isEnabled() {
        return true; // Alterar conforme sua lógica de negócios
    }

    /**
     * Retorna a entidade {@link User} associada.
     *
     * @return A entidade {@link User}.
     */
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "username='" + user.getUsername() + '\'' +
                ", role=" + user.getRole() +
                ", isLocked=" + user.isLocked() +
                '}';
    }
}