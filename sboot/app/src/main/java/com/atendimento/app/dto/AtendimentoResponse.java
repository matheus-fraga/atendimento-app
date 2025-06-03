package com.atendimento.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtendimentoResponse {

    private String protocolo;
    private String nomeCliente;
    private String cpf;
    private String descricao;
    private String tipo;
    private LocalDateTime createdAt;
}