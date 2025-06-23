package com.atendimento.app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.atendimento.app.utils.ValidCPF;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade para representar os atendimentos na aplicação.
 * 
 * <p>
 * Esta classe contém informações sobre os atendimentos, incluindo nome do cliente,
 * CPF, descrição, tipo, protocolo e datas de auditoria. As datas de criação
 * e atualização são gerenciadas automaticamente pelo Spring Data JPA.
 * </p>
 */
@Entity
@Table(name = "atendimentos")
@EntityListeners(AuditingEntityListener.class)
@Data // Gera getters, setters, equals, hashCode e toString automaticamente
@NoArgsConstructor // Gera um construtor vazio
@AllArgsConstructor // Gera um construtor com todos os campos
@Builder // Adiciona o padrão Builder para facilitar a criação de objetos
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres")
    private String nomeCliente;

    @ValidCPF
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "A descrição do atendimento é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotBlank(message = "O tipo do atendimento é obrigatório")
    @Size(max = 50, message = "O tipo deve ter no máximo 50 caracteres")
    private String tipo;

    @CreationTimestamp
    private LocalDateTime dataHora;

    @Column(nullable = false, unique = true)
    private String protocolo;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne // Relacionamento com o atendente (usuário responsável)
    @JoinColumn(name = "atendente_id", nullable = false) // Cria a FK no banco de dados
    private User atendente;
}