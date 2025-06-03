package com.atendimento.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AtendimentoRequest {

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres")
    private String nomeCliente;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "A descrição do atendimento é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotBlank(message = "O tipo do atendimento é obrigatório")
    @Size(max = 50, message = "O tipo deve ter no máximo 50 caracteres")
    private String tipo;
}