package com.atendimento.app.mappers;

import com.atendimento.app.dto.AtendimentoRequest;
import com.atendimento.app.dto.AtendimentoResponse;
import com.atendimento.app.entities.Atendimento;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre DTOs e Entidade Atendimento.
 */
@Component
public class AtendimentoMapper {

    /**
     * Converte um DTO de entrada (AtendimentoRequest) em uma entidade Atendimento.
     *
     * @param request DTO de entrada.
     * @return Entidade Atendimento.
     */
    public Atendimento toEntity(AtendimentoRequest request) {
        return Atendimento.builder()
                .nomeCliente(request.getNomeCliente())
                .cpf(request.getCpf())
                .descricao(request.getDescricao())
                .tipo(request.getTipo())
                .build();
    }

    /**
     * Converte uma entidade Atendimento em um DTO de saída (AtendimentoResponse).
     *
     * @param atendimento Entidade Atendimento.
     * @return DTO de saída AtendimentoResponse.
     */
    public AtendimentoResponse toResponse(Atendimento atendimento) {
        AtendimentoResponse response = new AtendimentoResponse();
        response.setProtocolo(atendimento.getProtocolo());
        response.setNomeCliente(atendimento.getNomeCliente());
        response.setCpf(atendimento.getCpf());
        response.setDescricao(atendimento.getDescricao());
        response.setTipo(atendimento.getTipo());
        response.setCreatedAt(atendimento.getCreatedAt());
        return response;
    }
}