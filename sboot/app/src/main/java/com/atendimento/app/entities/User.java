package com.atendimento.app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade para representar os usuários na aplicação.
 * 
 * <p>
 * Esta classe contém informações sobre os usuários, incluindo nome de usuário,
 * senha, papel e status de bloqueio. As datas de criação e atualização são gerenciadas
 * automaticamente pelo Spring Data JPA.
 * </p>
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data // Gera getters, setters, equals, hashCode e toString automaticamente
@NoArgsConstructor // Gera um construtor vazio
@AllArgsConstructor // Gera um construtor com todos os campos
@Builder // Adiciona o padrão Builder para facilitar a criação de objetos
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    @JsonIgnore // Garante que a senha não seja exposta em respostas JSON
    private String password;

    @Builder.Default
    private boolean isLocked = false; // Inicializado como não bloqueado por padrão

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O papel do usuário é obrigatório")
    private Role role;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}