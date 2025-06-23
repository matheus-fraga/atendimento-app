package com.atendimento.app.controllers;

import com.atendimento.app.dto.AtualizarDescricaoRequest;
import com.atendimento.app.entities.Atendimento;
import com.atendimento.app.repositories.AtendimentoRepository;
import com.atendimento.app.repositories.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador para gerenciar atendimentos (supervisor).
 */
@Tag(name = "Supervisor", description = "Endpoints para supervisores gerenciarem atendimentos")
@RestController
@RequestMapping("/supervisor/atendimentos")
public class SupervisorController {

    private static final Logger logger = LoggerFactory.getLogger(SupervisorController.class);

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Lista todos os atendimentos.
     *
     * @return Lista de atendimentos.
     */
    @Operation(summary = "Listar atendimentos", description = "Lista todos os atendimentos registrados (somente supervisores).")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')") // Supervisores e administradores podem utilizar recurso.
    @GetMapping
    public ResponseEntity<List<Atendimento>> listarAtendimentos() {
        logger.info("Listando todos os atendimentos (somente supervisores).");
        List<Atendimento> atendimentos = atendimentoRepository.findAll();
        return ResponseEntity.ok(atendimentos);
    }

    /**
     * Atualiza a descrição de um atendimento.
     *
     * @param atendimentoId ID do atendimento a ser atualizado.
     * @param novaDescricao Nova descrição para o atendimento.
     * @return Mensagem de sucesso ou erro.
     */
    @Operation(summary = "Editar atendimento", description = "Atualiza a descrição de um atendimento (somente supervisores).")
    @PreAuthorize("hasRole('SUPERVISOR')") // Somente supervisores podem acessar este método
    @PatchMapping("/{atendimentoId}/editar")
    public ResponseEntity<?> editarAtendimento(@PathVariable UUID atendimentoId, @Valid @RequestBody AtualizarDescricaoRequest request) {
        logger.info("Solicitação para atualizar a descrição do atendimento com ID: {}", atendimentoId);

        var atendimentoOptional = atendimentoRepository.findById(atendimentoId);
        if (atendimentoOptional.isEmpty()) {
            logger.warn("Atendimento com ID {} não encontrado.", atendimentoId);
            return ResponseEntity.notFound().build();
        }

        Atendimento atendimento = atendimentoOptional.get();
        atendimento.setDescricao(request.getNovaDescricao());
        atendimentoRepository.save(atendimento);

        logger.info("Descrição do atendimento com ID {} atualizada com sucesso.", atendimentoId);
        return ResponseEntity.ok("Descrição atualizada com sucesso.");
    }

    /**
     * Consulta atendimentos por um número de protocolo.
     *
     * @param protocolo Número de protocolo.
     * @return Atendimento correspondente.
     */
    @Operation(summary = "Consultar atendimento por protocolo", description = "Consulta um atendimento pelo número de protocolo (somente supervisores).")
    @PreAuthorize("hasRole('SUPERVISOR')") // Somente supervisores podem acessar este método
    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<?> consultarPorProtocolo(@PathVariable String protocolo) {
        logger.info("Consultando atendimento pelo protocolo: {}", protocolo);

        var atendimentoOptional = atendimentoRepository.findByProtocolo(protocolo);
        if (atendimentoOptional.isEmpty()) {
            logger.warn("Atendimento com protocolo {} não encontrado.", protocolo);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(atendimentoOptional.get());
    }

    /**
     * Consulta atendimentos de um atendente.
     *
     * @param atendenteId ID do atendente.
     * @return Lista de atendimentos associados ao atendente.
     */
    @Operation(summary = "Consultar atendimentos de um atendente", description = "Lista todos os atendimentos registrados por um atendente específico (somente supervisores).")
    @PreAuthorize("hasRole('SUPERVISOR')") // Somente supervisores podem acessar este método
    @GetMapping("/atendente/{atendenteId}")
    public ResponseEntity<?> consultarAtendimentosPorAtendente(@PathVariable UUID atendenteId) {
        logger.info("Consultando atendimentos do atendente com ID: {}", atendenteId);

        // Verifica se o atendente existe
        var atendenteOptional = userRepository.findById(atendenteId);
        if (atendenteOptional.isEmpty()) {
            logger.warn("Atendente com ID {} não encontrado.", atendenteId);
            return ResponseEntity.notFound().build();
        }

        // Recupera os atendimentos do atendente
        var atendimentos = atendimentoRepository.findAllByAtendente(atendenteOptional.get());
        if (atendimentos.isEmpty()) {
            logger.info("Nenhum atendimento encontrado para o atendente com ID: {}", atendenteId);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se não houver atendimentos
        }

        return ResponseEntity.ok(atendimentos);
    }
}