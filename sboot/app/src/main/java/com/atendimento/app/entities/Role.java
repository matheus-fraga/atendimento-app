package com.atendimento.app.entities;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enumeração para representar diferentes papéis de usuários na aplicação.
 */
public enum Role {
    USER("Usuário"),
    ADMIN("Administrador"),
    SUPERVISOR("Supervisor");

    private final String displayName;

    /**
     * Construtor para Role.
     *
     * @param displayName Nome amigável do papel.
     */
    Role(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retorna o nome amigável do papel.
     *
     * @return Nome amigável do papel.
     */
    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Localiza um papel baseado no nome amigável.
     *
     * @param displayName Nome amigável do papel.
     * @return Um {@link Optional} contendo o papel correspondente, ou {@link Optional#empty()} 
     * caso o papel não seja encontrado.
     *
     * <p>Exemplo de uso:</p>
     * <pre>
     * Optional<Role> role = Role.fromDisplayName("Usuário");
     * role.ifPresentOrElse(
     *     r -> System.out.println("Papel encontrado: " + r),
     *     () -> System.out.println("Papel não encontrado")
     * );
     * </pre>
     */
    public static Optional<Role> fromDisplayName(String displayName) {
        return Arrays.stream(values())
            .filter(role -> role.displayName.equalsIgnoreCase(displayName))
            .findFirst();
    }
}