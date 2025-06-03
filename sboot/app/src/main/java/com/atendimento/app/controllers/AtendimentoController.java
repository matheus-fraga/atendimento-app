package com.atendimento.app.controllers;

import com.atendimento.app.dto.AtendimentoRequest;
import com.atendimento.app.dto.AtendimentoResponse;
import com.atendimento.app.entities.Atendimento;
import com.atendimento.app.mappers.AtendimentoMapper;
import com.atendimento.app.services.AtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar atendimentos.
 */
@Tag(name = "Atendimentos", description = "Gerenciamento de atendimentos")
@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private static final Logger logger = LoggerFactory.getLogger(AtendimentoController.class);

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private AtendimentoMapper atendimentoMapper;

    /**
     * Cria um novo atendimento.
     *
     * @param request Dados do atendimento.
     * @return Atendimento criado.
     */
    @Operation(summary = "Criar um novo atendimento", description = "Recebe os dados de um cliente e cria um atendimento.")
    @PostMapping
    public ResponseEntity<AtendimentoResponse> criarAtendimento(@Valid @RequestBody AtendimentoRequest request) {
        logger.info("Recebendo solicitação para criar atendimento: {}", request);
        Atendimento novoAtendimento = atendimentoService.criarAtendimento(atendimentoMapper.toEntity(request));
        logger.info("Atendimento criado com sucesso: {}", novoAtendimento.getProtocolo());
        return ResponseEntity.ok(atendimentoMapper.toResponse(novoAtendimento));
    }

    /**
     * Consulta atendimentos pelo CPF.
     *
     * @param cpf CPF do cliente.
     * @return Lista de atendimentos associados ao CPF.
     */
    @Operation(summary = "Consultar atendimentos por CPF", description = "Retorna todos os atendimentos associados ao CPF fornecido.")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<AtendimentoResponse>> consultarPorCpf(@PathVariable String cpf) {
        logger.info("Consultando atendimentos para o CPF: {}", cpf);
        List<Atendimento> atendimentos = atendimentoService.consultarPorCpf(cpf);
        if (atendimentos.isEmpty()) {
            logger.warn("Nenhum atendimento encontrado para o CPF: {}", cpf);
            return ResponseEntity.notFound().build();
        }
        List<AtendimentoResponse> responses = atendimentos.stream()
                .map(atendimentoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    /**
     * Consulta um atendimento pelo número de protocolo.
     *
     * @param protocolo Número de protocolo.
     * @return Atendimento correspondente ao protocolo.
     */
    @Operation(summary = "Consultar atendimento por protocolo", description = "Retorna o atendimento associado ao protocolo fornecido.")
    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<AtendimentoResponse> consultarPorProtocolo(@PathVariable String protocolo) {
        logger.info("Consultando atendimento para o protocolo: {}", protocolo);
        Optional<Atendimento> atendimento = atendimentoService.consultarPorProtocolo(protocolo);
        return atendimento.map(a -> ResponseEntity.ok(atendimentoMapper.toResponse(a)))
                          .orElseGet(() -> {
                              logger.warn("Atendimento não encontrado para o protocolo: {}", protocolo);
                              return ResponseEntity.notFound().build();
                          });
    }
}