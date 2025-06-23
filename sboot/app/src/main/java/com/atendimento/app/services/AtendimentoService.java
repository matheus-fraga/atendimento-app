package com.atendimento.app.services;

import com.atendimento.app.entities.Atendimento;
import com.atendimento.app.entities.User;
import com.atendimento.app.repositories.AtendimentoRepository;
import com.atendimento.app.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço para gerenciar a lógica de negócios relacionada a atendimentos.
 */
@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Salva um novo atendimento no banco de dados.
     *
     * @param atendimento Objeto de atendimento a ser salvo.
     * @return Atendimento salvo.
     */
    public Atendimento criarAtendimento(Atendimento atendimento) {
    // Gera protocolo único
    atendimento.setProtocolo(gerarProtocolo());

    // Obtém o usuário autenticado
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName(); // login do atendente

    // Busca o atendente no banco
    User atendente = userRepository.findByUsername(username)
    .orElseThrow(() -> new UsernameNotFoundException("Atendente não encontrado: " + username));

    // Associa ao atendimento
    atendimento.setAtendente(atendente);

    // Salva
    return atendimentoRepository.save(atendimento);
}


    /**
     * Consulta atendimentos pelo CPF do cliente.
     *
     * @param cpf CPF do cliente.
     * @return Lista de atendimentos associados ao CPF.
     */
    public List<Atendimento> consultarPorCpf(String cpf) {
        return atendimentoRepository.findByCpf(cpf);
    }

    /**
     * Consulta um atendimento pelo número de protocolo.
     *
     * @param protocolo Número de protocolo.
     * @return O atendimento, se encontrado.
     */
    public Optional<Atendimento> consultarPorProtocolo(String protocolo) {
        return atendimentoRepository.findByProtocolo(protocolo);
    }

    /**
     * Gera um número de protocolo único.
     *
     * @return Número de protocolo.
     */
    private String gerarProtocolo() {
        return UUID.randomUUID().toString();
    }
}