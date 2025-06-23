package com.atendimento.app.repositories;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atendimento.app.entities.User;

/**
 * Repositório para a entidade {@link User}.
 * <p>
 * Esta interface fornece métodos para operações de persistência
 * e consultas personalizadas relacionadas aos usuários.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username O nome de usuário.
     * @return Um {@link Optional} contendo o usuário, se encontrado.
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca um usuário pelo nome de usuário que não esteja bloqueado.
     *
     * @param username O nome de usuário.
     * @return Um {@link Optional} contendo o usuário, se encontrado e não bloqueado.
     */
    Optional<User> findByUsernameAndIsLockedFalse(String username);

    /**
     * Busca todos os usuários que estão bloqueados.
     *
     * @return Uma lista de usuários bloqueados.
     */
    List<User> findAllByIsLockedTrue();

    /**
     * Busca todos os usuários que possuem um papel específico.
     *
     * @param role O papel do usuário.
     * @return Uma lista de usuários com o papel especificado.
     */
    List<User> findAllByRole(com.atendimento.app.entities.Role role);
}