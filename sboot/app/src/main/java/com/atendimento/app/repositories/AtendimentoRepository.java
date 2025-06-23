package com.atendimento.app.repositories;

import com.atendimento.app.entities.Atendimento;
import com.atendimento.app.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para a entidade {@link Atendimento}.
 */
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

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

    /**
     * Busca atendimentos por tipo.
     * 
     * @param tipo Tipo do atendimento.
     * @return Lista de atendimentos com o tipo especificado.
     */
    List<Atendimento> findAllByTipo(String tipo);

    /**
     * Busca atendimentos por atendente.
     *
     * @param atendente O atendente responsável.
     * @return Lista de atendimentos associados ao atendente.
     */
    List<Atendimento> findAllByAtendente(User atendente);
}
