package com.atendimento.app.repositories;

import com.atendimento.app.entities.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para a entidade {@link Atendimento}.
 */
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    /**
     * Busca atendimentos pelo CPF do cliente.
     *
     * @param cpf CPF do cliente.
     * @return Lista de atendimentos associados ao CPF.
     */
    List<Atendimento> findByCpf(String cpf);

    /**
     * Busca um atendimento pelo número de protocolo.
     *
     * @param protocolo Número do protocolo.
     * @return O atendimento, se encontrado.
     */
    Optional<Atendimento> findByProtocolo(String protocolo);
}
